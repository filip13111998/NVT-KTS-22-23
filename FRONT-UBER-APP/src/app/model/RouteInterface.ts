import { RoutePartInterface } from "./RoutePartInterface";

export interface RouteInterface{
  id:number;
  length:number;
  routeParts:RoutePartInterface[];
}
