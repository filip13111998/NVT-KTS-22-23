import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import { AuthService } from 'src/app/services/auth.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { TokenInterface } from 'src/app/model/TokenInterface';
import { of } from 'rxjs';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [AuthService],
      imports: [ HttpClientTestingModule, ReactiveFormsModule , RouterTestingModule],
      declarations: [LoginComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call login method on form submission', () => {
    spyOn(component, 'login');
    const loginButton = fixture.nativeElement.querySelector('.btn-primary');
    loginButton.click();
    expect(component.login).toHaveBeenCalled();
  });

  it('should navigate to citizen home on successful login with ROLE_CITIZEN', fakeAsync(() => {
    const token: TokenInterface = {
      accessToken: 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6ImpvaG4iLCJpYXQiOjE1MTYyMzkwMjIsInJvbGVzIjoiUk9MRV9DSVRJWkVOIn0.l8KSBL3pmJ1Zy3oBRDM5OdCUrH99jLOSYo4y0ZOvA7U',
      expiresIn: 3600,
    };
    spyOn(authService, 'login').and.returnValue(of(token));
    spyOn(router, 'navigate');

    component.login();
    tick(); // Wait for the asynchronous operation to complete

    expect(authService.login).toHaveBeenCalled();
    expect(router.navigate).toHaveBeenCalledWith(['/', 'citizen-home']);
  }));

  it('should navigate to driver home on successful login with ROLE_DRIVER', () => {
    const token: TokenInterface = {
      accessToken: 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6ImpvaG4iLCJpYXQiOjE1MTYyMzkwMjIsInJvbGVzIjoiUk9MRV9EUklWRVIifQ.1sFJxZqE4l0sRCza32lLq7etR9iaaAXHNdcWi1xyWa0',
      expiresIn: 3600,
    };
    spyOn(authService, 'login').and.returnValue(of(token));
    spyOn(router, 'navigate');

    component.login();

    expect(authService.login).toHaveBeenCalled();
    expect(router.navigate).toHaveBeenCalledWith(['/', 'driver-home']);
  });

  it('should navigate to admin home on successful login with ROLE_ADMIN', () => {
    const token: TokenInterface = {
      accessToken: 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6ImpvaG4iLCJpYXQiOjE1MTYyMzkwMjIsInJvbGVzIjoiUk9MRV9BRE1JTiJ9.rlvBTqJEtNbPqvEIdrqqcf_SCVDIELQvOaWVO67u6AU',
      expiresIn: 3600,
    };
    spyOn(authService, 'login').and.returnValue(of(token));
    spyOn(router, 'navigate');

    component.login();

    expect(authService.login).toHaveBeenCalled();
    expect(router.navigate).toHaveBeenCalledWith(['/', 'admin-home']);
  });


});
