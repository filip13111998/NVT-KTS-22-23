import { Injectable } from '@angular/core';
import { RideNotificationInterface } from '../model/RideNotificationInterface';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private notification = new BehaviorSubject<RideNotificationInterface>(
    {
      "price":0,
      "text":"",
      "id":0
    }
  );

  notification_subscriber$ = this.notification.asObservable();

  private time = new BehaviorSubject<RideNotificationInterface>(
    {
      "price":0,
      "text":"",
      "id":0
    }
  );

  time_subscriber$ = this.time.asObservable();

  private end = new BehaviorSubject<RideNotificationInterface>(
    {
      "price":0,
      "text":"",
      "id":0
    }
  );

  end_subscriber$ = this.end.asObservable();

  private notification_driver = new BehaviorSubject<RideNotificationInterface>(
    {
      "price":0,
      "text":"",
      "id":0
    }
  );
  notification_driver_subscriber$ = this.notification_driver.asObservable();

  constructor() { }

  sentCitizenStart(message: RideNotificationInterface): void {

    this.notification.next(message);
  }

  sentCitizenTime(message: RideNotificationInterface): void {

    this.notification.next(message);
  }


  sentCitizenEnd(message: RideNotificationInterface): void {

    this.notification.next(message);
  }



  sentDriverStart(message: RideNotificationInterface): void {

    this.notification_driver.next(message);
  }



}
