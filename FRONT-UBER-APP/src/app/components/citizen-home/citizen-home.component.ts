import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-citizen-home',
  templateUrl: './citizen-home.component.html',
  styleUrls: ['./citizen-home.component.css']
})
export class CitizenHomeComponent {

  jwtToken: string = '';

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    console.log("EXETUCE");
    console.log("SN:" + this.route.snapshot);
    const token = this.route.snapshot.paramMap.get('jwtToken');
    if (token != null){
      console.log("USOOO");
      this.jwtToken = token;
      localStorage.setItem('user_token', token);
    }

  }


}
