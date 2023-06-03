import { Component } from '@angular/core';
import { FavoriteInterface } from 'src/app/model/FavoriteInterface';
import { FavoriteService } from 'src/app/services/favorite.service';
import { SharedFavoriteService } from 'src/app/shared/favorite.service';

@Component({
  selector: 'app-citizen-favorite-table',
  templateUrl: './citizen-favorite-table.component.html',
  styleUrls: ['./citizen-favorite-table.component.css']
})
export class CitizenFavoriteTableComponent {
  displayedColumns: string[] = [ 'id', 'name' , 'price' , 'meters' , 'type' , 'petFriendly' , 'babyFriendly' , 'clone'];

  favorites : FavoriteInterface[] = [];

  // selectedUser : string = '';
  // config : ConfigInterface = {
  //   name:"",
  //   minutes: "",
  //   regex:""
  // }


  constructor(private sharedFavoriteService : SharedFavoriteService, private favoriteService : FavoriteService) {
  }

  ngOnInit(): void {
    this.getAllFavorite();
  }

  public getAllFavorite(){

    let token = localStorage.getItem('user_token');
    if (token == null) {
      return;
    }

    let usernames = JSON.parse(atob(token.split('.')[1]))['sub'];

    this.favoriteService.getAllFavorite(usernames).subscribe((data)=>{
      this.favorites = data;
    });
  }


  public clone(id:string){
    console.log(id);
    this.sharedFavoriteService.sentID(id);
  }

}
