import { Component,ViewChild, ElementRef,Renderer2  } from '@angular/core';
import { map } from 'rxjs';
import { RoutePartExtendInterface } from 'src/app/model/RoutePartExtendInterface';
import { MapService } from 'src/app/shared/map.service';

@Component({
  selector: 'app-citizen-home-routes-view',
  templateUrl: './citizen-home-routes-view.component.html',
  styleUrls: ['./citizen-home-routes-view.component.css']
})
export class CitizenHomeRoutesViewComponent {

  @ViewChild('routes', { static: true }) container: any;

  clickedDivId: string = "";

  routePartExtendInterface:RoutePartExtendInterface[] = [];

  totalAmount: number = 0;

  totalLength: number = 0;

  constructor(private mapService : MapService,private renderer: Renderer2){
    this.mapService.route_parts_subscriber$.subscribe(data => {
      this.routePartExtendInterface = [];
      // console.log('DATA IS ' + data.length)
      this.routePartExtendInterface = data;
      this.setTotalAmount();
      this.setTotalLength();
    });
  }

  onDivClick(id: string) {
    // console.log(`Clicked div with id: ${id}`);
    let index : number = id.split('_')[2]==='0' ? 1: 0;
    let sibiling_index :string = id.split('_')[0] + '_' + id.split('_')[1] + '_' + index;

    const element = this.routePartExtendInterface.find((e) => e.id === id);
    const sibillingElement = this.routePartExtendInterface.find((e) => e.id === sibiling_index);

    if (element){
      element.selected= true;
    }
    if(sibillingElement){
      sibillingElement.selected= false;
    }

    this.setTotalAmount();
    this.setTotalLength();

    this.mapService.changeRoutePart(id);
  }

  public setTotalAmount(){
    this.totalAmount = this.routePartExtendInterface.reduce((sum, e) => {
      if (e.selected) {
        return sum + Number(e.time);
      } else {
        return sum;
      }
    }, 0);
  }

  public setTotalLength(){
    this.totalLength = this.routePartExtendInterface.reduce((sum, e) => {
      if (e.selected) {
        return sum + Number(e.length);
      } else {
        return sum;
      }
    }, 0);
  }


  public amount_route(){

    const num1List = this.routePartExtendInterface.map((rpei) => {
      const [id, num1, num2] = rpei.id.split('_');
      return Number(num1);
    });

    for (const counter of num1List) {
      // let index : number = rpei.id.split('_')[2]==='0' ? 1: 0;
      let first_index :string = 'ID' + '_' + counter + '_' + 0;
      let second_index :string =  'ID' + '_' + counter + '_' + 1;

      const first_element = this.routePartExtendInterface.find((e) => e.id === first_index);
      const second_element = this.routePartExtendInterface.find((e) => e.id === second_index);

      if(second_element?.time && first_element?.time){
        if(second_element.time > first_element.time){
          second_element.selected = false
          first_element.selected = true
          this.mapService.changeRoutePart(first_index);
          // this.totalLength += Number(second_element.length);
        }
        else{
          second_element.selected = true
          first_element.selected = false
          this.mapService.changeRoutePart(second_index);
          // this.totalLength += Number(first_element.length);
        }
        // this.mapService.changeRoutePart(first_index);
      }

    }
    this.setTotalAmount();
    this.setTotalLength();


  }

  public length_route(){

    const num1List = this.routePartExtendInterface.map((rpei) => {
      const [id, num1, num2] = rpei.id.split('_');
      return Number(num1);
    });

    for (const counter of num1List) {
      // let index : number = rpei.id.split('_')[2]==='0' ? 1: 0;
      let first_index :string = 'ID' + '_' + counter + '_' + 0;
      let second_index :string =  'ID' + '_' + counter + '_' + 1;

      const first_element = this.routePartExtendInterface.find((e) => e.id === first_index);
      const second_element = this.routePartExtendInterface.find((e) => e.id === second_index);

      if(second_element?.length && first_element?.length){
        if(second_element.length > first_element.length){
          second_element.selected = false
          first_element.selected = true
          this.mapService.changeRoutePart(first_index);
          // this.totalLength += Number(second_element.length);
        }
        else{
          second_element.selected = true
          first_element.selected = false
          this.mapService.changeRoutePart(second_index);
          // this.totalLength += Number(first_element.length);
        }
      }

    }
    this.setTotalAmount();
    this.setTotalLength();

  }

  public delete_location_markers(){
    this.routePartExtendInterface = [];
    this.mapService.deleteLocationMarkers();
    this.totalAmount = 0;
    this.totalLength = 0;
  }

}
