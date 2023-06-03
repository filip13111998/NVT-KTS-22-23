import { MarkDetailViewInterface } from "./MarkDetailViewInterface";
import { RoutePartInterface } from "./RoutePartInterface";

export interface HistoryDetailViewInterface{
  id: number,
  name:string,
  start:number,
  end:number,
  price:number,
  driverfirstName: string,
  driverLastName:string,
  marks:MarkDetailViewInterface[],
  routes:RoutePartInterface[],
  citizens:string[]
}
