import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ComponentsManageComponent} from './components-manage.component';

describe('ComponentsManageComponent', () => {
  let component: ComponentsManageComponent;
  let fixture: ComponentFixture<ComponentsManageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ComponentsManageComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ComponentsManageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
