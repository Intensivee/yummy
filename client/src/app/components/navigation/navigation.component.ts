import {CreateRecipeComponent} from '../create-recipe/create-recipe.component';
import {MatDialog} from '@angular/material/dialog';
import {AuthenticationService} from '../../security/authentication.service';
import {Router} from '@angular/router';
import {RecipeCategoryService} from '../../service/recipe-category.service';
import {Component, OnInit} from '@angular/core';
import {ComponentCategoryService} from '../../service/component-category.service';
import {ComponentCategory} from '../../model/component-category';
import {RecipeCategory} from 'src/app/model/recipe-category';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {
  componentCategories: ComponentCategory[];
  recipeCategories: RecipeCategory[];

  constructor(private route: Router,
              private dialog: MatDialog,
              private componentCatService: ComponentCategoryService,
              private recipeCatService: RecipeCategoryService,
              public authService: AuthenticationService) {
  }

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.loadRecipeCategories();
    this.loadComponentCategories();
  }

  loadComponentCategories(): void {
    this.componentCatService.getAllWithComponentLists()
      .subscribe(categories => this.componentCategories = categories);
  }

  loadRecipeCategories(): void {
    this.recipeCatService.getAll()
      .subscribe(categories => this.recipeCategories = categories);
  }

  searchForRecipes(key: string): void {
    this.route.navigate(['recipes/search', key]);
  }

  openRecipeCreatePopup(): void {
    this.dialog.open(CreateRecipeComponent);
  }

  logout(): void {
    this.authService.logout();
  }
}
