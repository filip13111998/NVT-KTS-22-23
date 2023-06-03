import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { FavoriteService } from 'src/app/services/favorite.service';
import { HistoryService } from 'src/app/services/history.service';
import { SharedHistoryService } from 'src/app/shared/history.service';

@Component({
  selector: 'app-driver-history-detail-view-form',
  templateUrl: './driver-history-detail-view-form.component.html',
  styleUrls: ['./driver-history-detail-view-form.component.css']
})
export class DriverHistoryDetailViewFormComponent {
  cloneForm = new FormGroup({
    id: new FormControl({value:'',disabled: true}),
    name: new FormControl({value:'',disabled: true}),
    start: new FormControl({value:'',disabled: true}),
    end: new FormControl({value:'',disabled: true}),
    price: new FormControl({value:'',disabled: true}),
    driverfirstName: new FormControl({value:'',disabled: true}),
    driverLastName: new FormControl({value:'',disabled: true}),
    marks: new FormControl({value:'',disabled: true}),
    citizens: new FormControl({value:'',disabled: true}),
    minutes:new FormControl(''),
  });

  constructor(private sharedHostoryService : SharedHistoryService, private favoriteService : FavoriteService,
    private hositoryService : HistoryService) {
      this.sharedHostoryService.id_subscriber$.subscribe(id => {

        this.hositoryService.getRide(id).subscribe(data => {
          // console.log(data.routes);
          const id = this.cloneForm.get('id');
          const name = this.cloneForm.get('name');
          const start = this.cloneForm.get('start');
          const end = this.cloneForm.get('end');
          const price = this.cloneForm.get('price');
          const driverfirstName = this.cloneForm.get('driverfirstName');
          const driverLastName = this.cloneForm.get('driverLastName');
          const marks = this.cloneForm.get('marks');
          const citizens = this.cloneForm.get('citizens');
          if (id != null){
            id.setValue(data.id+'');
          }
          if (name != null){
            name.setValue(data.name);
          }
          if (start != null){
            start.setValue(data.start+'');
          }
          if (end != null){
            end.setValue(data.end+'');
          }
          if (price != null){
            price.setValue(data.price+'');
          }
          if (driverfirstName != null){
            driverfirstName.setValue(data.driverfirstName+'');
          }
          if (driverLastName != null){
            driverLastName.setValue(data.driverLastName+'');
          }
          if (marks != null){
            marks.setValue(data.marks+'');
          }
          if (citizens != null){
            citizens.setValue(data.citizens+'');
          }

          this.sharedHostoryService.sentHistoryDetailViewRoute(data.routes);
        });
          // if(data.routes!= null){

          // }

        // const control = this.cloneForm.get('id');

        // if (control != null){

        //   control.setValue(data);
        // }

      });
  }

  ngOnInit(): void {
  }

  cloneRide(){
    const id = this.cloneForm.value.id!;
    const minutes = this.cloneForm.value.minutes!;
    //VALIDATE THIS....
    // const minutes: number = parseInt(minutesString, 10); // Example using parseInt()

    this.favoriteService.cloneRide(Number(id) ,minutes).subscribe(data =>{
      // console.log(data);
    });
  }

}
