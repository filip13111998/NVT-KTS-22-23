import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { RideSaveInterface } from '../model/RideSaveInterface';
import { RideNotificationInterface } from '../model/RideNotificationInterface';

@Injectable({
  providedIn: 'root'
})
export class RideService {

  private path = "http://localhost:8080/api/ride"
  private path_driver = "http://localhost:8080/api/driver"

  constructor(private http: HttpClient) { }

  save(entity:RideSaveInterface):Observable<boolean> {
    return this.http.post<boolean>( this.path + `/save`,entity);
  }

  newRide(username:string):Observable<RideNotificationInterface> {
    return this.http.get<RideNotificationInterface>( this.path + `/new/${username}`);
  }

  accept(username: string, id: number):Observable<boolean> {
    return this.http.get<boolean>( this.path + `/history/${username}/${id}`);
  }

  newDriverRide(username:string):Observable<RideNotificationInterface> {
    return this.http.get<RideNotificationInterface>( this.path_driver + `/new/${username}`);
  }

  acceptRide(username: string):Observable<boolean> {
    return this.http.get<boolean>( this.path_driver + `/start/${username}`);
  }

  rejectRide(username: string , message:string):Observable<boolean> {
    return this.http.get<boolean>( this.path_driver + `/reject/${username}/${message}`);
  }


  finishRide(username: string):Observable<boolean> {
    return this.http.get<boolean>( this.path_driver + `/finish/${username}`);
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
