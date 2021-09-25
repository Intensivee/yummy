import {RecipeCategoryService} from './../../service/recipe-category.service';
import {Observable} from 'rxjs';
import {RecipeService} from '../../service/recipe.service';
import {Component, OnInit} from '@angular/core';
import {Recipe} from 'src/app/model/recipe';
import {TimeType} from 'src/app/model/timeType';
import {RecipeCategory} from 'src/app/model/recipe-category';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  timeTypeEnum = TimeType;
  recipes$: Observable<Recipe[]>;
  categories: RecipeCategory[];

  constructor(private recipeService: RecipeService,
              private categoryService: RecipeCategoryService) {
  }

  ngOnInit(): void {
    this.recipes$ = this.recipeService.get3TopRatedRecipes();
    const numberOfCategories = 6;
    this.categoryService.getCategoriesWithImages(numberOfCategories)
      .subscribe(data => this.categories = data);
  }

  scroll(el: HTMLElement) {
    el.scrollIntoView({behavior: 'smooth'});
  }
}
