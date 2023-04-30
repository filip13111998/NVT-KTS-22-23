import { ChangeDetectorRef, Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { some } from 'd3';
import { MessageInterface } from 'src/app/model/MessageInterface';
import { ChatUsersMessageService } from 'src/app/services/chat-users-message.service';
import { ChatService } from 'src/app/shared/chat.service';
import { WebSocketAPI } from 'src/app/websocket/websocket-chat.service';


@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent {

  channel:string = '';

  usernames:string = '';

  selectedUser:string = '';

  messages : MessageInterface[] = [];

  // webSocketAPI: WebSocketAPI | undefined;

  messageForm = new FormGroup({
    message: new FormControl(''),
  });


  constructor(private chatService:ChatService , private chatUsersMessage : ChatUsersMessageService,
    private webSocketAPI: WebSocketAPI){
    this.setChannel();

    this.chatService.chat_receive_messages_subscriber$.subscribe((data:MessageInterface) =>{
      if(data.sender == this.selectedUser || data.sender == 'ADMIN'){
        // return;
        this.messages = [...this.messages , data]
      }
      // this.messages = [...this.messages , data]
    });


    this.chatService.chat_username_subscriber$.subscribe((username:string) =>{
      this.selectedUser = username;
      console.log(this.selectedUser)
      chatUsersMessage.getAllCitizenMessages(username).subscribe((data:MessageInterface[]) =>{

        if(username === ''){
          return;
        }

        if(data.length === 0){
          chatUsersMessage.getAllDriverMessages(username).subscribe((data:MessageInterface[]) =>{
            this.messages = data;
            return;
          });
        }

        this.messages = data;
      });
    });

  }

  ngOnInit() {

  }



  public sentMessage(){
    console.log(this.messageForm.value.message);

    let mes : MessageInterface = {
      sender: '',
      receiver: '',
      channel:'',
      message: this.messageForm.value.message + '',
      date: new Date().getTime(),
    }

    if(this.channel === 'ADMIN'){
      mes.sender='ADMIN';
      mes.receiver = this.selectedUser;
      mes.channel = this.channel;
    }
    else{
      mes.sender=this.usernames;
      mes.receiver = 'ADMIN';
      mes.channel = this.channel;
    }
    // if(mes.sender !== 'ADMIN'){
    //   this.messages = [...this.messages , mes];
    // }
    this.messages = [...this.messages , mes];
    // this.messageForm.value.message = '';
    this.messageForm.reset();
    this.chatService.sentMessage(mes);
  }



  public setChannel(){
    let token = localStorage.getItem('user_token');
    if (token == null) {
      return;
    }

    this.usernames = JSON.parse(atob(token.split('.')[1]))['sub'];

    this.channel = this.usernames.split('@')[0];

    let my_roles = JSON.parse(atob(token.split('.')[1]))['roles'].split(',');

    console.log(my_roles);
    if (my_roles.includes('ROLE_ADMIN')) { //ROLE CITIZEN
      // this.router.navigate(['/', 'citizen-home']);
      this.channel = 'ADMIN';
      this.chatService.sentChannel('ADMIN');
    }
    else if(my_roles.includes('ROLE_DRIVER')){
      this.chatService.sentChannel(this.channel);
      this.chatUsersMessage.getAllDriverMessages(this.channel).subscribe((data:MessageInterface[]) =>{
        this.messages = data;
        return;
      });
    }
    else{
      this.chatService.sentChannel(this.channel);
      this.chatUsersMessage.getAllCitizenMessages(this.channel).subscribe((data:MessageInterface[]) =>{
        this.messages = data;

      });
    }

  }

}
