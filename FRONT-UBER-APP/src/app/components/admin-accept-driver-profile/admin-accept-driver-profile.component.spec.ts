import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAcceptDriverProfileComponent } from './admin-accept-driver-profile.component';

describe('AdminAcceptDriverProfileComponent', () => {
  let component: AdminAcceptDriverProfileComponent;
  let fixture: ComponentFixture<AdminAcceptDriverProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminAcceptDriverProfileComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminAcceptDriverProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
