import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { HistoryInterface } from '../model/HistoryInterface';
import { HistoryDetailViewInterface } from '../model/HistoryDetailViewInterface';
import { MarkInterface } from '../model/MarkInterface';

@Injectable({
  providedIn: 'root'
})
export class HistoryService {

  private path_history = "http://localhost:8080/api/history"
  private path_favorite = "http://localhost:8080/api/favorite"
  private path_mark = "http://localhost:8080/api/mark"

  constructor(private http: HttpClient) { }

  //CITIZEN
  sortByNameCitizen(username:string):Observable<HistoryInterface[]> {
    return this.http.get<HistoryInterface[]>( this.path_history + `/sort/citizen/name/${username}`);
  }

  sortByStartDateCitizen(username:string):Observable<HistoryInterface[]> {
    return this.http.get<HistoryInterface[]>( this.path_history + `/sort/citizen/start/${username}`);
  }

  sortByEndDateCitizen(username:string):Observable<HistoryInterface[]> {
    return this.http.get<HistoryInterface[]>( this.path_history + `/sort/citizen/end/${username}`);
  }

  sortByPriceCitizen(username:string):Observable<HistoryInterface[]> {
    return this.http.get<HistoryInterface[]>( this.path_history + `/sort/citizen/price/${username}`);
  }

  //DRIVER
  sortByNameDriver(username:string):Observable<HistoryInterface[]> {
    return this.http.get<HistoryInterface[]>( this.path_history + `/sort/driver/name/${username}`);
  }

  sortByStartDateDriver(username:string):Observable<HistoryInterface[]> {
    return this.http.get<HistoryInterface[]>( this.path_history + `/sort/driver/start/${username}`);
  }

  sortByEndDateDriver(username:string):Observable<HistoryInterface[]> {
    return this.http.get<HistoryInterface[]>( this.path_history + `/sort/driver/end/${username}`);
  }

  sortByPriceDriver(username:string):Observable<HistoryInterface[]> {
    return this.http.get<HistoryInterface[]>( this.path_history + `/sort/driver/price/${username}`);
  }

  //ADMIN
  sortByNameCitizenAdmin():Observable<HistoryInterface[]> {
    return this.http.get<HistoryInterface[]>( this.path_history + `/sort/citizen/admin/name`);
  }

  sortByStartDateCitizenAdmin():Observable<HistoryInterface[]> {
    return this.http.get<HistoryInterface[]>( this.path_history + `/sort/citizen/admin/start`);
  }

  sortByEndDateCitizenAdmin():Observable<HistoryInterface[]> {
    return this.http.get<HistoryInterface[]>( this.path_history + `/sort/citizen/admin/end`);
  }

  sortByPriceCitizenAdmin():Observable<HistoryInterface[]> {
    return this.http.get<HistoryInterface[]>( this.path_history + `/sort/citizen/admin/price`);
  }

  sortByNameDriverAdmin():Observable<HistoryInterface[]> {
    return this.http.get<HistoryInterface[]>( this.path_history + `/sort/driver/admin/name`);
  }

  sortByStartDateDriverAdmin():Observable<HistoryInterface[]> {
    return this.http.get<HistoryInterface[]>( this.path_history + `/sort/driver/admin/start`);
  }

  sortByEndDateDriverAdmin():Observable<HistoryInterface[]> {
    return this.http.get<HistoryInterface[]>( this.path_history + `/sort/driver/admin/end`);
  }

  sortByPriceDriverAdmin():Observable<HistoryInterface[]> {
    return this.http.get<HistoryInterface[]>( this.path_history + `/sort/driver/admin/price`);
  }


  addRideToFavorite(username:string,id:string):Observable<HistoryInterface[]> {
    return this.http.get<HistoryInterface[]>( this.path_favorite + `/add/${username}/${id}`);
  }

  getRide(id:string):Observable<HistoryDetailViewInterface> {
    return this.http.get<HistoryDetailViewInterface>( this.path_history + `/get/${id}`);
  }

  mark(entity:MarkInterface):Observable<boolean> {
    return this.http.post<boolean>( this.path_mark + `/citizens`,entity);
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
