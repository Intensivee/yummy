import { RecipeService } from '../../service/recipe.service';
import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/service/user.service';
import { Recipe } from 'src/app/model/recipe';
import { User } from 'src/app/model/user';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  user = new User();
  recipes: Recipe[];
  pageSize = 6;
  pageNumber = 0;
  totalElements: number;

  constructor(private recipeService: RecipeService,
              private userService: UserService) { }

  ngOnInit(): void {
    this.user.id = 1; // TODO: load from session
    this.loadData();
  }

  loadData(): void {
    this.loadUserData();
    this.loadUserRecipes();
  }

  loadUserData(): void {
    this.userService.getById(this.user.id)
      .subscribe(data => this.user = data);
  }

  loadUserRecipes(): void {
    this.recipeService.getByUserIdPaged(this.user.id, this.pageNumber, this.pageSize)
      .subscribe(this.processRecipeResponse());
  }

  processRecipeResponse(): any {
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
    this.loadUserRecipes();
  }
}
