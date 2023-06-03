import { Component } from '@angular/core';
import { RideNotificationInterface } from 'src/app/model/RideNotificationInterface';
import { NotificationService } from 'src/app/shared/notification.service';
import { WebsocketCitizenNotificationsService } from 'src/app/websocket/citizen-notifications.service';

@Component({
  selector: 'app-citizen-notification-ride-time',
  templateUrl: './citizen-notification-ride-time.component.html',
  styleUrls: ['./citizen-notification-ride-time.component.css']
})
export class CitizenNotificationRideTimeComponent {

  rideNotification : RideNotificationInterface = {
    "price":0,
    "text":"",
    "id":0
  };

  constructor(
    private websocketCitizenNotificationsService:WebsocketCitizenNotificationsService,
    private notificationService : NotificationService) {

   }

  ngOnInit(): void {
    this.connectTime();
    this.notificationServiceSubscribe();
  }

  public notificationServiceSubscribe(): void {
    this.notificationService.end_subscriber$.subscribe((data:RideNotificationInterface)=>{
      this.rideNotification = data;
    });
  }

  public connectTime(): void {
    let userToken = localStorage.getItem('user_token');

    if (userToken) {
      let parsedToken = JSON.parse(atob(userToken.split('.')[1]));
      let username = parsedToken['sub'];
      this.websocketCitizenNotificationsService.connectTime(username);
    }


  }

}
