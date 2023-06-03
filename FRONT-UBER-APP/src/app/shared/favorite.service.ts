import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedFavoriteService {

  private id = new BehaviorSubject<string>('');

  id_subscriber$ = this.id.asObservable();

  constructor() { }

  sentID(ID: string): void {
    this.id.next(ID);
  }
}
