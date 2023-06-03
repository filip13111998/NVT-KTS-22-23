import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { HistoryInterface } from 'src/app/model/HistoryInterface';
import { HistoryService } from 'src/app/services/history.service';

@Component({
  selector: 'app-admin-driver-history-table',
  templateUrl: './admin-driver-history-table.component.html',
  styleUrls: ['./admin-driver-history-table.component.css']
})
export class AdminDriverHistoryTableComponent {
  displayedColumns: string[] = ['id', 'name' , 'start' , 'end' , 'price','driver'];

  histories : HistoryInterface[] = [];

  historyForm = new FormGroup({
    sort: new FormControl(''),
  });

  constructor(private historyService : HistoryService) {

  }

  ngOnInit(): void {
    this.sortByNameDriverAdmin();
  }

  public sort(){
    const sort = this.historyForm.value.sort!;
    if (sort === "START") {
      this.sortByStartDateDriverAdmin();
    } else if (sort === "END") {
      this.sortByEndDateDriverAdmin();
    } else if (sort === "PRICE") {
      this.sortByPriceDriverAdmin();
    } else {
      this.sortByNameDriverAdmin();
    }
  }

  public sortByNameDriverAdmin(){
    this.historyService.sortByNameDriverAdmin().subscribe((data)=>{
      this.histories = data;
    });
  }

  public sortByStartDateDriverAdmin(){
    this.historyService.sortByStartDateDriverAdmin().subscribe((data)=>{
      this.histories = data;
    });
  }

  public sortByEndDateDriverAdmin(){
    this.historyService.sortByEndDateDriverAdmin().subscribe((data)=>{
      this.histories = data;
    });
  }

  public sortByPriceDriverAdmin(){
    this.historyService.sortByPriceDriverAdmin().subscribe((data)=>{
      this.histories = data;
    });
  }
}
