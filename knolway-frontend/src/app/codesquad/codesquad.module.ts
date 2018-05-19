import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import {CodeSquadComponent} from './codesquad.component';
import {DashboardService} from '../dashboard/dashboard.service';
import {CodeSquadService} from './codesquad.service';

@NgModule({
  imports: [
    SharedModule
  ],
  exports: [
    CodeSquadComponent
  ],
  declarations: [CodeSquadComponent],
  providers: [CodeSquadService]
})
export class CodeSquadModule { }
