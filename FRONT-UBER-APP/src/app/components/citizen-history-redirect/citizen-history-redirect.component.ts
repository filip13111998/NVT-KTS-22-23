import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-citizen-history-redirect',
  templateUrl: './citizen-history-redirect.component.html',
  styleUrls: ['./citizen-history-redirect.component.css']
})
export class CitizenHistoryRedirectComponent {

  constructor(private router: Router){
    console.log("PROLETIO")
    // this.router.navigate(['/','citizen-home']);
  }

  ngOnInit(){
    this.router.navigate(['/','citizen-home']);
  }
}
