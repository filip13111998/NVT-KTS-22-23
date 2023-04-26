import { Injectable } from '@angular/core';
import { MarkerInterface } from '../model/MarkerInterface';
import { BehaviorSubject } from 'rxjs';
import { RoutePartExtendInterface } from '../model/RoutePartExtendInterface';

@Injectable({
  providedIn: 'root'
})
export class MapService {

  private delete_markers = new BehaviorSubject<void>(undefined);
  delete_markers_subscriber$ = this.delete_markers.asObservable();

  private route_parts = new BehaviorSubject<RoutePartExtendInterface[]>([]);
  route_parts_subscriber$ = this.route_parts.asObservable();

  private change_part = new BehaviorSubject<string>('');
  change_part_subscriber$ = this.change_part.asObservable();

  private get_parts = new BehaviorSubject<void>(undefined);
  get_parts_subscriber$ = this.get_parts.asObservable();

  private routing_controls = new BehaviorSubject<any[]>([]);
  routing_controls$ = this.routing_controls.asObservable();

  constructor() { }

  deleteLocationMarkers(): void {
    this.delete_markers.next();
  }

  sendRouteParts(routePartExtendInterface:RoutePartExtendInterface[]){
    this.route_parts.next(routePartExtendInterface);
  }

  changeRoutePart(index:string){
    this.change_part.next(index);
  }

  getRouteParts(){
    this.get_parts.next();
  }

  getRoutingControls(data:any[]){
    this.routing_controls.next(data);
  }

}
