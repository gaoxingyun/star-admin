import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SmartTablesComponent } from './smart-tables.component';

describe('SmartTablesComponent', () => {
  let component: SmartTablesComponent;
  let fixture: ComponentFixture<SmartTablesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SmartTablesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SmartTablesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
