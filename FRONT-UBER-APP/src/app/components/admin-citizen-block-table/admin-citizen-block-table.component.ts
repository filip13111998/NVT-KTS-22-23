import { Component } from '@angular/core';
import { BlockInterface } from 'src/app/model/BlockInterface';
import { BlockService } from 'src/app/services/block.service';
import { BlockSharedService } from 'src/app/shared/block.service';

@Component({
  selector: 'app-admin-citizen-block-table',
  templateUrl: './admin-citizen-block-table.component.html',
  styleUrls: ['./admin-citizen-block-table.component.css']
})
export class AdminCitizenBlockTableComponent {
  displayedColumns: string[] = [ 'username', 'firstName' , 'lastName' , 'city' , 'phone' , 'block'];

  users : BlockInterface[] = [];

  selectedUser : string = '';
  // config : ConfigInterface = {
  //   name:"",
  //   minutes: "",
  //   regex:""
  // }


  constructor(private sharedBlockService : BlockSharedService, private blockService : BlockService) {
    this.sharedBlockService.update_table_subscriber$.subscribe( () => {
      // this.findAllCitizens();
      const user = this.users.find(u => u.username === this.selectedUser);
      console.log('UNBLOCK!');
      if (user) {
        user.block = !user.block; // Set the block attribute to true
      }
    });
  }

  ngOnInit(): void {
    this.findAllCitizens();
  }

  public findAllCitizens(){
    this.blockService.findAllCitizens().subscribe((data)=>{
      this.users = data;
    });
  }


  public block(username:string){
    this.selectedUser = username;
    this.sharedBlockService.sentUsername(username);

  }

  public unblock(username:string){
    this.selectedUser = username;
    //VALIDATE THIS....
    this.blockService.blockCitizen(username ,'UBLOCK').subscribe(data =>{
      console.log(data);
      if(data){
        this.sharedBlockService.updateTable();
      }
    });

  }
}
