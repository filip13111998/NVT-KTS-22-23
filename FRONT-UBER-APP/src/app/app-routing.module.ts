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
import { PlotComponent } from './components/plot/plot.component';
import { AdminCitizenBlockComponent } from './components/admin-citizen-block/admin-citizen-block.component';
import { AdminDriverBlockComponent } from './components/admin-driver-block/admin-driver-block.component';
import { CitizenFavoriteComponent } from './components/citizen-favorite/citizen-favorite.component';
import { CitizenHistoryComponent } from './components/citizen-history/citizen-history.component';
import { DriverHistoryComponent } from './components/driver-history/driver-history.component';
import { AdminCitizenHistoryComponent } from './components/admin-citizen-history/admin-citizen-history.component';
import { AdminDriverHistoryComponent } from './components/admin-driver-history/admin-driver-history.component';
import { CitizenHistoryDetailViewComponent } from './components/citizen-history-detail-view/citizen-history-detail-view.component';
import { CitizenHistoryRedirectComponent } from './components/citizen-history-redirect/citizen-history-redirect.component';
import { DriverHistoryDetailViewComponent } from './components/driver-history-detail-view/driver-history-detail-view.component';
import { DriverHistoryRedirectComponent } from './components/driver-history-redirect/driver-history-redirect.component';
import { CitizenGraphComponent } from './components/citizen-graph/citizen-graph.component';
import { DriverGraphComponent } from './components/driver-graph/driver-graph.component';
import { AdminGraphComponent } from './components/admin-graph/admin-graph.component';
import { CitizenNotificationComponent } from './components/citizen-notification/citizen-notification.component';
import { CitizenCurrentRideComponent } from './components/citizen-current-ride/citizen-current-ride.component';
import { DriverNotificationComponent } from './components/driver-notification/driver-notification.component';
import { CitizenProfileComponent } from './components/citizen-profile/citizen-profile.component';
import { DriverProfileComponent } from './components/driver-profile/driver-profile.component';
import { AdminAcceptDriverProfileComponent } from './components/admin-accept-driver-profile/admin-accept-driver-profile.component';

const routes: Routes = [
  {path : '', component:MapComponent},
  {path : 'd3', component:BarChartComponent},
  {path : 'plot', component:PlotComponent},

  {path : 'citizen-home', component:CitizenHomeComponent},
  {path : 'citizen-home/:jwtToken', component:CitizenHomeComponent},
  {path : 'citizen-chat', component:CitizenChatComponent},
  {path : 'citizen-favorite', component:CitizenFavoriteComponent},
  {path : 'citizen-history', component:CitizenHistoryComponent},
  {path : 'citizen-history-detail-view' , component:CitizenHistoryDetailViewComponent},
  {path:  'citizen-history-redirect' , component:CitizenHistoryRedirectComponent},
  {path:  'citizen-graph' , component:CitizenGraphComponent},
  {path:  'citizen-notification' , component:CitizenNotificationComponent},
  {path:  'citizen-current' , component:CitizenCurrentRideComponent},
  {path:  'citizen-profile' , component:CitizenProfileComponent},

  {path : 'admin-home', component:AdminHomeComponent},
  {path : 'admin-citizen-chat', component:AdminCitizenChatComponent},
  {path : 'admin-driver-chat', component:AdminDriverChatComponent},
  {path : 'admin-citizen-block', component:AdminCitizenBlockComponent},
  {path : 'admin-driver-block', component:AdminDriverBlockComponent },
  {path : 'admin-citizen-history', component:AdminCitizenHistoryComponent },
  {path : 'admin-driver-history', component:AdminDriverHistoryComponent },
  {path : 'admin-graph', component:AdminGraphComponent },
  {path : 'admin-accept-driver-profile', component:AdminAcceptDriverProfileComponent },

  {path : 'driver-home', component:DriverHomeComponent},
  {path : 'driver-chat', component:DriverChatComponent},
  {path : 'driver-history', component:DriverHistoryComponent},
  {path : 'driver-history-detail-view' , component:DriverHistoryDetailViewComponent},
  {path:  'driver-history-redirect' , component:DriverHistoryRedirectComponent},
  {path:  'driver-graph' , component:DriverGraphComponent},
  {path:  'driver-notification' , component:DriverNotificationComponent},
  {path:  'driver-profile' , component:DriverProfileComponent},

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
