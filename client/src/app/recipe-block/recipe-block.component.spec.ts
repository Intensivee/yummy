import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipeBlockComponent } from './recipe-block.component';

describe('RecipeBlockComponent', () => {
  let component: RecipeBlockComponent;
  let fixture: ComponentFixture<RecipeBlockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecipeBlockComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RecipeBlockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
