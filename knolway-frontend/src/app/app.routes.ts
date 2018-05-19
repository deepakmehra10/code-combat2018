import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {CodeSquadComponent} from './codesquad/codesquad.component';

export const rootRouterConfig: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent},
  {path: 'codesquad/reports', component: CodeSquadComponent}
];

