import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {IEmployeeInterface} from './employee';
import {Observable} from 'rxjs/Observable';
import {Router} from '@angular/router';

@Injectable()
export class DashboardService {

  private _dashboardUrl = 'http://192.168.2.135:9000/api/admin/getResource/email/neeraj@knoldus.com/loginType/ADMIN';

  private _deleteResource = 'http://192.168.2.135:9000/api/admin/remove';

  private _addResource = 'http://192.168.2.135:9000/api/admin/addResource';

  private _github = 'http://192.168.2.135:9000/api/admin/project/rccl';

  constructor(private _http: HttpClient) {
  }

  getEmployee() {
    return this._http.get<IEmployeeInterface[]>(this._dashboardUrl)
      .do(data => console.log('All:'+ JSON.stringify(data)))
      .catch(this.handleError);
  }

  deleteResource(employeeId:string) {
    return this._http.delete<string>(this._deleteResource+"/"+employeeId)
      .do(data => console.log('All:'+ JSON.stringify(data)))
      .catch(this.handleError);
  }

  addResource(employee: IEmployeeInterface) {
    return this._http.post<string>(this._addResource,employee)
      .do(data => console.log('All:'+ JSON.stringify(data)))
      .catch(this.handleError);
  }

  getGithubLink(projectName: string) {
    return this._http.get<string>(this._github)
      .do(data => console.log('All:'+ JSON.stringify(data)))
      .catch(this.handleError);
  }

  private handleError(err: HttpErrorResponse) {
    // in a real world app, we may send the server to some remote logging infrastructure
    // instead of just logging it to the console
    let errorMessage = '';
    if (err.error instanceof Error) {
      // A client-side or network error occurred. Handle it accordingly.
      errorMessage = `An error occurred: ${err.error.message}`;
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      errorMessage = `Server returned code: ${err.status}, error message is: ${err.message}`;
    }
    console.error(errorMessage);
    return Observable.throw(errorMessage);
  }
}
