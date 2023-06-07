import { TokenInterface } from './../../model/TokenInterface';
import { LoginInterface } from './../../model/LoginInterface';
import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import {  Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),

  });

  loginInterface: LoginInterface = {
    username: '',
    password: ''
  };

  constructor(private authService : AuthService,private router: Router) { }

  ngOnInit(): void {
  }

  loginWithGoogle(): void {
    window.location.href = `http://localhost:8080/auth/login/oauth2/code/google`;
    window.location.href = 'https://accounts.google.com/o/oauth2/v2/auth' +
    '?client_id=450547483794-u7usqmlhrtr11e0psoef0ghck73sb25u.apps.googleusercontent.com' +
    '&response_type=code' +
    '&scope=openid+email+profile' +
    '&redirect_uri=http://localhost:8080/auth/login/oauth2/code/google'+
    '';


  }

  public login(){
    // console.log(this.loginForm.value);
    this.loginInterface.username = this.loginForm.value.username!;
    this.loginInterface.password = this.loginForm.value.password!;
    // if (this.loginForm.value.username !== '' && this.loginForm.value.username !== undefined) {
    //   this.loginInterface.username = this.loginForm.value.username!;
    // }

    this.authService.login(this.loginInterface).subscribe(
      (tkn: TokenInterface) => {
        localStorage.setItem('user_token',tkn.accessToken)

        console.log(tkn.accessToken);

        let my_roles = JSON.parse(atob(tkn.accessToken.split('.')[1]))['roles'].split(',');
        console.log(my_roles);
        if (my_roles.includes('ROLE_CITIZEN')) { //ROLE CITIZEN
          this.router.navigate(['/', 'citizen-home']);

        }

        if (my_roles.includes('ROLE_DRIVER')) { //ROLE EMPLOYEE
          this.router.navigate(['/', 'driver-home']);

        }

        if (my_roles.includes('ROLE_ADMIN')) { //ROLE EMPLOYEE
          this.router.navigate(['/', 'admin-home']);

        }

      },
    )
    console.log('sent user');

  }

}
