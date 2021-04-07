import { Component, OnInit } from '@angular/core';
import { ComponentCategoryService } from '../../service/component-category.service';
import { ComponentCategory } from '../../model/component-category';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {
  componentCategories: ComponentCategory[];

  constructor(private componentCatService: ComponentCategoryService) { }

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.loadComponentCategories();
  }

  loadComponentCategories(): void {
    this.componentCatService.getAllWithComponentLists()
      .subscribe(categories => this.componentCategories = categories);
  }

}
