import { Injectable } from '@angular/core';
import { VehiclesMapViewInterface } from '../model/VehiclesMapViewInterface';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {

  private vehicles = new BehaviorSubject<VehiclesMapViewInterface[]>([]);

  vehicles_subscriber$ = this.vehicles.asObservable();

  constructor() { }

  sentVehicles(vehicles: VehiclesMapViewInterface[]): void {

    this.vehicles.next(vehicles);
  }

}
