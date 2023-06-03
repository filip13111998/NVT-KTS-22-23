import { RoutePartInterface } from "./RoutePartInterface";

export interface RideSaveInterface{
  name: string;
  pets: boolean;
  baby:boolean;
  car_type: string;
  price : number;
  distance: number;
  users:string[];
  minutes: number;
  routePartInterface:RoutePartInterface[];
}
