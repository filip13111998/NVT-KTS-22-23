import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { FavoriteInterface } from '../model/FavoriteInterface';

@Injectable({
  providedIn: 'root'
})
export class FavoriteService {

  private path_favorite = "http://localhost:8080/api/favorite"
  private path_history = "http://localhost:8080/api/history"

  constructor(private http: HttpClient) { }

  getAllFavorite(username:string):Observable<FavoriteInterface[]> {
    return this.http.get<FavoriteInterface[]>( this.path_favorite + `/all/${username}`);
  }

  cloneRide(id: number , minutes:string):Observable<Boolean> {
    return this.http.get<Boolean>( this.path_history + `/sort/clone/${id}/${minutes}` );
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
