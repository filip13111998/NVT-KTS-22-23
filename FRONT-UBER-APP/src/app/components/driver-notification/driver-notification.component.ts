import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { RideNotificationInterface } from 'src/app/model/RideNotificationInterface';
import { RideService } from 'src/app/services/ride.service';
import { NotificationService } from 'src/app/shared/notification.service';
import { WebsocketDriverNotificationService } from 'src/app/websocket/driver-notification.service';

@Component({
  selector: 'app-driver-notification',
  templateUrl: './driver-notification.component.html',
  styleUrls: ['./driver-notification.component.css']
})
export class DriverNotificationComponent {

  isFinish = false;

  start = false;

  rideNotification : RideNotificationInterface = {
    "price":0,
    "text":"",
    "id":0
  };

  currentRide : RideNotificationInterface = {
    "price":0,
    "text":"",
    "id":0
  };

  messageForm = new FormGroup({
    message: new FormControl('')
  });

  constructor(private rideService : RideService ,
    private websocketDriverNotificationsService:WebsocketDriverNotificationService,
    private notificationService : NotificationService) {

   }

  ngOnInit(): void {
    this.newDriverRide();
    this.connectStart();
    this.notificationServiceSubscribe();
    this.loopStart();
  }

  public loopStart(){
    console.log('DOSAO');
    setTimeout(() => {
      // Set the flag to true after 1 minute
      this.start = true;
      console.log("Flag set to true.");
    }, 60000);
  }

  public notificationServiceSubscribe(): void {
    this.notificationService.notification_driver_subscriber$.subscribe((data:RideNotificationInterface)=>{
      this.currentRide = this.rideNotification;
      this.rideNotification = data;
    });
  }

  public newDriverRide(): void {

    let userToken = localStorage.getItem('user_token');

    if (userToken) {
      let parsedToken = JSON.parse(atob(userToken.split('.')[1]));
      let username = parsedToken['sub'];
      this.rideService.newDriverRide(username).subscribe((ride:RideNotificationInterface) => {
        this.rideNotification =ride;
        this.currentRide = this.rideNotification;
      });

    }

  }

  public connectStart(): void {
    let userToken = localStorage.getItem('user_token');

    if (userToken) {
      let parsedToken = JSON.parse(atob(userToken.split('.')[1]));
      let username = parsedToken['sub'];
      this.websocketDriverNotificationsService.connectStart(username);
    }


  }



  public acceptRide(): void {
    let userToken = localStorage.getItem('user_token');

    if (userToken) {
      let parsedToken = JSON.parse(atob(userToken.split('.')[1]));
      let username = parsedToken['sub'];
      this.rideService.acceptRide(username).subscribe((answer : boolean) => {
        if(answer){
          this.currentRide = this.rideNotification
          // this.rideNotification = {
          //   "price":0,
          //   "text":"",
          //   "id":0
          // };
        }
      });

    }
  }

  public rejectRide(): void {
    let userToken = localStorage.getItem('user_token');

    if (userToken) {
      let parsedToken = JSON.parse(atob(userToken.split('.')[1]));
      let username = parsedToken['sub'];
      this.rideService.rejectRide(username , this.messageForm.value.message!).subscribe((answer : boolean) => {
        if(answer){
          this.rideNotification = {
            "price":0,
            "text":"",
            "id":0
          };
        }
      });

    }
  }

  public finishRide(): void {
    let userToken = localStorage.getItem('user_token');

    if (userToken) {
      let parsedToken = JSON.parse(atob(userToken.split('.')[1]));
      let username = parsedToken['sub'];
      this.rideService.finishRide(username).subscribe((answer : boolean) => {
        // if(answer){
        //   this.rideNotification = {
        //     "price":0,
        //     "text":"",
        //     "id":0
        //   };
        // }
      });

    }
  }


}
