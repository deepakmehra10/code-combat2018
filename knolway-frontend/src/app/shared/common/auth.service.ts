import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Login } from '../../shared/models/login';
import { User } from '../../shared/models/user';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Rx';
import { SessionService } from './session.service';
import { Session } from '../../shared/models/session';
import { ResponseModel } from '../../shared/models/response';

@Injectable()
export class AuthService {
    session: Session = null;
    private _isAuthenticated: Subject<boolean> = new Subject<boolean>();

    constructor(private router: Router, private http: Http, private sessionService: SessionService) {
    }

    getUsersList(): Observable<any> {
        return this.http.get('../../../assets/json/user.json')
            .map((response: any) => {
                return response.json();
            }
            )
            .catch(this.handleError);
    }

    authenticateUser(loginModel: Login): Observable<boolean> {
        let response: ResponseModel = new ResponseModel();
        this.getUsersList().subscribe((result) => {
            response.Data = result.find(u => u.username === loginModel.Username && u.password === loginModel.Password);
            if (response.Data !== undefined) {
                this.session = new Session();
                this.session.user = response.Data;
                this.session.cartItems = new Array();
                this.sessionService.set(this.session);
                this.router.navigate(['/dashboard']);
                this._isAuthenticated.next(true);
            }
            else {
                this._isAuthenticated.next(false);
            }
        });
        return this._isAuthenticated.asObservable();
    }

    logOut(session: Session) {
        this.sessionService.clear();
        this.router.navigate(['']);
    }

    private handleError(error: Response) {
        console.error(error);
        const msg = `Error occured`;
        return Observable.throw(msg);
    }
}
