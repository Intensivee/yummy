import {DirectionService} from '../../service/direction.service';
import {RecipeService} from '../../service/recipe.service';
import {RecipeCategory} from '../../model/recipe-category';
import {RecipeCategoryService} from '../../service/recipe-category.service';
import {ComponentService} from '../../service/component.service';
import {IngredientService} from '../../service/ingredient.service';
import {Observable} from 'rxjs';
import {debounceTime, distinctUntilChanged, finalize, map, startWith, switchMap} from 'rxjs/operators';
import {TimeType} from '../../model/TimeType';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Component, Inject, OnInit} from '@angular/core';
import {DEFAULT_IMG} from 'src/app/constants';
import * as clone from 'clone';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Router} from '@angular/router';
import {AngularFireStorage} from '@angular/fire/storage';

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
  imgErrorMessage: string = null;
  selectedImage = null;
  editedRecipeId: number = null;
  amountTypes$: Observable<string[]>;
  categories$: Observable<RecipeCategory[]>;

  constructor(@Inject(MAT_DIALOG_DATA) private dialogData: { editRecipeId: number },
              private dialogRef: MatDialogRef<any>,
              private router: Router,
              private fireStorage: AngularFireStorage,
              private formBuilder: FormBuilder,
              private ingredientService: IngredientService,
              private directionsService: DirectionService,
              private componentService: ComponentService,
              private recipeCategoryService: RecipeCategoryService,
              private recipeService: RecipeService) {
  }

  get category() {
    return this.recipeForm.get('category');
  }

  get componentName() {
    return this.ingredientForm.get('componentName');
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

  ngOnInit(): void {
    this.dialogRef.updateSize('80vw', '90vh');
    this.resetRecipeForm();
    this.resetIngredientForm();
    this.initializeComponentControl();
    this.amountTypes$ = this.ingredientService.getAmountTypes();
    this.categories$ = this.recipeCategoryService.getAll();
    if (this.dialogData?.editRecipeId) {
      this.editedRecipeId = this.dialogData.editRecipeId;
      this.initializeEditRecipeData();
    }
  }

  removeDirectionStep(index: number): void {
    this.directions.removeAt(index);
  }

  onImgChange(event: any): void {
    this.imgErrorMessage = null;
    this.selectedImage = null;

    if (this.isImageValid(event)) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.recipeForm.patchValue({imgSource: e.target.result});
      };
      reader.readAsDataURL(event.target.files[0]);
      this.selectedImage = event.target.files[0];
    } else {
      this.imgErrorMessage = 'Provide proper image that weighs less than 2MB!';
    }
  }

  isImageValid(event: any): boolean {
    const maxSizeInMB = 2;
    return (event.target.files && event.target.files[0] &&
      event.target.files[0].size / (1024 * 1024) < maxSizeInMB);
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

  initializeComponentControl(): void {
    this.filteredComponentOptions = this.componentName.valueChanges.pipe(
      startWith(''),
      debounceTime(200),
      distinctUntilChanged(),
      switchMap(value => this.filterComponents(value || ''))
    );
  }

  initializeEditRecipeData(): void {
    this.populateRecipeData();
    this.populateDirectionsData();
    this.populateCategoryData();
    this.populateIngredientsData();
  }

  populateRecipeData(): void {
    this.recipeService.getById(this.editedRecipeId).subscribe(recipe => {
      this.recipeForm.patchValue({
        title: recipe.title,
        timeType: recipe.timeType,
        imgSource: recipe.img
      });
    });
  }

  populateDirectionsData(): void {
    this.directionsService.getByRecipeId(this.editedRecipeId)
      .subscribe(directions => {
        this.directions.removeAt(0);
        directions.forEach(direction => {
          this.addDirectionStep(direction.description);
        });
      });
  }

  populateCategoryData(): void {
    this.recipeCategoryService.getAllByRecipeId(this.editedRecipeId)
      .subscribe(categories => {
        this.category.setValue(categories[0].id);
      });
  }

  populateIngredientsData(): void {
    this.ingredientService.getByRecipeId(this.editedRecipeId)
      .subscribe(ingredients => {
        ingredients.forEach(ingredient => {
          this.ingredientForm.setValue({
            componentName: ingredient.componentName,
            amount: ingredient.amount,
            amountType: ingredient.amountType
          });
          this.addIngredientToList();
        });
      });
  }

  addDirectionStep(direction: string = ''): void {
    const control = new FormControl(direction, Validators.required);
    this.directions.push(control);
  }

  onSubmit(): void {
    this.recipeForm.markAllAsTouched();
    if (!this.recipeForm.valid) {
      return;
    }
    if (this.editedRecipeId) {
      this.handleEditSubmit();
    } else {
      this.handleCreateSubmit();
    }


  }

  handleEditSubmit(): void {
    if (this.selectedImage == null) {
      this.editRecipe();
    } else {
      this.saveRecipeWithImage(false);
    }
  }

  handleCreateSubmit(): void {
    if (this.selectedImage) {
      this.saveRecipeWithImage(true);
    } else {
      this.imgErrorMessage = 'image is required';
    }
  }

  saveRecipeWithImage(isCreateRequest: boolean): void {
    const filePath = `recipe-img/${this.selectedImage.name.split('.').slice(0, -1).join('.')}_${new Date().getTime()}`;
    const fileRef = this.fireStorage.ref(filePath);
    this.fireStorage.upload(filePath, this.selectedImage)
      .snapshotChanges().pipe(
      finalize(() => {
        fileRef.getDownloadURL().subscribe((url) => {
          this.recipeForm.patchValue({imgSource: url});
          if (isCreateRequest) {
            this.saveRecipe();
          } else {
            this.editRecipe();
          }
        });
      })
    ).subscribe();
  }

  saveRecipe(): void {
    const recipeCreateRequest = this.mapFormToRecipeRequest();
    this.recipeService.createRecipe(recipeCreateRequest).subscribe(recipe => {
      this.router.navigate(['recipe', recipe.id]);
      this.dialogRef.close();
    });
  }

  editRecipe(): void {
    const recipeEditRequest = this.mapFormToRecipeRequest();
    this.recipeService.editRecipe(recipeEditRequest).subscribe(recipe => {
      this.router.navigate(['recipe', recipe.id]);
      this.dialogRef.close();
    });
  }

  mapFormToRecipeRequest(): any {
    return {
      id: this.editedRecipeId,
      timeType: this.timeType.value.toString(),
      title: this.title.value,
      img: this.imgSource.value,
      categoriesIds: [this.category.value],
      ingredients: this.ingredients.value,
      directions: this.directions.value
    };
  }

  filterComponents(value: string): Observable<string[]> {
    return this.componentService.getAllComponentNames()
      .pipe(
        map(response => response.filter(option => {
          return option.toLowerCase().indexOf(value.toLowerCase()) === 0;
        }))
      );
  }
}
