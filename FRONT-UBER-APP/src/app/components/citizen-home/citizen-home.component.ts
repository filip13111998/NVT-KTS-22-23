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
    const token = this.route.snapshot.queryParamMap.get('jwtToken');
    if (token != null){
      this.jwtToken = token;
    }
  }

}
