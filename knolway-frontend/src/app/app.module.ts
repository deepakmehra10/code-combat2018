import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import {SharedModule} from './shared/shared.module';
import {LoginModule} from './login/login.module';
import {RouterModule} from '@angular/router';
import {DashboardModule} from './dashboard/dashboard.module';
import {rootRouterConfig} from './app.routes';
import {HttpClientModule} from '@angular/common/http';
import {CodeSquadComponent} from './codesquad/codesquad.component';
import {CodeSquadModule} from './codesquad/codesquad.module';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    SharedModule,
    LoginModule,
    DashboardModule,
    CodeSquadModule,
    HttpClientModule,
    RouterModule.forRoot(rootRouterConfig)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
