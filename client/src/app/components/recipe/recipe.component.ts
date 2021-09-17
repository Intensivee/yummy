import {Direction} from '../../model/direction';
import {Comment} from '../../model/comment';
import {DirectionService} from '../../service/direction.service';
import {CommentService} from '../../service/comment.service';
import {IngredientService} from '../../service/ingredient.service';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {RecipeService} from '../../service/recipe.service';
import {Recipe} from '../../model/recipe';
import {Ingredient} from 'src/app/model/ingredient';
import { TimeType } from 'src/app/model/timeType';
import {AuthenticationService} from '../../security/authentication.service';

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.css']
})
export class RecipeComponent implements OnInit {

  timeTypeEnum = TimeType;
  recipe: Recipe = new Recipe();
  ingredients: Ingredient[];
  directions: Direction[];
  comments: Comment[];

  constructor(private rout: ActivatedRoute,
              private route: Router,
              private recipeService: RecipeService,
              private ingredientService: IngredientService,
              private commentService: CommentService,
              private directionService: DirectionService,
              public authService: AuthenticationService) {
  }

  ngOnInit(): void {
    this.recipe.id = +this.rout.snapshot.paramMap.get('id');
    this.loadData();
  }

  loadData(): void {
    this.loadRecipe();
    this.loadIngredients();
    this.loadDirections();
    this.loadComments();
  }

  loadRecipe(): void {
    this.recipeService.getById(this.recipe.id)
      .subscribe(recipe => this.recipe = recipe);
  }

  loadIngredients(): void {
    this.ingredientService.getByRecipeId(this.recipe.id)
      .subscribe(ingredients => this.ingredients = ingredients);
  }

  loadDirections(): void {
    this.directionService.getByRecipeId(this.recipe.id)
      .subscribe(directions => this.directions = directions);
  }

  loadComments(): void {
    this.commentService.getByRecipeId(this.recipe.id)
      .subscribe(comments => this.comments = comments);
  }

  deleteRecipe(recipeId: number): void {
    if (confirm('Do you really want to delete recipe?')){
    this.recipeService.deleteById(recipeId).subscribe(() => this.route.navigate(['recipes']));
    }
  }
}
