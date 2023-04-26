import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { RideSaveInterface } from 'src/app/model/RideSaveInterface';
import { RoutePartInterface } from 'src/app/model/RoutePartInterface';
import { MapService } from 'src/app/shared/map.service';

@Component({
  selector: 'app-citizen-home-ride-form',
  templateUrl: './citizen-home-ride-form.component.html',
  styleUrls: ['./citizen-home-ride-form.component.css']
})
export class CitizenHomeRideFormComponent {

  private selected_routes : any[] = [];

  total_price : number = 0 ;

  users:string[] = [];

  emailError :string = '';

  nameError :string = '';

  distance:number = 0;

  makeRideForm = new FormGroup({
    name: new FormControl(''),
    pets: new FormControl(''),
    baby: new FormControl(''),
    car_type: new FormControl(''),
    user: new FormControl(''),
  });

  userForm = new FormGroup({
    user: new FormControl('')
  })

  makeRideInterface: RideSaveInterface = {
    name: '',
    pets: false,
    baby:false,
    car_type: 'MINI',
    users:[],
    price : 0,
    distance : 0,
    routePartInterface:[],
  };

  constructor(private mapService: MapService){
    this.mapService.routing_controls$.subscribe(data => {
      this.selected_routes = data;
      if(this.selected_routes.length === 0){
        this.total_price = 0;
      }
    });
  }

  public calculate_price(){

    if(this.selected_routes.length === 0){
      this.total_price = 0;
      return;
    }

    setTimeout(() => {
      this.distance = this.selected_routes.reduce((sum, e) => {
        return sum + e._routes[e.myindex].summary.totalDistance
      }, 0);

      let type = 250

      if(this.makeRideForm.value.car_type! === 'MINI'){
        type = 120;
      }
      else if(this.makeRideForm.value.car_type! === 'VAN'){
        type = 340;
      }

      this.total_price = this.distance * 120 + type;
    },200)

  }

  public add_user(){

    let user  = this.userForm.value.user!;

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    // const isValidEmail = emailRegex.test('example@email.com');

    if(!emailRegex.test(user)){
      this.emailError = 'WRONG MAIL!';
      return;
    }

    this.emailError = '';

    this.users.push(user);

    this.userForm.reset();

  }

  public delete_user(user:string){
    let index = this.users.indexOf(user);
    if (index !== -1) {
      this.users.splice(index, 1);
    }
  }

  public make_ride(){
    // this.mapService.getRouteParts();
    this.makeRideInterface.distance = this.distance;
    this.makeRideInterface.price = this.total_price;;

    this.makeRideInterface.users = this.users;

    this.makeRideInterface.name = this.makeRideForm.value.name!;

    if(this.makeRideInterface.name === ''){
      this.nameError = 'WRONG NAME!';
      return;
    }

    this.makeRideInterface.pets = Boolean(this.makeRideForm.value.pets!);
    this.makeRideInterface.baby = Boolean(this.makeRideForm.value.baby!);
    this.makeRideInterface.car_type = this.makeRideForm.value.car_type!;

    // let routePartInterface : RoutePartInterface[] = this.selected_routes.map((e) => {
    //   return {
    //     id: e.myindex,
    //     coordinates: e._routes[e.myindex].coordinates.map((w:any) => {
    //       return { longitude: w.lng, latitude: w.lat };
    //     }),
    //   };
    // });

    let routePartInterface: RoutePartInterface[] = this.selected_routes.map((e) => {
      const coordinates = e._routes[e.myindex].coordinates;
      const firstCoordinate = { longitude: coordinates[0].lng, latitude: coordinates[0].lat };
      const lastCoordinate = { longitude: coordinates[coordinates.length - 1].lng, latitude: coordinates[coordinates.length - 1].lat };
      const everyFifthCoordinate = coordinates.filter((_:any, index:any) => index % 5 === 0)
        .map((w:any) => { return { longitude: w.lng, latitude: w.lat } });
      return {
        id: e.myindex,
        coordinates: [firstCoordinate, ...everyFifthCoordinate, lastCoordinate]
      };
    });

    this.makeRideInterface.routePartInterface = routePartInterface;

    console.log('MAKE RIDE BRO');
    console.log(this.selected_routes);
    console.log('ENDE');
    console.log(this.makeRideInterface);
    console.log('ENDE2');
  }

}
