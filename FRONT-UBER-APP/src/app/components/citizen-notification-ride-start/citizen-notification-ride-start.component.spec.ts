import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CitizenNotificationRideStartComponent } from './citizen-notification-ride-start.component';

describe('CitizenNotificationRideStartComponent', () => {
  let component: CitizenNotificationRideStartComponent;
  let fixture: ComponentFixture<CitizenNotificationRideStartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CitizenNotificationRideStartComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CitizenNotificationRideStartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
