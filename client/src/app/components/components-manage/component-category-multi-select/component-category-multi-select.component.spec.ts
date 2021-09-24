import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ComponentCategoryMultiSelectComponent} from './component-category-multi-select.component';

describe('ComponentCategoryMultiSelectComponent', () => {
  let component: ComponentCategoryMultiSelectComponent;
  let fixture: ComponentFixture<ComponentCategoryMultiSelectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ComponentCategoryMultiSelectComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ComponentCategoryMultiSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
