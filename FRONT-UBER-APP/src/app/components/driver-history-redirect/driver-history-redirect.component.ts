import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-driver-history-redirect',
  templateUrl: './driver-history-redirect.component.html',
  styleUrls: ['./driver-history-redirect.component.css']
})
export class DriverHistoryRedirectComponent {
  constructor(private router: Router){
    console.log("PROLETIO")
    // this.router.navigate(['/','citizen-home']);
  }

  ngOnInit(){
    this.router.navigate(['/','driver-home']);
  }
}
