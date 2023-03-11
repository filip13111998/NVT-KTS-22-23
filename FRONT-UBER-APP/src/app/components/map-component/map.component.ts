import { AfterViewInit, Component, OnInit } from '@angular/core';
import 'leaflet';
// import 'leaflet';
import 'leaflet-routing-machine';

declare const L: any;

@Component({
  selector: 'app-map-component',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements AfterViewInit {

  ngAfterViewInit(): void {
    const map = L.map('map', {
      center: [45.2396, 19.8227],
      zoom: 11,
    });

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      attribution: 'OpenStreetMap'
    });

    tiles.addTo(map);

    const marker = L.marker([45.2396, 19.8227]).addTo(map);
  }

}
