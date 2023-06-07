import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CitizenRegisterComponent } from './citizen-register.component';
import { AuthService } from 'src/app/services/auth.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

describe('CitizenRegisterComponent', () => {
  let component: CitizenRegisterComponent;
  let fixture: ComponentFixture<CitizenRegisterComponent>;
  let authService: jasmine.SpyObj<AuthService>;

  beforeEach(async () => {
    const authServiceSpy = jasmine.createSpyObj('AuthService', ['registerCitizen']);

    await TestBed.configureTestingModule({
      declarations: [CitizenRegisterComponent],
      imports: [ReactiveFormsModule],
      providers: [{ provide: AuthService, useValue: authServiceSpy }]
    }).compileComponents();

    fixture = TestBed.createComponent(CitizenRegisterComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService) as jasmine.SpyObj<AuthService>;

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should register a citizen', () => {
    const registerData = {
      username: 'testuser',
      password: 'testpassword',
      confirm: 'testpassword',
      firstName: 'John',
      lastName: 'Doe',
      city: 'Sample City',
      phone: '1234567890'
    };

    authService.registerCitizen.and.returnValue(of({}));

    component.citizenForm.setValue(registerData);
    component.registerCitizen();

    expect(authService.registerCitizen).toHaveBeenCalledWith(registerData);
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
      phone: '1234567890'
    };

    component.citizenForm.setValue(registerData);
    component.registerCitizen();

    expect(component.passwordFlag).toBeTrue();
    expect(authService.registerCitizen).not.toHaveBeenCalled();
  });

});
