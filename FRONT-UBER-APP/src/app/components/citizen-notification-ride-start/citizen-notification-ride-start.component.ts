import { Component } from '@angular/core';
import { RideNotificationInterface } from 'src/app/model/RideNotificationInterface';
import { RideService } from 'src/app/services/ride.service';
import { SharedHistoryService } from 'src/app/shared/history.service';
import { NotificationService } from 'src/app/shared/notification.service';
import { WebsocketCitizenNotificationsService } from 'src/app/websocket/citizen-notifications.service';

@Component({
  selector: 'app-citizen-notification-ride-start',
  templateUrl: './citizen-notification-ride-start.component.html',
  styleUrls: ['./citizen-notification-ride-start.component.css']
})
export class CitizenNotificationRideStartComponent {


  rideNotification : RideNotificationInterface = {
    "price":0,
    "text":"",
    "id":0
  };

  constructor(private rideService : RideService ,
    private websocketCitizenNotificationsService:WebsocketCitizenNotificationsService,
    private notificationService : NotificationService,
    private sharedistoryService :SharedHistoryService) {

   }

  ngOnInit(): void {
    this.newRide();
    this.connectStart();
    this.notificationServiceSubscribe();
  }

  public notificationServiceSubscribe(): void {
    this.notificationService.notification_subscriber$.subscribe((data:RideNotificationInterface)=>{
      this.rideNotification = data;
      this.sharedistoryService.sentHistoryDetailViewID(this.rideNotification.id + '');
    });
  }

  public connectStart(): void {
    let userToken = localStorage.getItem('user_token');

    if (userToken) {
      let parsedToken = JSON.parse(atob(userToken.split('.')[1]));
      let username = parsedToken['sub'].trim().split('@')[0];
      this.websocketCitizenNotificationsService.connectStart(username);
    }


  }

  public newRide(): void {

    let userToken = localStorage.getItem('user_token');

    if (userToken) {
      let parsedToken = JSON.parse(atob(userToken.split('.')[1]));
      let username = parsedToken['sub'];
      this.rideService.newRide(username).subscribe((ride:RideNotificationInterface) => {
        this.rideNotification =ride;
        this.sharedistoryService.sentHistoryDetailViewID(this.rideNotification.id + '');
      });

    }

  }

  public acceptRide(): void {
    console.log("KLIK");
    let userToken = localStorage.getItem('user_token');

    if (userToken) {
      let parsedToken = JSON.parse(atob(userToken.split('.')[1]));
      let username = parsedToken['sub'];
      this.rideService.accept(username , this.rideNotification.id).subscribe((answer : boolean) => {
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

}
