import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { HistoryInterface } from 'src/app/model/HistoryInterface';
import { HistoryService } from 'src/app/services/history.service';
import { SharedHistoryService } from 'src/app/shared/history.service';

@Component({
  selector: 'app-driver-history-table',
  templateUrl: './driver-history-table.component.html',
  styleUrls: ['./driver-history-table.component.css']
})
export class DriverHistoryTableComponent {
  displayedColumns: string[] = [ 'id', 'name' , 'start' , 'end' , 'price' , 'detail'];

  histories : HistoryInterface[] = [];

  historyForm = new FormGroup({
    sort: new FormControl(''),
  });

  constructor(private historyService : HistoryService, private router : Router
    , private sharedistoryService :SharedHistoryService ) {

  }

  ngOnInit(): void {
    this.sortByNameDriver();
  }

  public sort(){
    const sort = this.historyForm.value.sort!;
    if (sort === "START") {
      this.sortByStartDateDriver();
    } else if (sort === "END") {
      this.sortByEndDateDriver();
    } else if (sort === "PRICE") {
      this.sortByPriceDriver();
    } else {
      this.sortByNameDriver();
    }
  }

  public sortByNameDriver(){

    let token = localStorage.getItem('user_token');
    if (token == null) {
      return;
    }

    let usernames = JSON.parse(atob(token.split('.')[1]))['sub'];

    this.historyService.sortByNameDriver(usernames).subscribe((data)=>{
      this.histories = data;
    });

  }

  public sortByStartDateDriver(){
    let token = localStorage.getItem('user_token');
    if (token == null) {
      return;
    }

    let usernames = JSON.parse(atob(token.split('.')[1]))['sub'];

    this.historyService.sortByStartDateDriver(usernames).subscribe((data)=>{
      this.histories = data;
    });
  }

  public sortByEndDateDriver(){
    let token = localStorage.getItem('user_token');
    if (token == null) {
      return;
    }

    let usernames = JSON.parse(atob(token.split('.')[1]))['sub'];

    this.historyService.sortByEndDateDriver(usernames).subscribe((data)=>{
      this.histories = data;
    });
  }

  public sortByPriceDriver(){
    let token = localStorage.getItem('user_token');
    if (token == null) {
      return;
    }

    let usernames = JSON.parse(atob(token.split('.')[1]))['sub'];

    this.historyService.sortByPriceDriver(usernames).subscribe((data)=>{
      this.histories = data;
    });
  }


  public details(id:string){
    //redirect to detail view page
    this.router.navigate(['/' , 'driver-history-detail-view']);
    this.sharedistoryService.sentHistoryDetailViewID(id);
  }

}
