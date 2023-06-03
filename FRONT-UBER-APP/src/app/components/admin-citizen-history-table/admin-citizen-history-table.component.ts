import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { HistoryInterface } from 'src/app/model/HistoryInterface';
import { HistoryService } from 'src/app/services/history.service';

@Component({
  selector: 'app-admin-citizen-history-table',
  templateUrl: './admin-citizen-history-table.component.html',
  styleUrls: ['./admin-citizen-history-table.component.css']
})
export class AdminCitizenHistoryTableComponent {

  displayedColumns: string[] = ['id', 'name' , 'start' , 'end' , 'price' , 'citizens'];

  histories : HistoryInterface[] = [];

  historyForm = new FormGroup({
    sort: new FormControl(''),
  });

  constructor(private historyService : HistoryService) {

  }

  ngOnInit(): void {
    this.sortByNameCitizenAdmin();
  }

  public sort(){
    const sort = this.historyForm.value.sort!;
    if (sort === "START") {
      this.sortByStartDateCitizenAdmin();
    } else if (sort === "END") {
      this.sortByEndDateCitizenAdmin();
    } else if (sort === "PRICE") {
      this.sortByPriceCitizenAdmin();
    } else {
      this.sortByNameCitizenAdmin();
    }
  }

  public sortByNameCitizenAdmin(){
    this.historyService.sortByNameCitizenAdmin().subscribe((data)=>{
      this.histories = data;
    });

  }

  public sortByStartDateCitizenAdmin(){
    this.historyService.sortByStartDateCitizenAdmin().subscribe((data)=>{
      this.histories = data;
    });
  }

  public sortByEndDateCitizenAdmin(){
    this.historyService.sortByEndDateCitizenAdmin().subscribe((data)=>{
      this.histories = data;
    });
  }

  public sortByPriceCitizenAdmin(){
    this.historyService.sortByPriceCitizenAdmin().subscribe((data)=>{
      this.histories = data;
    });
  }


}
