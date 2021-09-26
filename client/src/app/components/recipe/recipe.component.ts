import {RateService} from '../../service/rate.service';
import {FormControl, Validators} from '@angular/forms';
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
import {TimeType} from 'src/app/model/timeType';
import {AuthenticationService} from '../../security/authentication.service';
import {tap} from 'rxjs/operators';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.css']
})
export class RecipeComponent implements OnInit {

  timeTypeEnum = TimeType;
  recipe: Recipe = new Recipe();
  ingredients$: Observable<Ingredient[]>;
  directions$: Observable<Direction[]>;
  comments$: Observable<Comment[]>;
  newComment = new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(200)]);

  constructor(private rout: ActivatedRoute,
              private route: Router,
              private recipeService: RecipeService,
              private rateService: RateService,
              private ingredientService: IngredientService,
              private commentService: CommentService,
              private directionService: DirectionService,
              public authService: AuthenticationService) {
  }

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.recipe.id = +this.rout.snapshot.paramMap.get('id');
    this.loadRecipe();
    this.loadIngredients();
    this.loadDirections();
    this.loadComments();
  }

  loadRecipe(): void {
    this.recipeService.getById(this.recipe.id).subscribe(recipe => this.recipe = recipe);
  }

  loadIngredients(): void {
    this.ingredients$ = this.ingredientService.getByRecipeId(this.recipe.id);
  }

  loadDirections(): void {
    this.directions$ = this.directionService.getByRecipeId(this.recipe.id);
  }

  loadComments(): void {
    this.comments$ = this.commentService.getByRecipeId(this.recipe.id);
  }

  deleteRecipe(): void {
    if (confirm('Do you really want to delete recipe?')) {
      this.recipeService.deleteById(this.recipe.id).subscribe(() => this.route.navigate(['recipes']));
    }
  }

  onCommentSend(): void {
    this.newComment.markAsTouched();
    if (this.newComment.valid) {
      this.commentService.createComment(this.newComment.value, this.recipe.id).pipe(
        tap(() => this.newComment.reset())
      ).subscribe(() => this.loadComments());
    }
  }

  deleteComment(commentId: number): void {
    this.commentService.deleteCommentById(commentId).subscribe(() => this.loadComments());
  }

  onRateChange(event: any): void {
    this.rateService.addOrReplaceRate(event.newValue, this.recipe.id)
      .subscribe(newAverageRate => this.recipe.avgRate = newAverageRate);
  }
}
