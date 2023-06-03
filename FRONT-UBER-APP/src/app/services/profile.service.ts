import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { CitizenProfileInterface } from '../model/CitizenProfileInterface';
import { DriverProfileInterface } from '../model/DriverProfileInterface';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private path = "http://localhost:8080/auth"

  constructor(private http: HttpClient) { }

  updateCitizen(entity:CitizenProfileInterface):Observable<boolean> {
    const formData = new FormData();
    formData.append('user', JSON.stringify(entity));
    formData.append('image', '');

    // const httpOptions = {
    //   headers: new HttpHeaders({
    //     'Content-Type': 'multipart/form-data'
    //   })
    // };


    return this.http.post<boolean>( this.path + `/update-citizen`, formData);
  }

  getCitizen(username:string):Observable<CitizenProfileInterface> {
    return this.http.get<CitizenProfileInterface>( this.path + `/get-citizen/${username}`);
  }

  updateDriver(entity:DriverProfileInterface):Observable<boolean> {
    const formData = new FormData();
    formData.append('user', JSON.stringify(entity));

    // const httpOptions = {
    //   headers: new HttpHeaders({
    //     'Content-Type': 'multipart/form-data'
    //   })
    // };


    return this.http.post<boolean>( this.path + `/update-driver`, formData);
  }

  getDriver(username:string):Observable<DriverProfileInterface> {
    return this.http.get<DriverProfileInterface>( this.path + `/get-driver/${username}`);
  }

  getDriverProfiles():Observable<DriverProfileInterface[]> {
    return this.http.get<DriverProfileInterface[]>( this.path + `/get-drivers/`);
  }

  adminAcceptDriverUpdate(username:string):Observable<boolean> {
    return this.http.get<boolean>( this.path + `/admin-accept-driver-profile/${username}`);
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
