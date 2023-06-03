import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { FavoriteService } from 'src/app/services/favorite.service';
import { SharedFavoriteService } from 'src/app/shared/favorite.service';

@Component({
  selector: 'app-citizen-favorite-form',
  templateUrl: './citizen-favorite-form.component.html',
  styleUrls: ['./citizen-favorite-form.component.css']
})
export class CitizenFavoriteFormComponent {


  // selectedUser : string = '';

  cloneForm = new FormGroup({
    id: new FormControl(''),
    minutes: new FormControl(''),

  });

  constructor(private sharedFavoriteService : SharedFavoriteService, private favoriteService : FavoriteService) {
      this.sharedFavoriteService.id_subscriber$.subscribe(data => {

        const control = this.cloneForm.get('id');

        if (control != null){

          control.setValue(data);
        }

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
      console.log(data);
    });
  }

}
