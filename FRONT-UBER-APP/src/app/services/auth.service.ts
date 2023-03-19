import { DriverRegisterInterface } from './../model/DriverRegisterInterface';
import { CitizenRegisterInterface } from './../model/CitizenRegisterInterface';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginInterface } from '../model/LoginInterface';
import { Observable, throwError } from 'rxjs';
import { TokenInterface } from '../model/TokenInterface';
import { ResetPasswordInterface } from '../model/ResetPasswordInterface';


@Injectable({
  providedIn: 'root'
})
export class AuthService {


  private path = "http://localhost:8080/auth"

  constructor(private http: HttpClient) { }

  login(entity: LoginInterface):Observable<TokenInterface> {
    console.log('login request send');
    return this.http.post<TokenInterface>( this.path + '/login' , entity );
  }

  registerCitizen(entity: CitizenRegisterInterface) {
    console.log('register-citizen request send');
    return this.http.post( this.path + '/register-citizen' , entity );
  }

  registerDriver(entity: DriverRegisterInterface) {
    console.log('register-driver request send');
    return this.http.post( this.path + '/register-driver' , entity );
  }

  resetPasswordCitizen(entity: ResetPasswordInterface) {
    console.log('reset-password request send');
    return this.http.post( this.path + '/reset-citizen' , entity );
  }

  resetPasswordDriver(entity: ResetPasswordInterface) {
    console.log('reset-password request send');
    return this.http.post( this.path + '/reset-driver' , entity );
  }

  resetPasswordAdmin(entity: ResetPasswordInterface) {
    console.log('reset-password request send');
    return this.http.post( this.path + '/reset-admin' , entity );
  }


  error(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      errorMessage = error.error.message;
    } else {
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(() => {
      return errorMessage;
    });
  }

}
