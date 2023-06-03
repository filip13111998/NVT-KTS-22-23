import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CitizenNotificationComponent } from './citizen-notification.component';

describe('CitizenNotificationComponent', () => {
  let component: CitizenNotificationComponent;
  let fixture: ComponentFixture<CitizenNotificationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CitizenNotificationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CitizenNotificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
