import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { BlockInterface } from '../model/BlockInterface';

@Injectable({
  providedIn: 'root'
})
export class BlockService {

  private path = "http://localhost:8080/api/block"
  private path_citizen = "http://localhost:8080/api/citizen"

  constructor(private http: HttpClient) { }

  findAllCitizens():Observable<BlockInterface[]> {
    return this.http.get<BlockInterface[]>( this.path + '/citizens');
  }

  findAllDrivers():Observable<BlockInterface[]> {
    return this.http.get<BlockInterface[]>( this.path + '/drivers');
  }

  blockCitizen(username: string , comment:string):Observable<Boolean> {
    return this.http.get<Boolean>( this.path + `/citizen/${username}/${comment}` );
  }

  blockDriver(username: string , comment:string):Observable<Boolean> {
    return this.http.get<Boolean>( this.path + `/driver/${username}/${comment}` );
  }

  isCitizenBlock(username: string ):Observable<boolean> {
    return this.http.get<boolean>( this.path_citizen + `/isBlock/${username}` );
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
