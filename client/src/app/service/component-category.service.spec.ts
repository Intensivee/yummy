import { TestBed } from '@angular/core/testing';

import { ComponentCategoryService } from './component-category.service';

describe('ComponentCategoryService', () => {
  let service: ComponentCategoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ComponentCategoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
