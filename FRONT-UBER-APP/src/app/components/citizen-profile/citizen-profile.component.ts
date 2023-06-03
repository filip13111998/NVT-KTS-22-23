import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { CitizenProfileInterface } from 'src/app/model/CitizenProfileInterface';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-citizen-profile',
  templateUrl: './citizen-profile.component.html',
  styleUrls: ['./citizen-profile.component.css']
})
export class CitizenProfileComponent {

  profile : CitizenProfileInterface = {
    "username":"",
    "firstName":"",
    "lastName":"",
    "password":"",
    "city":"",
    "phone":"",
    "tokens":0,
    "comment":"",
  };

  profileForm = new FormGroup({
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    password: new FormControl(''),
    city: new FormControl(''),
    phone: new FormControl(''),
    tokens: new FormControl(''),
    comment: new FormControl({ value: '', disabled: true })
  })

  constructor(private profileServise : ProfileService ) {}

  ngOnInit(): void {

    this.getCitizen();

  }

  public getCitizen(){
    let userToken = localStorage.getItem('user_token');

    if (userToken) {
      let parsedToken = JSON.parse(atob(userToken.split('.')[1]));
      let username = parsedToken['sub'].trim().split('@')[0];
      this.profileServise.getCitizen(username).subscribe((data:CitizenProfileInterface)=>{
        this.profile = data;
        this.profileForm.patchValue({ firstName: data.firstName + '' }); // Update form control value
        this.profileForm.patchValue({ lastName: data.lastName + '' }); // Update form control value
        this.profileForm.patchValue({ password: data.password + '' }); // Update form control value
        this.profileForm.patchValue({ city: data.city + '' }); // Update form control value
        this.profileForm.patchValue({ phone: data.phone + '' }); // Update form control value
        this.profileForm.patchValue({ tokens: data.tokens + '' }); // Update form control value
        this.profileForm.patchValue({ comment: data.comment + '' }); // Update form control value
      });
    }

  }

  public update(){

    this.profile.firstName = this.profileForm.value.firstName!;
    this.profile.lastName = this.profileForm.value.lastName!;
    this.profile.password = this.profileForm.value.password!;
    this.profile.city = this.profileForm.value.city!;
    this.profile.phone = this.profileForm.value.phone!;
    this.profile.comment = this.profileForm.value.comment!;
    this.profile.tokens = parseInt(this.profileForm.value.tokens!);

    this.profileServise.updateCitizen(this.profile).subscribe(data => {

      //this.danForm.get('ocena')!.enable(); // Enable the form control


    });

  }

}
