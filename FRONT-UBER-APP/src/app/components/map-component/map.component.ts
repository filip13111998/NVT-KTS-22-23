import { RoutePartExtendInterface } from './../../model/RoutePartExtendInterface';
import { MapService } from './../../shared/map.service';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import 'leaflet';
import { Marker } from 'leaflet';
// import 'leaflet';
import 'leaflet-routing-machine';

declare const L: any;

@Component({
  selector: 'app-map-component',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements AfterViewInit {

  private map!: L.Map;

  private location_markers = L.layerGroup(); // MARKERI KOJI PREDSTAVLJAJU TRENUTNU RUTU KOJU KORISNKI BIRA

  private vehicles_markers = L.layerGroup(); // MARKERI KOJI PREDSTAVLJAJU VOZILA NA MAPI

  private selected_routes : any[] = [];


  private icon = L.icon({
    iconUrl: 'assets/marker-icon.png',
    iconSize: [32, 32], // size of the icon
    iconAnchor: [16, 16], // point of the icon which will correspond to marker's location
    shadowUrl: undefined
  });


  constructor(private mapService: MapService){
    this.mapService.delete_markers_subscriber$.subscribe(data => {
      this.delete_location_markers();
    });
    this.mapService.change_part_subscriber$.subscribe((data:string) => {
      this.changeSelectedRoute(data);
    });

    this.mapService.get_parts_subscriber$.subscribe(data => {
      this.mapService.getRoutingControls(this.selected_routes);
    });
  }

  ngAfterViewInit(): void {
    Marker.prototype.options.icon = this.icon;
    this.map = L.map('map', {
      center: [45.2396, 19.8227],
      zoom: 11,
    });

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      attribution: 'OpenStreetMap'
    });

    tiles.addTo(this.map);

    this.map.on('click', (e: any) => {
      this.addMarkerToMap(e.latlng.lat , e.latlng.lng);

      // this.findAllRoutePaths();
      this.findLastRoutePaths();
    });

  }

  public findLastRoutePaths(){
    // this.removeContorlsFromMap();
    if (this.location_markers.getLayers().length === 1){
      return;
    }

    const marker1 = this.location_markers.getLayers()[this.location_markers.getLayers().length-2];
    const marker2 = this.location_markers.getLayers()[this.location_markers.getLayers().length-1];
    const waypoints = [
      L.latLng(marker1.getLatLng()),
      L.latLng(marker2.getLatLng())
    ];
    const routingControl = L.Routing.control({
      waypoints: waypoints,
      routeWhileDragging: true,
      draggableWaypoints: false,
      lineOptions: {
        styles: [{color: '#00aaff', weight: 5}]
      },
      altLineOptions: {
        styles: [{color: '#ff0000', weight: 5, dashArray: '10,10'}]
      }
    });
    routingControl.addTo(this.map);
    routingControl._container.style.display = "None";
    routingControl.myindex = 0;
    // routingControl.route();
    this.selected_routes.push(routingControl);

    // this.sendRouteParts();

    setTimeout(() => {
      this.sendRouteParts();
    },200)

  }

  public findAllRoutePaths(){
    this.selected_routes = [];
    // this.removeContorlsFromMap();
    if (this.location_markers.getLayers().length === 1){
      return;
    }
    for (let i = 0; i < this.location_markers.getLayers().length - 1; i++) {
      const marker1 = this.location_markers.getLayers()[i];
      const marker2 = this.location_markers.getLayers()[i + 1];
      const waypoints = [
        L.latLng(marker1.getLatLng()),
        L.latLng(marker2.getLatLng())
      ];
      const routingControl = L.Routing.control({
        waypoints: waypoints,
        routeWhileDragging: true,
        draggableWaypoints: false,
        lineOptions: {
          styles: [{color: '#00aaff', weight: 5}]
        },
        altLineOptions: {
          styles: [{color: '#ff0000', weight: 5, dashArray: '10,10'}]
        }
      });
      routingControl.addTo(this.map);
      routingControl._container.style.display = "None";
      routingControl.route();
      this.selected_routes.push(routingControl);

    }
    // console.log(this.selected_routes);
    // this.sendRouteParts();
    // setTimeout(() => {
    //   this.sendRouteParts();
    // },1000)

  }

  public sendRouteParts(){
    // let rpei_list : RoutePartExtendInterface[][]  = [];
    let rpei_list_part : RoutePartExtendInterface[] = []
    for(let i = 0; i < this.selected_routes.length; i++){

      for(let j = 0; j < this.selected_routes[i]._routes.length ; j++){
        let selected_route = this.selected_routes[i]._routes[j];
        let sel = false;
        if(j===0){
          sel = true;
        }
        let rpei : RoutePartExtendInterface = {
          id:'ID_' + i + '_' + j,
          name: selected_route.name,
          length: selected_route.summary.totalDistance + '',
          time: selected_route.summary.totalTime + '',
          selected:sel
        };
        rpei_list_part.push(rpei);
      }
      // console.log(rpei_list_part);

    }
    this.mapService.sendRouteParts(rpei_list_part);

  }


  public addMarkerToMap(lat:number,lng: number){

    var mrk = L.marker([lat, lng] , {icon: this.icon}).addTo(this.location_markers);

    this.location_markers.addTo(this.map);

    mrk.dragging.disable();
  }

  public delete_vehicle_markers(){
    this.vehicles_markers.clearLayers();
  }

  public changeSelectedRoute(id:string){
    let route : number = Number(id.split('_')[1]);
    let part : number = Number(id.split('_')[2]);
    if(this.selected_routes[route] && this.selected_routes[route].options){
      // console.log(this.selected_routes[route]);
      if(part === 0){
        this.selected_routes[route].options.showAlternatives = false
        let lineOptions = {styles: [{color: "#ff0000", opacity: 1, weight: 5}]};
        this.selected_routes[route].options.lineOptions = lineOptions
        this.selected_routes[route].myindex = 0;
        this.selected_routes[route].route();
      }else{
        let lineOptions = {styles: [{opacity: 0}]};
        let altLineOptions = {styles: [{color: "#ff0000", opacity: 1, weight: 5}]};
        this.selected_routes[route].options.lineOptions = lineOptions
        this.selected_routes[route].options.showAlternatives = true
        this.selected_routes[route].options.altLineOptions = altLineOptions
        // this.selected_routes[route]._selectedRoute.routesIndex = 1;
        this.selected_routes[route].myindex = 1;
        this.selected_routes[route].route();
      }
    }
    // console.log(this.selected_routes);
  }

  public delete_location_markers(){
    this.location_markers.clearLayers();
    this.removeContorlsFromMap();

  }


  public removeContorlsFromMap(){

    // Iterate over each control in the this.selected_routes array and remove it from the map
    this.selected_routes.forEach(control => {
      control.remove();
    });

    // Clear the this.selected_routes array
    this.selected_routes = [];

    this.mapService.getRoutingControls(this.selected_routes);

  }

}
