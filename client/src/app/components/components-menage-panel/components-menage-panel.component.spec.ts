import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComponentsMenagePanelComponent } from './components-menage-panel.component';

describe('ComponentsMenagePanelComponent', () => {
  let component: ComponentsMenagePanelComponent;
  let fixture: ComponentFixture<ComponentsMenagePanelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ComponentsMenagePanelComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ComponentsMenagePanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
