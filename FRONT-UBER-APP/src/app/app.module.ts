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
import { PlotComponent } from './components/plot/plot.component';
import { AdminCitizenBlockComponent } from './components/admin-citizen-block/admin-citizen-block.component';
import { AdminDriverBlockComponent } from './components/admin-driver-block/admin-driver-block.component';
import { AdminCitizenBlockTableComponent } from './components/admin-citizen-block-table/admin-citizen-block-table.component';
import { AdminCitizenBlockFormComponent } from './components/admin-citizen-block-form/admin-citizen-block-form.component';
import { AdminDriverBlockFormComponent } from './components/admin-driver-block-form/admin-driver-block-form.component';
import { AdminDriverBlockTableComponent } from './components/admin-driver-block-table/admin-driver-block-table.component';
import { MatTableModule } from '@angular/material/table';
import { CitizenFavoriteComponent } from './components/citizen-favorite/citizen-favorite.component';
import { CitizenFavoriteTableComponent } from './components/citizen-favorite-table/citizen-favorite-table.component';
import { CitizenFavoriteFormComponent } from './components/citizen-favorite-form/citizen-favorite-form.component';
import { CitizenHistoryComponent } from './components/citizen-history/citizen-history.component';
import { DriverHistoryComponent } from './components/driver-history/driver-history.component';
import { AdminCitizenHistoryComponent } from './components/admin-citizen-history/admin-citizen-history.component';
import { CitizenHistoryTableComponent } from './components/citizen-history-table/citizen-history-table.component';
import { DriverHistoryTableComponent } from './components/driver-history-table/driver-history-table.component';
import { AdminDriverHistoryComponent } from './components/admin-driver-history/admin-driver-history.component';
import { AdminCitizenHistoryTableComponent } from './components/admin-citizen-history-table/admin-citizen-history-table.component';
import { AdminDriverHistoryTableComponent } from './components/admin-driver-history-table/admin-driver-history-table.component';
import { CitizenHistoryDetailViewComponent } from './components/citizen-history-detail-view/citizen-history-detail-view.component';
import { CitizenHistoryDetailViewFormComponent } from './components/citizen-history-detail-view-form/citizen-history-detail-view-form.component';
import { CitizenHistoryRedirectComponent } from './components/citizen-history-redirect/citizen-history-redirect.component';
import { DriverHistoryRedirectComponent } from './components/driver-history-redirect/driver-history-redirect.component';
import { DriverHistoryDetailViewComponent } from './components/driver-history-detail-view/driver-history-detail-view.component';
import { DriverHistoryDetailViewFormComponent } from './components/driver-history-detail-view-form/driver-history-detail-view-form.component';
import { CitizenGraphComponent } from './components/citizen-graph/citizen-graph.component';
import { GraphComponent } from './components/graph/graph.component';
import { DriverGraphComponent } from './components/driver-graph/driver-graph.component';
import { AdminGraphComponent } from './components/admin-graph/admin-graph.component';
import { CitizenHistoryMarkComponent } from './components/citizen-history-mark/citizen-history-mark.component';
import { CitizenNotificationComponent } from './components/citizen-notification/citizen-notification.component';
import { CitizenNotificationRideStartComponent } from './components/citizen-notification-ride-start/citizen-notification-ride-start.component';
import { CitizenNotificationRideFinishComponent } from './components/citizen-notification-ride-finish/citizen-notification-ride-finish.component';
import { CitizenNotificationRideTimeComponent } from './components/citizen-notification-ride-time/citizen-notification-ride-time.component';
import { CitizenCurrentRideComponent } from './components/citizen-current-ride/citizen-current-ride.component';
import { DriverNotificationComponent } from './components/driver-notification/driver-notification.component';
import { CitizenProfileComponent } from './components/citizen-profile/citizen-profile.component';
import { DriverProfileComponent } from './components/driver-profile/driver-profile.component';
import { AdminAcceptDriverProfileComponent } from './components/admin-accept-driver-profile/admin-accept-driver-profile.component';


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
    PlotComponent,
    AdminCitizenBlockComponent,
    AdminDriverBlockComponent,
    AdminCitizenBlockTableComponent,
    AdminCitizenBlockFormComponent,
    AdminDriverBlockFormComponent,
    AdminDriverBlockTableComponent,
    CitizenFavoriteComponent,
    CitizenFavoriteTableComponent,
    CitizenFavoriteFormComponent,
    CitizenHistoryComponent,
    DriverHistoryComponent,
    AdminCitizenHistoryComponent,
    CitizenHistoryTableComponent,
    DriverHistoryTableComponent,
    AdminDriverHistoryComponent,
    AdminCitizenHistoryTableComponent,
    AdminDriverHistoryTableComponent,
    CitizenHistoryDetailViewComponent,
    CitizenHistoryDetailViewFormComponent,
    CitizenHistoryRedirectComponent,
    DriverHistoryRedirectComponent,
    DriverHistoryDetailViewComponent,
    DriverHistoryDetailViewFormComponent,
    CitizenGraphComponent,
    GraphComponent,
    DriverGraphComponent,
    AdminGraphComponent,
    CitizenHistoryMarkComponent,
    CitizenNotificationComponent,
    CitizenNotificationRideStartComponent,
    CitizenNotificationRideFinishComponent,
    CitizenNotificationRideTimeComponent,
    CitizenCurrentRideComponent,
    DriverNotificationComponent,
    CitizenProfileComponent,
    DriverProfileComponent,
    AdminAcceptDriverProfileComponent,
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
    MatTableModule,
    MDBBootstrapModule.forRoot(),
  ],
  providers: [WebSocketAPI ],
  bootstrap: [AppComponent]
})
export class AppModule { }
