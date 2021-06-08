import { AuthenticationService } from '../../security/authentication.service';
import { RecipeService } from '../../service/recipe.service';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/service/user.service';
import { Recipe } from 'src/app/model/recipe';
import { User } from 'src/app/model/user';
import { PageEvent } from '@angular/material/paginator';

const USERNAME_CATEGORY_PARAM = 'username';

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

  constructor(private route: ActivatedRoute,
              private recipeService: RecipeService,
              private userService: UserService,
              private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    if (this.route.snapshot.paramMap.has(USERNAME_CATEGORY_PARAM)) {
      this.user.username = this.route.snapshot.paramMap.get(USERNAME_CATEGORY_PARAM);
    } else {
      this.user.username = this.authenticationService.getAuthenticatedUsername();
    }
    this.loadUserData();
    this.loadUserRecipes();
  }

  loadUserData(): void {
    this.userService.getByUsername(this.user.username)
      .subscribe(data => this.user = data);
  }

  loadUserRecipes(): void {
    this.recipeService.getByUsernamePaged(this.user.username, this.pageNumber, this.pageSize)
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
