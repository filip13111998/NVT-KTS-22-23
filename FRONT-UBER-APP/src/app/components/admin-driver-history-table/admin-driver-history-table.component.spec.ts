import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDriverHistoryTableComponent } from './admin-driver-history-table.component';

describe('AdminDriverHistoryTableComponent', () => {
  let component: AdminDriverHistoryTableComponent;
  let fixture: ComponentFixture<AdminDriverHistoryTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminDriverHistoryTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminDriverHistoryTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
