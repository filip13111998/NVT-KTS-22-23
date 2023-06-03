import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { ReportRideInterface } from '../model/ReportRideInterface';

@Injectable({
  providedIn: 'root'
})
export class GraphService {

  private path = "http://localhost:8080/api/report"

  constructor(private http: HttpClient) { }

  rideNumber(start:string , end:string , username:string , role:string):Observable<ReportRideInterface> {
    return this.http.get<ReportRideInterface>( this.path + `/number/${start}/${end}/${username}/${role}`);
  }

  rideMeters(start:string , end:string , username:string , role:string):Observable<ReportRideInterface> {
    return this.http.get<ReportRideInterface>( this.path + `/meters/${start}/${end}/${username}/${role}`);
  }

  ridePrice(start:string , end:string , username:string , role:string):Observable<ReportRideInterface> {
    return this.http.get<ReportRideInterface>( this.path + `/price/${start}/${end}/${username}/${role}`);
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
