import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-citizen-menu',
  templateUrl: './citizen-menu.component.html',
  styleUrls: ['./citizen-menu.component.css']
})
export class CitizenMenuComponent {

  constructor(private router:Router) { }

  ngOnInit(): void {
  }

  // home(){
  //   console.log('HOME');
  //   this.router.navigate(['/','citizen-home']);
  // }

  public logout() {
    localStorage.clear();
  }
}
