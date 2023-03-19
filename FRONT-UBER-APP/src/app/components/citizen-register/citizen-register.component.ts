import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { CitizenRegisterInterface } from 'src/app/model/CitizenRegisterInterface';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-citizen-register',
  templateUrl: './citizen-register.component.html',
  styleUrls: ['./citizen-register.component.css']
})
export class CitizenRegisterComponent {
  citizenForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
    confirm: new FormControl(''),
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    city: new FormControl(''),
    phone: new FormControl(''),
  });

  citizenRegisterInterface: CitizenRegisterInterface = {
    username: '',
    password: '',
    confirm: '',
    firstName: '',
    lastName: '',
    city: '',
    phone: '',
  };


  passwordFlag = false;

  constructor(private authService : AuthService) { }

  ngOnInit(): void {
  }

  public registerCitizen(){
    console.log(this.citizenForm.value);

    if(this.citizenForm.value.password !== this.citizenForm.value.confirm){
      this.passwordFlag = true;
      return;
    }
    this.passwordFlag = false;


    this.citizenRegisterInterface.username = this.citizenForm.value.username!;
    this.citizenRegisterInterface.password = this.citizenForm.value.password!;
    this.citizenRegisterInterface.confirm = this.citizenForm.value.confirm!;
    this.citizenRegisterInterface.firstName = this.citizenForm.value.firstName!;
    this.citizenRegisterInterface.lastName = this.citizenForm.value.lastName!;
    this.citizenRegisterInterface.city = this.citizenForm.value.city!;
    this.citizenRegisterInterface.phone = this.citizenForm.value.phone!;


    this.authService.registerCitizen(this.citizenRegisterInterface).subscribe(
      res => {
        console.log(res);
      },
    )
    console.log('sent user');

  }
}
