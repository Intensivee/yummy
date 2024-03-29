import { TimeType } from 'src/app/model/TimeType';
import { Component, Input, OnInit } from '@angular/core';
import { Recipe } from '../../model/recipe';

@Component({
  selector: 'app-recipe-block',
  templateUrl: './recipe-block.component.html',
  styleUrls: ['./recipe-block.component.css']
})
export class RecipeBlockComponent implements OnInit {
  @Input() recipe: Recipe;
  timeTypeEnum = TimeType;

  constructor() { }

  ngOnInit(): void {
  }

}
