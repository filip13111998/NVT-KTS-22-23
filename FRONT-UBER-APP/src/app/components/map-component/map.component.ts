
import { Router } from '@angular/router';
import { RoutePartExtendInterface } from './../../model/RoutePartExtendInterface';
import { MapService } from './../../shared/map.service';
import { AfterViewInit, Component, OnDestroy, OnInit  } from '@angular/core';
import 'leaflet';
import { Marker } from 'leaflet';
// import 'leaflet';
import 'leaflet-routing-machine';
import { RoutePartInterface } from 'src/app/model/RoutePartInterface';
import { SharedHistoryService } from 'src/app/shared/history.service';
import { WebsocketVehicleService } from 'src/app/websocket/websocket-vehicle.service';
import { VehicleService } from 'src/app/shared/vehicle.service';
import { VehiclesMapViewInterface } from 'src/app/model/VehiclesMapViewInterface';

declare const L: any;

@Component({
  selector: 'app-map-component',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements AfterViewInit , OnDestroy ,OnInit {

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


  constructor(private router:Router,
            private mapService: MapService ,
            private sharedHistoryService : SharedHistoryService,
            private vehicleService:VehicleService,
            private websocketVehicleService: WebsocketVehicleService){


  }

  ngOnInit(): void{
    this.websocketVehicleService.connect();
    this.mapService.delete_markers_subscriber$.subscribe(data => {
      this.delete_location_markers();
    });
    this.mapService.change_part_subscriber$.subscribe((data:string) => {
      this.changeSelectedRoute(data);
    });

    this.mapService.get_parts_subscriber$.subscribe(data => {
      this.mapService.getRoutingControls(this.selected_routes);
    });

    this.sharedHistoryService.routes_subscriber$.subscribe(data => {
      // console.log(data);
      this.viewRoutesOnMap(data);
    });

    this.vehicleService.vehicles_subscriber$.subscribe(data => {
      // console.log(data);
      // console.log("DOLAZIM");
      // console.log(data);
      this.addNewVehicleOnMap(data);
    });
  }

  ngOnDestroy(): void {
    // Clear the this.selected_routes array
    this.selected_routes = [];
    this.sendRouteParts();
    if (this.map) {
      this.map.remove();
      // this.map = null;
    }

  }

  ngAfterViewInit(): void {


    Marker.prototype.options.icon = this.icon;
    this.map = L.map('mapica', {
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

    // this.router.navigate(['/','citizen-home']);
    // if(this.reload){
    //   window.location.reload();
    //   this.reload = false;
    // }
  }

  public addNewVehicleOnMap(vehiclesMapViewInterface:VehiclesMapViewInterface[]){
    this.delete_vehicle_markers();
    // this.vehicles_markers = L.layerGroup();
    // this.vehicles_markers.addTo(this.map);
    if (this.map) {
      // console.log("DODAJ NA MAPU");
      this.vehicles_markers.addTo(this.map);
    } else {
      return;
      // Handle the case when this.map is undefined or not yet initialized
      // You may choose to queue the markers and add them to the map later when it is ready
    }
    for(let vehicle of vehiclesMapViewInterface){
      // this.addMarkerToMap(vehicle.location.longitude , vehicle.location.latitude);
      let icon  = this.drawTaxiIcons(vehicle.type,vehicle.occupied)
      var mrk = L.marker([vehicle.location.longitude,vehicle.location.latitude] , {icon: icon}).addTo(this.vehicles_markers);
      console.log("MARKER");
      console.log(mrk);
      // this.location_markers.addTo(this.map);

      mrk.dragging.disable();
      // this.vehicles_markers.
      // indexes.push(route.id);
    }
  }

  public drawTaxiIcons(type:string , occupied:boolean) : any {

      let imageUlr = "assets/taxi-icons/";

      if(occupied){
        if(type==='CAR'){
          imageUlr += "taxi1.png";
        }
        else if(type==='MINI'){
          imageUlr += "taxi3.png";
        }
        else{
          imageUlr += "van3.png";
        }
      }
      else{
        if(type==='CAR'){
          imageUlr += "open3.png";
        }
        else if(type==='MINI'){
          imageUlr += "open3.png";
        }
        else{
          imageUlr += "vanopen2.png";
        }
      }

      let taxiIcon = L.icon({
        iconUrl: imageUlr,
        iconSize: [40, 40]
      })

      return taxiIcon;

  }


  public removeDuplciateMarkers(){
    // Create a set to store unique marker identifiers
    const markerIds = new Set();
    // Iterate through the layerGroup's markers
    this.location_markers.eachLayer((marker:L.Marker) => {
      const markerCoordinates = marker.getLatLng();
      const markerId = markerCoordinates.toString(); // You can use any unique identifier here

      // Check if the marker's identifier is already in the set
      if (markerIds.has(markerId)) {
        // Duplicate marker found, remove it from the layerGroup
        this.location_markers.removeLayer(marker);
      } else {
        // Unique marker, add its identifier to the set
        markerIds.add(markerId);
      }
    });
  }

  private flag = false;

  public viewRoutesOnMap(routes: RoutePartInterface[]){
    if(this.flag){
      return;
    }
    // let indexes = [];
    for(let route of routes){
      this.addMarkerToMap(route.coordinates[0].longitude , route.coordinates[0].latitude);
      this.addMarkerToMap(route.coordinates[1].longitude , route.coordinates[1].latitude);
      // indexes.push(route.id);
    }

    this.removeDuplciateMarkers();

    console.log("MARKERI ");
    console.log(this.location_markers.getLayers())
    console.log(this.location_markers.getLayers().length );
    this.flag= true
    this.selected_routes = [];
    // this.removeContorlsFromMap();
    if (this.location_markers.getLayers().length === 1){
      return;
    }
    // console.log(indexes);
    for (let i = 0; i < this.location_markers.getLayers().length - 1; i++) {
      console.log("ULECEM");
      let route = routes[i];
      setTimeout(() => {
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

        // console.log("IDENX:" + route.id);
        if(route !== undefined && route.id !== undefined){
          if(route.id === 1){
            let lineOptions = {styles: [{opacity: 0}]};
            let altLineOptions = {styles: [{color: "#ff0000", opacity: 1, weight: 5}]};
            routingControl.options.lineOptions = lineOptions
            routingControl.options.showAlternatives = true
            routingControl.options.altLineOptions = altLineOptions
            // this.selected_routes[route]._selectedRoute.routesIndex = 1;
            // this.selected_routes[route].myindex = 1;
            routingControl.route();
          }
        }
        // if(route.id === 0){
        //   routingControl.options.showAlternatives = false
        //   let lineOptions = {styles: [{color: "#ff0000", opacity: 1, weight: 5}]};
        //   routingControl.options.lineOptions = lineOptions
        //   // routingControl.myindex = 0;
        //   routingControl.route();
        // }else{
        //   let lineOptions = {styles: [{opacity: 0}]};
        //   let altLineOptions = {styles: [{color: "#ff0000", opacity: 1, weight: 5}]};
        //   routingControl.options.lineOptions = lineOptions
        //   routingControl.options.showAlternatives = true
        //   routingControl.options.altLineOptions = altLineOptions
        //   // this.selected_routes[route]._selectedRoute.routesIndex = 1;
        //   // this.selected_routes[route].myindex = 1;
        //   routingControl.route();
        // }
        routingControl.addTo(this.map);
        routingControl._container.style.display = "None";

      },1500)

      // routingControl.route();
      // this.selected_routes.push(routingControl);

    }

  }

  public findLastRoutePaths(){

    // this.removeContorlsFromMap();
    if (this.location_markers.getLayers().length === 1){
      return;
    }

    const marker1 = this.location_markers.getLayers()[this.location_markers.getLayers().length-2];
    const marker2 = this.location_markers.getLayers()[this.location_markers.getLayers().length-1];
    // console.log(marker1);
    // console.log(marker2);
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


    if (this.map) {
      this.location_markers.addTo(this.map);
    } else {
      return;
      // Handle the case when this.map is undefined or not yet initialized
      // You may choose to queue the markers and add them to the map later when it is ready
    }
    var mrk = L.marker([lat, lng] , {icon: this.icon}).addTo(this.location_markers);

    // this.location_markers.addTo(this.map);

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
