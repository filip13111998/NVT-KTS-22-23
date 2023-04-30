import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { MessageInterface } from '../model/MessageInterface';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private chat_receive_messages = new BehaviorSubject<MessageInterface>({
    sender: 'John',
    receiver: 'Mary',
    message: '',
    channel:'',
    date: new Date().getTime(),
  });

  chat_receive_messages_subscriber$ = this.chat_receive_messages.asObservable();

  private chat_sent_messages = new BehaviorSubject<MessageInterface>({
    sender: 'John',
    receiver: 'Mary',
    message: '',
    channel:'',
    date: new Date().getTime(),
  });

  chat_sent_messages_subscriber$ = this.chat_sent_messages.asObservable();

  //USERNAME
  private chat_username = new BehaviorSubject<string>('');

  chat_username_subscriber$ = this.chat_username.asObservable();

  //CHANNEL
  private chat_channel = new BehaviorSubject<string>('');

  chat_channel_subscriber$ = this.chat_channel.asObservable();

  receiveMessage(message: MessageInterface): void {
    this.chat_receive_messages.next(message);
  }

  sentMessage(message: MessageInterface): void {

    console.log("MESSAge   " + message.channel)
    this.chat_sent_messages.next(message);
  }

  sentUsername(username:string): void {
    this.chat_username.next(username);
  }

  sentChannel(username:string): void {
    this.chat_channel.next(username);
  }

  constructor() { }
}
