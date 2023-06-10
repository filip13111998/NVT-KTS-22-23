import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { ResetPasswordInterface } from 'src/app/model/ResetPasswordInterface';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent {

  resetPasswordForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
    confirm: new FormControl(''),
    role: new FormControl(''),

  });

  resetPasswordInterface: ResetPasswordInterface = {
    username: '',
    password: '',
    confirm: '',
  };

  passwordFlag = false;

  constructor(private authService : AuthService , private router : Router) { }

  ngOnInit(): void {
  }

  public resetPassword(){
    console.log(this.resetPasswordForm.value);

    if(this.resetPasswordForm.value.password !== this.resetPasswordForm.value.confirm){
      this.passwordFlag = true;
      return;
    }
    this.passwordFlag = false;

    this.resetPasswordInterface.username = this.resetPasswordForm.value.username!;
    this.resetPasswordInterface.password = this.resetPasswordForm.value.password!;
    this.resetPasswordInterface.confirm = this.resetPasswordForm.value.confirm!;

    if(this.resetPasswordForm.value.role === 'CITIZEN'){
      this.authService.resetPasswordCitizen(this.resetPasswordInterface).subscribe(
        res => {
          this.router.navigate(['/','login']);
          console.log(res);
        },
      )
    }else if (this.resetPasswordForm.value.role === 'DRIVER'){
      this.authService.resetPasswordDriver(this.resetPasswordInterface).subscribe(
        res => {
          this.router.navigate(['/','login']);
          console.log(res);
        },
      )
    }else if (this.resetPasswordForm.value.role === 'ADMIN'){
      this.authService.resetPasswordAdmin(this.resetPasswordInterface).subscribe(
        res => {
          this.router.navigate(['/','login']);
          console.log(res);
        },
      )
    }


    console.log('sent user');

  }

}
