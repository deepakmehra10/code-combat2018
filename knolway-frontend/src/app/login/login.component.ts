import { Component, OnDestroy } from '@angular/core';
import { AuthService } from '../shared/common/auth.service';
import { Login } from '../shared/models/login';
import { User } from '../shared/models/user';
import { Router } from '@angular/router';
import { SessionService } from '../shared/common/session.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
/**Login Component */
export class LoginComponent implements OnDestroy {
    user: User = new User();
    userList: User[];
    isSubmitted = false;
    model: Login = new Login();
    formValid = false;
    isUserVerified = true;
    connectOptions: Object;
    authenticateSubscriber: any;

    constructor(private authService: AuthService, private router: Router) {
    }

    ngOnDestroy() {
        if (this.authenticateSubscriber) {
            this.authenticateSubscriber.unsubscribe();
            this.authenticateSubscriber = null;
        }
    }

    toggleUser() {
        this.isUserVerified = true;
    }

    submit(formControl: any) {
        this.isSubmitted = true;
        if (formControl.valid) {
            this.formValid = true;
            btoa(this.model.Username + ':' + this.model.Password);
            this.authenticateSubscriber = this.authService.authenticateUser(this.model).subscribe((result) => {
                this.isUserVerified = result;
            });
        }
    }
}
