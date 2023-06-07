import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AuthService } from './auth.service';
import { TokenInterface } from '../model/TokenInterface';
import { LoginInterface } from '../model/LoginInterface';
import { HttpErrorResponse } from '@angular/common/http';
import { CitizenRegisterInterface } from '../model/CitizenRegisterInterface';
import { DriverRegisterInterface } from '../model/DriverRegisterInterface';

describe('AuthService', () => {
  // let service: AuthService;

  // beforeEach(() => {
  //   TestBed.configureTestingModule({});
  //   service = TestBed.inject(AuthService);
  // });

  // it('should be created', () => {
  //   expect(service).toBeTruthy();
  // });

  let authService: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService],
    });
    authService = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(authService).toBeTruthy();
  });

  it('should send a login request and return a token', () => {
    const dummyLogin: LoginInterface = {
      username: 'testuser',
      password: 'testpassword',
    };
    const dummyToken: TokenInterface = {
      accessToken: 'dummy-token',
      expiresIn: 3600,
    };

    authService.login(dummyLogin).subscribe((token: TokenInterface) => {
      expect(token).toEqual(dummyToken);
    });

    const req = httpMock.expectOne('http://localhost:8080/auth/login');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(dummyLogin);
    req.flush(dummyToken);
  });

  it('should handle an error response', () => {
    const dummyLogin: LoginInterface = {
      username: 'testuser',
      password: 'testpassword',
    };
    const errorMessage = '';

    authService.login(dummyLogin).subscribe(
      () => {
        fail('Expected an error, but the request succeeded');
      },
      (error: HttpErrorResponse) => {
        expect(error.error.message).toEqual(errorMessage);
        expect(error.status).toEqual(500);
      }
    );

    const req = httpMock.expectOne('http://localhost:8080/auth/login');
    expect(req.request.method).toBe('POST');
    req.error(new ErrorEvent('Test error'), { status: 500, statusText: errorMessage });
  });

  it('should send a POST request to register a citizen', () => {
    const dummyCitizen: CitizenRegisterInterface = {
      username: 'testuser',
      password: 'testpassword',
      confirm: 'testpassword',
      firstName: 'John',
      lastName: 'Doe',
      city: 'New York',
      phone: '1234567890',
    };

    authService.registerCitizen(dummyCitizen).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/auth/register-citizen');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(dummyCitizen);

    req.flush({});
  });

  it('should handle an error response when registering a citizen', () => {
    const dummyCitizen: CitizenRegisterInterface = {
      username: 'testuser',
      password: 'testpassword',
      confirm: 'testpassword',
      firstName: 'John',
      lastName: 'Doe',
      city: 'New York',
      phone: '1234567890',
    };
    const errorMessage = '';

    authService.registerCitizen(dummyCitizen).subscribe(
      () => {
        fail('Expected an error, but the request succeeded');
      },
      (error: HttpErrorResponse) => {
        expect(error.error.message).toEqual(errorMessage);
        expect(error.status).toEqual(500);
      }
    );

    const req = httpMock.expectOne('http://localhost:8080/auth/register-citizen');
    expect(req.request.method).toBe('POST');
    req.error(new ErrorEvent('Test error'), { status: 500, statusText: errorMessage });
  });

  it('should send a POST request to register a driver', () => {
    const dummyDriver: DriverRegisterInterface = {
      username: 'testuser',
      password: 'testpassword',
      confirm: 'testpassword',
      firstName: 'John',
      lastName: 'Doe',
      city: 'New York',
      phone: '1234567890',
      name: 'Test Driver',
      type: 'Car',
      petFriendly: true,
      babyFriendly: true,
    };

    authService.registerDriver(dummyDriver).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/auth/register-driver');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(dummyDriver);

    req.flush({});
  });


  it('should handle an error response when registering a driver', () => {
    const dummyDriver: DriverRegisterInterface = {
      username: 'testuser',
      password: 'testpassword',
      confirm: 'testpassword',
      firstName: 'John',
      lastName: 'Doe',
      city: 'New York',
      phone: '1234567890',
      name: 'Test Driver',
      type: 'Car',
      petFriendly: true,
      babyFriendly: true,
    };
    const errorMessage = '';

    authService.registerDriver(dummyDriver).subscribe(
      () => {
        fail('Expected an error, but the request succeeded');
      },
      (error: HttpErrorResponse) => {
        expect(error.error.message).toEqual(errorMessage);
        expect(error.status).toEqual(500);
      }
    );

    const req = httpMock.expectOne('http://localhost:8080/auth/register-driver');
    expect(req.request.method).toBe('POST');
    req.error(new ErrorEvent('Test error'), { status: 500, statusText: errorMessage });
  });


});
