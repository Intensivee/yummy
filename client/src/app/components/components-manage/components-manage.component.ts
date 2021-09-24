import {ComponentCategoryMultiSelectComponent} from './component-category-multi-select/component-category-multi-select.component';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {ComponentService} from '../../service/component.service';
import {ComponentCategoryService} from '../../service/component-category.service';
import {ComponentCategory} from '../../model/component-category';
import {Observable} from 'rxjs';
import {Component, OnInit} from '@angular/core';
import {Component as MyComponent} from 'src/app/model/component';
import {filter, map} from 'rxjs/operators';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-components-manage',
  templateUrl: './components-manage.component.html',
  styleUrls: ['./components-manage.component.css']
})
export class ComponentsManageComponent implements OnInit {

  components$: Observable<MyComponent[]>;
  categories$: Observable<ComponentCategory[]>;
  displayedColumns = ['id', 'name', 'actions'];

  constructor(private dialog: MatDialog,
              private snackBar: MatSnackBar,
              private componentService: ComponentService,
              private categoryService: ComponentCategoryService) {
  }

  ngOnInit(): void {
    this.categories$ = this.categoryService.getAll();
    this.components$ = this.componentService.getAllByIsAcceptedAndIsReviewed(false, false);
  }

  onAccept(id: number, name: string): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.data = this.categories$;
    this.dialog.open(ComponentCategoryMultiSelectComponent, dialogConfig).afterClosed()
      .pipe(
        filter(selectedCategories => selectedCategories.length > 0),
      ).subscribe(
      selectedCategories => this.patchComponent(id, name, true, true, selectedCategories)
    );
  }

  onReject(componentId: number, name: string): void {
    this.patchComponent(componentId, name, false, true);
  }

  patchComponent(id: number, name: string, isAccepted: boolean, isReviewed: boolean, categoriesIds?): void {
    this.componentService.patchComponent({id, name, isAccepted, isReviewed, categoriesIds})
      .subscribe(
        () => this.components$ = this.getComponentsFilteredWithId(id),
        error => this.snackBar.open(error.error.message, 'ok', {verticalPosition: 'top'})
      );
  }

  getComponentsFilteredWithId(componentId: number): Observable<MyComponent[]> {
    return this.components$.pipe(
      map(components =>
        components.filter(component => component.id !== componentId))
    );
  }
}
