import { RecipeService } from '../../service/recipe.service';
import { Component, OnInit } from '@angular/core';
import { Recipe } from 'src/app/model/recipe';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.css']
})
export class RecipesComponent implements OnInit {

  recipes: Recipe[];
  pageSize = 6;
  pageNumber = 0;
  totalElements: number;

  constructor(private recipeService: RecipeService) { }

  ngOnInit(): void {
    this.loadRecipesPaginated();
  }

  loadRecipesPaginated(): void {
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
    this.loadRecipesPaginated();
  }
}
