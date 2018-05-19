import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import {DashboardComponent} from './dashboard.component';
import {DashboardService} from './dashboard.service';

@NgModule({
  imports: [
    SharedModule
  ],
  exports: [
    DashboardComponent
  ],
  declarations: [DashboardComponent],
  providers: [DashboardService]
})
export class DashboardModule {}
