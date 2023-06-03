import { MarkerInterface } from "./MarkerInterface";


export interface VehiclesMapViewInterface{
  id:number,
  name:string,
  type:string,
  petFriendly:boolean,
  babyFriendly:boolean,
  occupied:boolean,
  location:MarkerInterface,
//  marker:L.Marker,
//  route:RouteInterface
}
