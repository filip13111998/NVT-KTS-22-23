import { DriverRegisterInterface } from './../../model/DriverRegisterInterface';
import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-driver-register',
  templateUrl: './driver-register.component.html',
  styleUrls: ['./driver-register.component.css']
})
export class DriverRegisterComponent {
  driverForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
    confirm: new FormControl(''),
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    city: new FormControl(''),
    phone: new FormControl(''),
    name: new FormControl(''),
    type: new FormControl(''),
    petFriendly: new FormControl(''),
    babyFriendly: new FormControl(''),
  });

  driverRegisterInterface: DriverRegisterInterface = {
    username: '',
    password: '',
    confirm: '',
    firstName: '',
    lastName: '',
    city: '',
    phone: '',
    name: '',
    type: '',
    petFriendly: false,
    babyFriendly: false,
  };

  passwordFlag = false;

  constructor(private authService : AuthService) { }

  ngOnInit(): void {
  }

  public registerDriver(){
    console.log(this.driverForm.value);

    if(this.driverForm.value.password !== this.driverForm.value.confirm){
      this.passwordFlag = true;
      return;
    }
    this.passwordFlag = false;

    this.driverRegisterInterface.username = this.driverForm.value.username!;
    this.driverRegisterInterface.password = this.driverForm.value.password!;
    this.driverRegisterInterface.confirm = this.driverForm.value.confirm!;
    this.driverRegisterInterface.firstName = this.driverForm.value.firstName!;
    this.driverRegisterInterface.lastName = this.driverForm.value.lastName!;
    this.driverRegisterInterface.city = this.driverForm.value.city!;
    this.driverRegisterInterface.phone = this.driverForm.value.phone!;
    this.driverRegisterInterface.name = this.driverForm.value.name!;
    this.driverRegisterInterface.type = this.driverForm.value.type!;
    if(this.driverForm.value.petFriendly === 'YES'){
      this.driverRegisterInterface.petFriendly = true;
    }else{
      this.driverRegisterInterface.petFriendly = false;
    }
    if(this.driverForm.value.babyFriendly === 'YEs'){
      this.driverRegisterInterface.babyFriendly = true;
    }else{
      this.driverRegisterInterface.babyFriendly = false;
    }

    this.authService.registerDriver(this.driverRegisterInterface).subscribe(
      res => {
        console.log(res);
      },
    )
    console.log('sent user');

  }
}
