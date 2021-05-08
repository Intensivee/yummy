import { ActivatedRoute } from '@angular/router';
import { RecipeService } from '../../service/recipe.service';
import { Component, OnInit } from '@angular/core';
import { Recipe } from 'src/app/model/recipe';
import { PageEvent } from '@angular/material/paginator';

const RECIPE_CATEGORY_PARAM = 'category';
const RECIPE_COMPONENT_PARAM = 'component';

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.css']
})
export class RecipesComponent implements OnInit {

  category = 'All';
  recipes: Recipe[];
  pageSize = 3;
  pageNumber = 0;
  totalElements: number;

  constructor(private recipeService: RecipeService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => this.initializeData());
  }

  initializeData(): void {
    if (this.route.snapshot.paramMap.has(RECIPE_CATEGORY_PARAM)) {
      this.handleRouteWithCategoryParam();
    } else if (this.route.snapshot.paramMap.has(RECIPE_COMPONENT_PARAM)) {
      this.handleRouteWithComponentParam();
    } else {
      this.loadAllRecipesPaginated();
    }
  }

  handleRouteWithCategoryParam(): void {
    const newCategory = this.route.snapshot.paramMap.get(RECIPE_CATEGORY_PARAM);
    this.resetPageNumberIfNewParam(newCategory);
    this.category = newCategory;
    this.loadRecipesPaginatedByCategory();
  }

  handleRouteWithComponentParam(): void {
    const newComponent = this.route.snapshot.paramMap.get(RECIPE_COMPONENT_PARAM);
    this.resetPageNumberIfNewParam(newComponent);
    this.category = newComponent;
    this.loadRecipesPaginatedByComponent();
  }

  resetPageNumberIfNewParam(newCategory: string): void {
    if (this.category !== newCategory) {
      this.pageNumber = 0;
    }
  }

  loadRecipesPaginatedByCategory(): void {
    this.recipeService.getPagedByCategoryName(this.category, this.pageNumber, this.pageSize)
      .subscribe(this.processResponse());
  }

  loadRecipesPaginatedByComponent(): void {
    this.recipeService.getPagedByComponentName(this.category, this.pageNumber, this.pageSize)
      .subscribe(this.processResponse());
  }

  loadAllRecipesPaginated(): void {
    this.recipeService.getPaged(this.pageNumber, this.pageSize)
      .subscribe(this.processResponse());
  }

  processResponse(): any {
    return data => {
      this.recipes = data.content;
      this.pageSize = data.size;
      this.pageNumber = data.number;
      this.totalElements = data.totalElements;
    };
  }

  OnPageChange(event: PageEvent): void {
    this.pageSize = event.pageSize;
    this.pageNumber = event.pageIndex;
    this.loadAllRecipesPaginated();
  }
}
