import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CitizenCurrentRideComponent } from './citizen-current-ride.component';

describe('CitizenCurrentRideComponent', () => {
  let component: CitizenCurrentRideComponent;
  let fixture: ComponentFixture<CitizenCurrentRideComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CitizenCurrentRideComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CitizenCurrentRideComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
