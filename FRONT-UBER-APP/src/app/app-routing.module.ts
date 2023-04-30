import { CitizenChatComponent } from './components/citizen-chat/citizen-chat.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { DriverRegisterComponent } from './components/driver-register/driver-register.component';
import { CitizenRegisterComponent } from './components/citizen-register/citizen-register.component';
import { LoginComponent } from './components/login/login.component';
import { AdminHomeComponent } from './components/admin-home/admin-home.component';
import { CitizenHomeComponent } from './components/citizen-home/citizen-home.component';
import { DriverMenuComponent } from './components/driver-menu/driver-menu.component';
import { AdminMenuComponent } from './components/admin-menu/admin-menu.component';
import { CitizenMenuComponent } from './components/citizen-menu/citizen-menu.component';
import { BarChartComponent } from './components/bar-chart/bar-chart.component';
import { MapComponent } from './components/map-component/map.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DriverHomeComponent } from './components/driver-home/driver-home.component';
import { AdminCitizenChatComponent } from './components/admin-citizen-chat/admin-citizen-chat.component';
import { AdminDriverChatComponent } from './components/admin-driver-chat/admin-driver-chat.component';
import { DriverChatComponent } from './components/driver-chat/driver-chat.component';
import { TestComponent } from './components/test/test.component';

const routes: Routes = [
  {path : '', component:MapComponent},
  {path : 'd3', component:BarChartComponent},

  {path : 'citizen-home', component:CitizenHomeComponent},
  {path : 'citizen-home/:jwtToken', component:CitizenHomeComponent},
  {path : 'citizen-chat', component:CitizenChatComponent},

  {path : 'admin-home', component:AdminHomeComponent},
  {path : 'admin-citizen-chat', component:AdminCitizenChatComponent},
  {path : 'admin-driver-chat', component:AdminDriverChatComponent},

  {path : 'driver-home', component:DriverHomeComponent},
  {path : 'driver-chat', component:DriverChatComponent},

  {path : 'test', component:TestComponent},

  {path : 'login', component:LoginComponent},
  {path : 'citizen-register', component:CitizenRegisterComponent},
  {path : 'driver-register', component:DriverRegisterComponent},
  {path : 'reset-password', component:ResetPasswordComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
