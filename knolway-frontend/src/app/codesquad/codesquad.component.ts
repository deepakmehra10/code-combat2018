import {Component} from '@angular/core';
import {Reports} from './reports';
import {DashboardService} from '../dashboard/dashboard.service';
import {Router} from '@angular/router';
import {CodeSquadService} from './codesquad.service';
import {Observable} from 'rxjs/Observable';

@Component({
  selector: 'app-codesquad',
  templateUrl: './codesquad.component.html',
  styleUrls: ['./codesquad.component.css']
})
/**Login Component */
export class CodeSquadComponent{

  errorMessage:string;

  reports:Reports[] = [];

  constructor(private _codeSquadService: CodeSquadService) {}

  ngOnInit(): void {
    this._codeSquadService.getReports()
      .subscribe(report => {
          this.reports = report;

        },
        error => this.errorMessage = <any>error);
  }

}
