import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MapComponent } from './components/map-component/map.component';
import { BarChartComponent } from './components/bar-chart/bar-chart.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { MatMenuModule } from '@angular/material/menu';
import { NgbNavModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { CitizenMenuComponent } from './components/citizen-menu/citizen-menu.component';
import { AdminMenuComponent } from './components/admin-menu/admin-menu.component';
import { DriverMenuComponent } from './components/driver-menu/driver-menu.component';
import { LoginComponent } from './components/login/login.component';

import { DriverHomeComponent } from './components/driver-home/driver-home.component';
import { CitizenHomeComponent } from './components/citizen-home/citizen-home.component';
import { AdminHomeComponent } from './components/admin-home/admin-home.component';

import { DriverRegisterComponent } from './components/driver-register/driver-register.component';
import { CitizenRegisterComponent } from './components/citizen-register/citizen-register.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { CitizenHomeRoutesViewComponent } from './components/citizen-home-routes-view/citizen-home-routes-view.component';
import { CitizenHomeRideFormComponent } from './components/citizen-home-ride-form/citizen-home-ride-form.component';
import { CitizenChatComponent } from './components/citizen-chat/citizen-chat.component';
import { ChatComponent } from './components/chat/chat.component';
import { AdminChatUsersListComponent } from './components/admin-chat-users-list/admin-chat-users-list.component';
import { AdminCitizenChatComponent } from './components/admin-citizen-chat/admin-citizen-chat.component';
import { AdminDriverChatComponent } from './components/admin-driver-chat/admin-driver-chat.component';
import { DriverChatComponent } from './components/driver-chat/driver-chat.component';
import { TestComponent } from './components/test/test.component';
import { WebSocketAPI } from './websocket/websocket-chat.service';


@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    BarChartComponent,
    CitizenMenuComponent,
    AdminMenuComponent,
    DriverMenuComponent,
    LoginComponent,
    DriverHomeComponent,
    CitizenHomeComponent,
    AdminHomeComponent,
    DriverRegisterComponent,
    CitizenRegisterComponent,
    ResetPasswordComponent,
    CitizenHomeRoutesViewComponent,
    CitizenHomeRideFormComponent,
    CitizenChatComponent,
    ChatComponent,
    AdminChatUsersListComponent,
    AdminCitizenChatComponent,
    AdminDriverChatComponent,
    DriverChatComponent,
    TestComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatMenuModule,
    NgbNavModule,
    MatButtonModule,
    MDBBootstrapModule.forRoot(),
  ],
  providers: [WebSocketAPI ],
  bootstrap: [AppComponent]
})
export class AppModule { }
