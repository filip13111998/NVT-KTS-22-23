import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCitizenHistoryTableComponent } from './admin-citizen-history-table.component';

describe('AdminCitizenHistoryTableComponent', () => {
  let component: AdminCitizenHistoryTableComponent;
  let fixture: ComponentFixture<AdminCitizenHistoryTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminCitizenHistoryTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminCitizenHistoryTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
