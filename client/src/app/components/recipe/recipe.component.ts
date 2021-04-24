import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {RecipeService} from '../../service/recipe.service';
import {Recipe} from '../../model/recipe';

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.css']
})
export class RecipeComponent implements OnInit {

  recipe: Recipe;

  constructor(private rout: ActivatedRoute,
              private recipeService: RecipeService) { }

  ngOnInit(): void {
  }

  getRecipe(): void {
    const id = this.rout.snapshot.paramMap.get('id');
    console.log(id);
  }

}
