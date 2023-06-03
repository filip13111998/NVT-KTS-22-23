import { Injectable } from '@angular/core';
import { NotificationService } from '../shared/notification.service';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class WebsocketCitizenNotificationsService {

  webSocketEndPoint: string = 'http://localhost:8080/ws';
  topic: string = "/topic/citizen/notification/";
  time: string = "/topic/citizen/notification/";
  end: string = "/topic/citizen/notification/";
  stompClient: any;
  stompClientTime: any;
  stompClientEnd: any;
    constructor(private notificationService:NotificationService){

    }


    connectStart(username : string) {
        console.log("Initialize WebSocket Connection");
        let ws = new SockJS(this.webSocketEndPoint);
        this.stompClient = Stomp.over(ws);
        this.stompClient.connect({},  (frame:any)=> {
          this.stompClient.subscribe(this.topic + username, (message:any)=> {
              console.log("SOKETI");
              this.notificationService.sentCitizenStart(JSON.parse(message.body));
            });
            this.stompClient.reconnect_delay = 2000;
        });
    };

    connectTime(username : string) {
      console.log("Initialize WebSocket Connection");
      let ws = new SockJS(this.webSocketEndPoint);
      this.stompClient = Stomp.over(ws);
      this.stompClient.connect({},  (frame:any)=> {
        this.stompClient.subscribe(this.time + username, (message:any)=> {
            console.log("SOKETI");
            this.notificationService.sentCitizenStart(JSON.parse(message.body));
          });
          this.stompClient.reconnect_delay = 2000;
      });
  };

  connectEnd(username : string) {
    console.log("Initialize WebSocket Connection");
    let ws = new SockJS(this.webSocketEndPoint);
    this.stompClient = Stomp.over(ws);
    this.stompClient.connect({},  (frame:any)=> {
      this.stompClient.subscribe(this.end + username, (message:any)=> {
          console.log("SOKETI");
          this.notificationService.sentCitizenStart(JSON.parse(message.body));
        });
        this.stompClient.reconnect_delay = 2000;
    });
  };

}
