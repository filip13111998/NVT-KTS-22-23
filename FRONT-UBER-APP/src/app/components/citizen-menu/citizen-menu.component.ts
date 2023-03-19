import { Component } from '@angular/core';

@Component({
  selector: 'app-citizen-menu',
  templateUrl: './citizen-menu.component.html',
  styleUrls: ['./citizen-menu.component.css']
})
export class CitizenMenuComponent {

  constructor() { }

  ngOnInit(): void {
  }

  public logout() {
    localStorage.clear();
  }
}
