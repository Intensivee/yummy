import {RecipeService} from '../../service/recipe.service';
import {RecipeCategory} from '../../model/recipe-category';
import {RecipeCategoryService} from '../../service/recipe-category.service';
import {ComponentService} from '../../service/component.service';
import {IngredientService} from '../../service/ingredient.service';
import {Observable} from 'rxjs';
import {debounceTime, distinctUntilChanged, map, startWith, switchMap} from 'rxjs/operators';
import {TimeType} from '../../model/TimeType';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Component, OnInit} from '@angular/core';
import {DEFAULT_IMG} from 'src/app/constants';
import * as clone from 'clone';
import {MatDialogRef} from '@angular/material/dialog';
import {Router} from '@angular/router';

@Component({
  selector: 'app-create-recipe',
  templateUrl: './create-recipe.component.html',
  styleUrls: ['./create-recipe.component.css']
})
export class CreateRecipeComponent implements OnInit {

  filteredComponentOptions: Observable<string[]>;
  ingredientForm: FormGroup;
  recipeForm: FormGroup;
  TimeType = TimeType;
  amountTypes$: Observable<string[]>;
  categories$: Observable<RecipeCategory[]>;

  constructor(private dialogRef: MatDialogRef<any>,
              private router: Router,
              private formBuilder: FormBuilder,
              private ingredientService: IngredientService,
              private componentService: ComponentService,
              private recipeCategoryService: RecipeCategoryService,
              private recipeService: RecipeService) {
  }

  get category() {
    return this.recipeForm.get('category') as FormArray;
  }

  ngOnInit(): void {
    this.dialogRef.updateSize('80vw', '90vh');
    this.resetRecipeForm();
    this.resetIngredientForm();
    this.initializeComponentControl();
    this.amountTypes$ = this.ingredientService.getAmountTypes();
    this.categories$ = this.recipeCategoryService.getAll();
  }

  get componentName() {
    return this.ingredientForm.get('componentName');
  }

  initializeComponentControl(): void {
    this.filteredComponentOptions = this.componentName.valueChanges.pipe(
      startWith(''),
      debounceTime(400),
      distinctUntilChanged(),
      switchMap(value => this.filterComponents(value || ''))
    );
  }

  filterComponents(value: string): Observable<string[]> {
    return this.componentService.getAllComponentNames()
      .pipe(
        map(response => response.filter(option => {
          return option.toLowerCase().indexOf(value.toLowerCase()) === 0;
        }))
      );
  }

  addIngredientToList(): void {
    this.ingredientForm.markAllAsTouched();
    if (this.ingredientForm.valid) {
      this.ingredients.push(clone(this.ingredientForm));
      this.ingredientForm.reset();
    }
  }

  removeIngredient(index: number): void {
    this.ingredients.removeAt(index);
  }

  addDirectionStep(): void {
    const control = new FormControl('', Validators.required);
    this.directions.push(control);
  }

  removeDirectionStep(index: number): void {
    this.directions.removeAt(index);
  }

  resetRecipeForm(): void {
    this.recipeForm = this.formBuilder.group({
      title: new FormControl('', Validators.required),
      timeType: new FormControl('', Validators.required),
      directions: new FormArray([new FormControl('')]),
      imgSource: new FormControl(DEFAULT_IMG),
      ingredients: new FormArray([], Validators.required),
      category: new FormControl(null, Validators.required),
    });
  }

  get amountType() {
    return this.ingredientForm.get('amountType');
  }

  get amount() {
    return this.ingredientForm.get('amount');
  }

  get title() {
    return this.recipeForm.get('title');
  }

  get timeType() {
    return this.recipeForm.get('timeType');
  }

  get imgSource() {
    return this.recipeForm.get('imgSource');
  }

  get directions() {
    return this.recipeForm.get('directions') as FormArray;
  }

  get ingredients() {
    return this.recipeForm.get('ingredients') as FormArray;
  }

  onSubmit(): void {
    this.recipeForm.markAllAsTouched();
    if (this.recipeForm.valid) {
      const recipeCreateRequest = {
        recipeRequest: {
          timeType: this.timeType.value,
          title: this.title.value,
          categoriesIds: [this.category.value]
        },
        ingredients: this.ingredients.value,
        directions: this.directions.value
      };
      this.recipeService.createRecipe(recipeCreateRequest).subscribe(recipe => {
        this.router.navigate(['recipe', recipe.id]);
        this.dialogRef.close();
      });
    }
  }

  resetIngredientForm(): void {
    this.ingredientForm = this.formBuilder.group({
      componentName: new FormControl('', [
        Validators.required,
        Validators.maxLength(40)
      ]),
      amount: new FormControl('', [
        Validators.required,
        Validators.pattern('^[0-9|.]*$'),
        Validators.maxLength(6),
        Validators.min(0)
      ]),
      amountType: new FormControl('', Validators.required)
    });
  }
}
