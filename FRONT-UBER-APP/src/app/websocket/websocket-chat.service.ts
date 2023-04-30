import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { ChatService } from '../shared/chat.service';
import { MessageInterface } from '../model/MessageInterface';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class WebSocketAPI {
    webSocketEndPoint: string = 'http://localhost:8080/ws';
    topic: string = "/topic";
    stompClient: any;

    constructor( private chatService:ChatService){
      this.chatService.chat_channel_subscriber$.subscribe((channel:string)=>{

        if(channel === ''){
          return;
        }

        this.topic = '/topic/' + channel;
        console.log(this.topic);
      });

      this.chatService.chat_sent_messages_subscriber$.subscribe((data:MessageInterface)=>{
        if(data.message === ''){
          return;
        }

        let chatName = data.receiver;

        // if(data.sender ===  'ADMIN'){
        //   let copy = data;
        //   copy.receiver = 'ADMIN';
        //   this.stompClient.send(`/app/chat/admin`, {}, JSON.stringify(data));
        // }

        if(window.location.href.includes('admin-citizen-chat') || window.location.href.includes('citizen-chat')){
          this.stompClient.send(`/app/chat/citizen/${chatName}`, {}, JSON.stringify(data));
          // if(data.sender ===  'ADMIN'){
          //   let copy = data;
          //   copy.receiver = 'ADMIN';
          //   this.stompClient.send(`/app/chat/admin`, {}, JSON.stringify(data));
          // }
        }else {
          console.log("SALJI");
          this.stompClient.send(`/app/chat/driver/${chatName}`, {}, JSON.stringify(data));
          // if(data.sender ===  'ADMIN'){
          //   let copy = data;
          //   copy.receiver = 'ADMIN';
          //   this.stompClient.send(`/app/chat/admin`, {}, JSON.stringify(data));
          // }
        }

      });
      this.connect();

    }


    connect() {
        console.log("Initialize WebSocket Connection");
        let ws = new SockJS(this.webSocketEndPoint);
        this.stompClient = Stomp.over(ws);
        this.stompClient.connect({},  (frame:any)=> {
          this.stompClient.subscribe(this.topic, (message:any)=> {
            // this.onMessageReceived(message);
              console.log("PRISTIGLA")
              console.log(message)
              this.chatService.receiveMessage(JSON.parse(message.body));
            });
            this.stompClient.reconnect_delay = 2000;
        });
    };

}
