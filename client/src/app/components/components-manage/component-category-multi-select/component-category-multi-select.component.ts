import {FormControl, Validators} from '@angular/forms';
import {Observable} from 'rxjs';
import {Component, Inject, OnInit, Output} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {ComponentCategory} from 'src/app/model/component-category';
import {filter} from 'rxjs/operators';

@Component({
  selector: 'app-component-category-multi-select',
  templateUrl: './component-category-multi-select.component.html',
  styleUrls: ['./component-category-multi-select.component.css']
})
export class ComponentCategoryMultiSelectComponent implements OnInit {

  @Output() selectedCategoriesIds = new FormControl([], Validators.required);
  categories$: Observable<ComponentCategory[]>;

  constructor(@Inject(MAT_DIALOG_DATA) data,
              private dialogRef: MatDialogRef<ComponentCategoryMultiSelectComponent>) {
    this.categories$ = data;
  }

  ngOnInit(): void {
    this.dialogRef.updateSize('500px', '270px');
    this.dialogRef.backdropClick().subscribe(() => this.onCancel());
    this.dialogRef.keydownEvents().pipe(
      filter(event => event.key === 'Escape')
    ).subscribe(() => this.onCancel());
  }

  onSubmit(): void {
    this.selectedCategoriesIds.markAllAsTouched();
    if (this.selectedCategoriesIds.valid) {
      this.dialogRef.close(this.selectedCategoriesIds.value);
    }
  }

  onCancel(): void {
    this.dialogRef.close([]);
  }
}
