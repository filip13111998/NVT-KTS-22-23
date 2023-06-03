import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { RoutePartInterface } from '../model/RoutePartInterface';

@Injectable({
  providedIn: 'root'
})
export class SharedHistoryService {

  private id = new BehaviorSubject<string>('');

  id_subscriber$ = this.id.asObservable();

  private routes = new Subject<RoutePartInterface[]>();

  routes_subscriber$ = this.routes.asObservable();

  constructor() { }

  sentHistoryDetailViewID(idRide: string): void {
    this.id.next(idRide);
  }

  sentHistoryDetailViewRoute(data: RoutePartInterface[]): void {
    this.routes.next(data);
  }

}
