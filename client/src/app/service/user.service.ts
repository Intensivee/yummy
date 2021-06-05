import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {API_URL} from '../constants';
import {User} from '../model/user';

const RESOURCE_URL = `${API_URL}/users`;

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  getById(userId: number): Observable<User> {
    const url = `${RESOURCE_URL}/${userId}`;
    return this.http.get<User>(url);
  }

  getByUsername(username: string): Observable<User> {
    const url = `${RESOURCE_URL}/search/findByUsername/${username}`;
    return this.http.get<User>(url);
  }

  editUser(user: User): Observable<any> {
    const url = `${RESOURCE_URL}`;
    return this.http.patch<any>(url, user);
  }
}
