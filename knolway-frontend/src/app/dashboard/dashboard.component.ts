import {Component, OnInit} from '@angular/core';
import {IEmployeeInterface} from './employee';
import {DashboardService} from './dashboard.service';
import {Login} from '../shared/models/login';
import {Employee} from '../shared/models/employee';
import {Router} from '@angular/router';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})

export class DashboardComponent {

  errorMessage:string;
  employees: IEmployeeInterface[] = [];
  project:any = [];
  employeeId: string;
  addResourceSuccessMessage: string;
  isSubmitted = false;
  model: Employee = new Employee();
  formValid = false;
  constructor(private _dashboardService: DashboardService,private router:Router) {

  }

  btnClick = function() {
    this.router.navigateByUrl('/codesquad/reports');
  }

  ngOnInit(): void {
    this._dashboardService.getGithubLink('rccl')
      .subscribe(message => {
          this.project = message;
        });

        this._dashboardService.getEmployee()
      .subscribe(products => {
          this.employees = products;

        },
        error => this.errorMessage = <any>error);
  }

  delete(employeeId: string): any {
    this._dashboardService.deleteResource(employeeId)
      .subscribe(message => {
          return message;

        },
        error => this.errorMessage = <any>error);
  }

  getLink(projectName:string): any {
    this._dashboardService.getGithubLink(projectName)
      .subscribe(message => {
          this.project = message;
        },
        error => this.errorMessage = <any>error);
  }

 /* post(employee:IEmployeeInterface): any {
    this._dashboardService.addResource(employee)
      .subscribe(message => {
          return message;

        },
        error => this.errorMessage = <any>error);
  }*/
  submit(formControl: any) {
    this.isSubmitted = true;
    if (formControl.valid) {
      this.formValid = true;
      //btoa(this.model.employeeId + ':' + this.model.employeeName + this.model.employeeDesignation + this.model.manager);
      this._dashboardService.addResource(this.model)
        .subscribe(message => {
            return message;

          },
          error => this.errorMessage = <any>error);
      }
  }
}
