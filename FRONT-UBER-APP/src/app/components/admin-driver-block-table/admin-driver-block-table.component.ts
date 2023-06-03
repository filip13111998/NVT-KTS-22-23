import { Component } from '@angular/core';
import { BlockInterface } from 'src/app/model/BlockInterface';
import { BlockService } from 'src/app/services/block.service';
import { BlockSharedService } from 'src/app/shared/block.service';

@Component({
  selector: 'app-admin-driver-block-table',
  templateUrl: './admin-driver-block-table.component.html',
  styleUrls: ['./admin-driver-block-table.component.css']
})
export class AdminDriverBlockTableComponent {
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
    this.findAllDrivers();
  }

  public findAllDrivers(){
    this.blockService.findAllDrivers().subscribe((data)=>{
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
    this.blockService.blockDriver(username ,'UBLOCK').subscribe(data =>{
      console.log(data);
      if(data){
        this.sharedBlockService.updateTable();
      }
    });

  }
}
