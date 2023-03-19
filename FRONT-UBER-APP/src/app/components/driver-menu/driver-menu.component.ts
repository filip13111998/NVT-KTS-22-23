import { Component } from '@angular/core';

@Component({
  selector: 'app-driver-menu',
  templateUrl: './driver-menu.component.html',
  styleUrls: ['./driver-menu.component.css']
})
export class DriverMenuComponent {
  constructor() { }

  ngOnInit(): void {
  }

  public logout() {
    localStorage.clear();
  }
}
