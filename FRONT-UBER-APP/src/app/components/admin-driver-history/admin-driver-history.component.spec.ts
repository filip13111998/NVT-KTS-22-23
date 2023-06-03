import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDriverHistoryComponent } from './admin-driver-history.component';

describe('AdminDriverHistoryComponent', () => {
  let component: AdminDriverHistoryComponent;
  let fixture: ComponentFixture<AdminDriverHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminDriverHistoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminDriverHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
