import { RecipeService } from '../../service/recipe.service';
import { Component, OnInit } from '@angular/core';
import { Recipe } from 'src/app/model/recipe';
import { TimeType } from 'src/app/model/timeType';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  timeTypeEnum = TimeType;
  recipes: Recipe[] = [];


  constructor(private recipeService: RecipeService) { }

  ngOnInit(): void {
    this.recipeService.get3TopRatedRecipes().subscribe(data => {
      this.recipes = data;
    });
  }
}
