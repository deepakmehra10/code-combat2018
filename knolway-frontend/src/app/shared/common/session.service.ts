import { Injectable } from '@angular/core';
import { Session } from '../models/session';

@Injectable()
/**Session service */
export class SessionService {

    get(): Session {
        const currentSession = JSON.parse(localStorage.getItem('currentSession'));
        return currentSession;
    }

    set(session: Session) {
        localStorage.setItem('currentSession', JSON.stringify(session));
    }

    remove() {
        localStorage.removeItem('currentSession');
    }

    clear() {
        localStorage.clear();
    }

}

