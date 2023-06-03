import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

import { Injectable } from '@angular/core';
import { VehicleService } from '../shared/vehicle.service';

@Injectable({
  providedIn: 'root'
})
export class WebsocketVehicleService {

  webSocketEndPoint: string = 'http://localhost:8080/ws';
    topic: string = "/topic/vehicle";
    stompClient: any;

    constructor(private vehicleService:VehicleService){
      console.log("POZVAN SOKET IZ KOTROLERA");
      this.connect();
    }


    connect() {
        console.log("Initialize WebSocket Connection");
        let ws = new SockJS(this.webSocketEndPoint);
        this.stompClient = Stomp.over(ws);
        this.stompClient.connect({},  (frame:any)=> {
          this.stompClient.subscribe(this.topic, (message:any)=> {
              console.log("SOKETI");
              this.vehicleService.sentVehicles(JSON.parse(message.body));
            });
            this.stompClient.reconnect_delay = 2000;
        });
    };
}
