import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CitizenHistoryTableComponent } from './citizen-history-table.component';

describe('CitizenHistoryTableComponent', () => {
  let component: CitizenHistoryTableComponent;
  let fixture: ComponentFixture<CitizenHistoryTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CitizenHistoryTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CitizenHistoryTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
