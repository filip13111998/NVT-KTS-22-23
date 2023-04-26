import { MarkerInterface } from "./MarkerInterface";


export interface VehiclesMapViewInterface{
  id:number,
  licencePlate:string,
  vehicleType:string,
  occupied:boolean,
  location:MarkerInterface,
//  marker:L.Marker,
//  route:RouteInterface
}
