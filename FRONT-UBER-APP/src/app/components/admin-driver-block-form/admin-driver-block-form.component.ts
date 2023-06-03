import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { BlockService } from 'src/app/services/block.service';
import { BlockSharedService } from 'src/app/shared/block.service';

@Component({
  selector: 'app-admin-driver-block-form',
  templateUrl: './admin-driver-block-form.component.html',
  styleUrls: ['./admin-driver-block-form.component.css']
})
export class AdminDriverBlockFormComponent {
  blockForm = new FormGroup({
    username: new FormControl(''),
    comment: new FormControl(''),

  });

  constructor(private sharedBlockService : BlockSharedService, private blockService : BlockService) {
      this.sharedBlockService.username_subscriber$.subscribe(data => {

        const usernameControl = this.blockForm.get('username');

        if (usernameControl != null){

          usernameControl.setValue(data);
        }

      });
  }

  ngOnInit(): void {
  }

  block(){
    const username = this.blockForm.value.username!;
    const comment = this.blockForm.value.comment!;
    //VALIDATE THIS....
    this.blockService.blockDriver(username ,comment).subscribe(data =>{
      console.log(data);
      if(data){
        this.sharedBlockService.updateTable();
      }
    });
  }
}
