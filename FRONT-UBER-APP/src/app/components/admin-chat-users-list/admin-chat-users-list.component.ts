import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ChatUsersMessageService } from 'src/app/services/chat-users-message.service';
import { ChatService } from 'src/app/shared/chat.service';

@Component({
  selector: 'app-admin-chat-users-list',
  templateUrl: './admin-chat-users-list.component.html',
  styleUrls: ['./admin-chat-users-list.component.css']
})
export class AdminChatUsersListComponent {

  users:string[] = [];

  isActive = true;

  activeUser: string = '';

  constructor(private chatUsersMessage : ChatUsersMessageService, private chat : ChatService) {

    if(window.location.href.includes('admin-citizen-chat')){
      chatUsersMessage.getAllCitizens().subscribe((data:string[])=> {
        this.users = data
      });
    }
    else{
      chatUsersMessage.getAllDrivers().subscribe(
        (data:string[])=> {this.users = data}
      );
    }

  }

  setActive(user: string) {
    this.activeUser = user;
    this.chat.sentUsername(user);
  }


}
