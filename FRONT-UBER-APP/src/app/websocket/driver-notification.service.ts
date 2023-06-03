import { Injectable } from '@angular/core';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { NotificationService } from '../shared/notification.service';

@Injectable({
  providedIn: 'root'
})
export class WebsocketDriverNotificationService {

  webSocketEndPoint: string = 'http://localhost:8080/ws';
  topic: string = "/topic/driver/notification/";
  stompClient: any;

  constructor(private notificationService:NotificationService){}


  connectStart(username : string) {
      console.log("Initialize WebSocket Connection");
      let ws = new SockJS(this.webSocketEndPoint);
      this.stompClient = Stomp.over(ws);
      this.stompClient.connect({},  (frame:any)=> {
        this.stompClient.subscribe(this.topic + username, (message:any)=> {
            console.log("SOKETI");
            this.notificationService.sentDriverStart(JSON.parse(message.body));
          });
          this.stompClient.reconnect_delay = 2000;
      });
  };
}
