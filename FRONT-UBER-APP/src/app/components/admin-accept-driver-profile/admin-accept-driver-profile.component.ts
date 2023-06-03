import { Component } from '@angular/core';
import { DriverProfileInterface } from 'src/app/model/DriverProfileInterface';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-admin-accept-driver-profile',
  templateUrl: './admin-accept-driver-profile.component.html',
  styleUrls: ['./admin-accept-driver-profile.component.css']
})
export class AdminAcceptDriverProfileComponent {

  displayedColumns: string[] = [ 'username', 'password' , 'firstName', 'lastName' , 'city' , 'phone' , 'comment' , 'accept'];

  profiles : DriverProfileInterface[] = [];

  constructor(private profileService : ProfileService) {}

  ngOnInit(): void {
    this.getAllProfiles();
  }

  public getAllProfiles(): void {

    this.profileService.getDriverProfiles().subscribe((data:DriverProfileInterface[]) => {
      this.profiles = data;
    })

  }

  public accept(username:string): void {
    console.log(username);
    this.profileService.adminAcceptDriverUpdate(username).subscribe((data:boolean) => {

    })
  }

}
