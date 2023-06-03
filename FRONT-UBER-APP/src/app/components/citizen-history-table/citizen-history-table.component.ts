import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { HistoryInterface } from 'src/app/model/HistoryInterface';
import { HistoryService } from 'src/app/services/history.service';
import { SharedHistoryService } from 'src/app/shared/history.service';

@Component({
  selector: 'app-citizen-history-table',
  templateUrl: './citizen-history-table.component.html',
  styleUrls: ['./citizen-history-table.component.css']
})
export class CitizenHistoryTableComponent {
  displayedColumns: string[] = [ 'id', 'name' , 'start' , 'end' , 'price' , 'favorite' , 'detail' , 'mark'];

  histories : HistoryInterface[] = [];

  historyForm = new FormGroup({
    sort: new FormControl(''),
  });

  constructor(private historyService : HistoryService , private router : Router
    , private sharedistoryService :SharedHistoryService) {

  }

  ngOnInit(): void {
    this.sortByNameCitizen();
  }

  public sort(){
    const sort = this.historyForm.value.sort!;
    if (sort === "START") {
      this.sortByStartDateCitizen();
    } else if (sort === "END") {
      this.sortByEndDateCitizen();
    } else if (sort === "PRICE") {
      this.sortByPriceCitizen();
    } else {
      this.sortByNameCitizen();
    }
  }

  public sortByNameCitizen(){

    let token = localStorage.getItem('user_token');
    if (token == null) {
      return;
    }

    let usernames = JSON.parse(atob(token.split('.')[1]))['sub'];

    this.historyService.sortByNameCitizen(usernames).subscribe((data)=>{
      this.histories = data;
    });

  }

  public sortByStartDateCitizen(){
    let token = localStorage.getItem('user_token');
    if (token == null) {
      return;
    }

    let usernames = JSON.parse(atob(token.split('.')[1]))['sub'];

    this.historyService.sortByStartDateCitizen(usernames).subscribe((data)=>{
      this.histories = data;
    });
  }

  public sortByEndDateCitizen(){
    let token = localStorage.getItem('user_token');
    if (token == null) {
      return;
    }

    let usernames = JSON.parse(atob(token.split('.')[1]))['sub'];

    this.historyService.sortByEndDateCitizen(usernames).subscribe((data)=>{
      this.histories = data;
    });
  }

  public sortByPriceCitizen(){
    let token = localStorage.getItem('user_token');
    if (token == null) {
      return;
    }

    let usernames = JSON.parse(atob(token.split('.')[1]))['sub'];

    this.historyService.sortByPriceCitizen(usernames).subscribe((data)=>{
      this.histories = data;
    });
  }

  public favorite(id:string){
    let token = localStorage.getItem('user_token');
    if (token == null) {
      return;
    }

    let usernames = JSON.parse(atob(token.split('.')[1]))['sub'];

    this.historyService.addRideToFavorite(usernames , id).subscribe((data)=>{
      console.log(data);
      this.sort();
    });
  }

  public details(id:string){

    this.router.navigate(['/' , 'citizen-history-detail-view']);
    this.sharedistoryService.sentHistoryDetailViewID(id);
  }

  public mark(id:string){
    this.sharedistoryService.sentHistoryDetailViewID(id);
  }

}
