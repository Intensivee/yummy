import { UserRoleType } from '../model/UserRoleType';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { API_URL } from '../constants';
import { JwtHelperService } from '@auth0/angular-jwt';

export const JWT_TOKEN = 'token';
export const AUTHENTICATED_USER = 'authenticatedUsername';
export const ROLE = 'currentRole';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient,
              private router: Router) { }


  public authenticateCredentials(username: string, password: string): Observable<any> {

    return this.http.post<any>(`${API_URL}/authentication/login`, { username, password })
      .pipe(map(
        response => {
          this.setSessionStorage(response);
          return response;
        }
      )
      );
  }

  public registerUser(email: string, username: string, password: string): Observable<any> {
    return this.http.post<any>(`${API_URL}/authentication/register`,
      { email, username, password });
  }

  setSessionStorage(response): void {
    const jwtHelper = new JwtHelperService();
    const token = jwtHelper.decodeToken(response.token);

    sessionStorage.setItem(AUTHENTICATED_USER, token.sub);
    sessionStorage.setItem(JWT_TOKEN, response.token);
    sessionStorage.setItem(ROLE, token.authorities[0].authority);

  }

  isAuthenticated(): boolean {
    const jwtHelper = new JwtHelperService();

    if (!this.getAuthenticatedToken() || !this.getAuthenticatedUsername()) {
      return false;
    }
    return !jwtHelper.isTokenExpired(sessionStorage.getItem(JWT_TOKEN));
  }

  getAuthenticatedUsername(): string {
    return sessionStorage.getItem(AUTHENTICATED_USER);
  }

  getAuthenticatedToken(): string {
    return sessionStorage.getItem(JWT_TOKEN);
  }

  getUserRole(): string {
    if (this.isAuthenticated()) {
      return sessionStorage.getItem(ROLE);
    }
    return '';
  }

  isAdmin(): boolean {
    return this.getUserRole() === UserRoleType.ROLE_ADMIN;
  }

  logout(): void {
    sessionStorage.removeItem(AUTHENTICATED_USER);
    sessionStorage.removeItem(JWT_TOKEN);
    sessionStorage.removeItem(ROLE);
    this.router.navigate(['welcome']);
  }
}
