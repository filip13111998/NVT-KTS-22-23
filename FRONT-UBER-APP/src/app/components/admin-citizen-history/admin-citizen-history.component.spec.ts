import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCitizenHistoryComponent } from './admin-citizen-history.component';

describe('AdminCitizenHistoryComponent', () => {
  let component: AdminCitizenHistoryComponent;
  let fixture: ComponentFixture<AdminCitizenHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminCitizenHistoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminCitizenHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
