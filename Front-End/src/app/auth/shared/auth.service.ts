import { EventEmitter, Injectable, Output } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SignupRequestPayload } from '../signup-request-payload';
import { Observable } from 'rxjs';
import { LoginRequestPayload } from '../login/loginRequest.payload';
import { LocalStorageService } from 'ngx-webstorage';
import { map, tap } from 'rxjs/operators';
import { LoginResponse } from '../login/loginResponse.payload';



@Injectable({
  providedIn: 'root'
})
export class AuthService {

  @Output() loggedIn: EventEmitter<boolean> = new EventEmitter();
  @Output() username: EventEmitter<string> = new EventEmitter();

  constructor(private httpClient: HttpClient,
    private localStorage: LocalStorageService) {
  }

  refreshTokenPayload = {
    refreshToken: this.getRefreshToken(),
    username: this.getUserName()
  }

  signup(signupReq: SignupRequestPayload): Observable<any> {
    return this.httpClient.post('http://localhost:8080/api/auth/signup', signupReq, { responseType: 'text' });
  }


  login(loginReq: LoginRequestPayload): Observable<any> {
    return this.httpClient.post<LoginResponse>('http://localhost:8080/api/auth/login', loginReq).pipe(
      map(data => {
        this.localStorage.store('authenticationToken', data.authenticationToken);
        this.localStorage.store('username', data.username);
        this.localStorage.store('refreshToken', data.refreshToken);
        this.localStorage.store('expiresAt', data.expiresAt);
        console.log('authenticationToken', data.authenticationToken);
        this.loggedIn.emit(true);
        this.username.emit(data.username);


        return true;
      })
    );
  }

  getJwtToken() {
    return this.localStorage.retrieve('authenticationToken');
  }

  getRefreshToken() {
    return this.localStorage.retrieve('refreshToken');
  }

  getUserName() {
    return this.localStorage.retrieve('username');
  }
  refreshToken() {
    const refreshTokenPayload = {
      refreshToken: this.getRefreshToken(),
      username: this.getUserName()
    }
    return this.httpClient.post<LoginResponse>('http://localhost:8080/api/auth/refresh/token',
      this.refreshTokenPayload)
      .pipe(
        tap(response => {
          this.localStorage.clear('authenticationToken');
          this.localStorage.clear('expiresAt');
          this.localStorage.store('authenticationToken', response.authenticationToken);
          this.localStorage.store('expiresAt', response.expiresAt);

        })
      );
  }
  isLoggedIn(): boolean {

    return this.getJwtToken() != null;
  }

}
