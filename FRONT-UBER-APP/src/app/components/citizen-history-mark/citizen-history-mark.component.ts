import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MarkInterface } from 'src/app/model/MarkInterface';
import { HistoryService } from 'src/app/services/history.service';
import { SharedHistoryService } from 'src/app/shared/history.service';

@Component({
  selector: 'app-citizen-history-mark',
  templateUrl: './citizen-history-mark.component.html',
  styleUrls: ['./citizen-history-mark.component.css']
})
export class CitizenHistoryMarkComponent {

  num = ["1","2","3","4","5"];

  mark : MarkInterface = {
    "username":"",
    "rideId":0,
    "rideComment":"",
    "rideMark":0,
    "driverMark":0
  };

  markForm = new FormGroup({
    rideId: new FormControl({ value: '', disabled: true }),
    rideComment: new FormControl(''),
    rideMark: new FormControl(''),
    driverMark: new FormControl(''),
  })

  constructor(private historyService : HistoryService,private sharedistoryService :SharedHistoryService) {
    this.sharedistoryService.id_subscriber$.subscribe(data => {
      this.mark.rideId = parseInt(data);
      console.log(this.mark);
      this.markForm.patchValue({ rideId: data + '' });

    });
  }

  ngOnInit(): void {

  }

  public markRide(): void{

    let userToken = localStorage.getItem('user_token');
    let username = '';
    if (userToken) {
      username = JSON.parse(atob(userToken.split('.')[1]))['sub'];;
    }

    // this.mark.rideId = parseInt(this.markForm.value.rideId!);
    this.mark.rideComment = this.markForm.value.rideComment!;
    this.mark.rideMark = parseInt(this.markForm.value.rideMark!);
    this.mark.driverMark =parseInt(this.markForm.value.driverMark!);
    this.mark.username = username;
    console.log(this.mark);
    this.historyService.mark(this.mark).subscribe(data => {
      console.log(data);
    });
  }

}
