import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MessageInterface } from '../model/MessageInterface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChatUsersMessageService {

  public apiUrl: string = "http://localhost:8080/api/chat";

  headers = new HttpHeaders().set('Content-Type', 'application/json');


  constructor(private http: HttpClient) { }

  public getAllCitizens(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/citizens`, { headers: this.headers });
  }

  public getAllDrivers(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/drivers`, { headers: this.headers });
  }

  public getAllCitizenMessages(username:string): Observable<MessageInterface[]> {
    return this.http.get<MessageInterface[]>(`${this.apiUrl}/citizens/${username}`, { headers: this.headers });
  }

  public getAllDriverMessages(username:string): Observable<MessageInterface[]> {
    return this.http.get<MessageInterface[]>(`${this.apiUrl}/drivers/${username}`, { headers: this.headers });
  }

}
