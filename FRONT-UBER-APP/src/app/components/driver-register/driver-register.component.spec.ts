import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DriverRegisterComponent } from './driver-register.component';
import { AuthService } from 'src/app/services/auth.service';
import { ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';

describe('DriverRegisterComponent', () => {
  let component: DriverRegisterComponent;
  let fixture: ComponentFixture<DriverRegisterComponent>;
  let authService: jasmine.SpyObj<AuthService>;

  beforeEach(async () => {
    const authServiceSpy = jasmine.createSpyObj('AuthService', ['registerDriver']);

    await TestBed.configureTestingModule({
      declarations: [DriverRegisterComponent],
      imports: [ReactiveFormsModule],
      providers: [{ provide: AuthService, useValue: authServiceSpy }]
    }).compileComponents();

    fixture = TestBed.createComponent(DriverRegisterComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService) as jasmine.SpyObj<AuthService>;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should register a driver', () => {
    const registerData = {
      username: 'testuser',
      password: 'testpassword',
      confirm: 'testpassword',
      firstName: 'John',
      lastName: 'Doe',
      city: 'Sample City',
      phone: '1234567890',
      name: 'Driver Name',
      type: 'CAR',
      petFriendly: 'Yes',
      babyFriendly: 'Yes'
    };

    const expectedDriverData = {
      username: 'testuser',
      password: 'testpassword',
      confirm: 'testpassword',
      firstName: 'John',
      lastName: 'Doe',
      city: 'Sample City',
      phone: '1234567890',
      name: 'Driver Name',
      type: 'CAR',
      petFriendly: false,
      babyFriendly: false
    };

    authService.registerDriver.and.returnValue(of({}));

    component.driverForm.setValue(registerData);
    component.registerDriver();


    expect(authService.registerDriver).toHaveBeenCalledWith(expectedDriverData);
    expect(component.passwordFlag).toBeFalse();
  });

  it('should set passwordFlag to true when confirm password is incorrect', () => {
    const registerData = {
      username: 'testuser',
      password: 'testpassword',
      confirm: 'incorrectpassword',
      firstName: 'John',
      lastName: 'Doe',
      city: 'Sample City',
      phone: '1234567890',
      name: 'Driver Name',
      type: 'CAR',
      petFriendly: 'Yes',
      babyFriendly: 'Yes'
    };

    component.driverForm.setValue(registerData);
    component.registerDriver();

    expect(component.passwordFlag).toBeTrue();
    expect(authService.registerDriver).not.toHaveBeenCalled();
  });

});
