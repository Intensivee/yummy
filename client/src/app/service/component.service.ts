import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {API_URL} from '../constants';
import {Component} from '../model/component';

const RESOURCE_URL = `${API_URL}/components`;

@Injectable({
  providedIn: 'root'
})
export class ComponentService {

  constructor(private http: HttpClient) {
  }

  getAllComponentNames(): Observable<string[]> {
    const url = `${RESOURCE_URL}/names`;
    return this.http.get<string[]>(url);
  }

  getByCategoryId(id: number): Observable<Component[]> {
    const url = `${RESOURCE_URL}/search/findByCategoryId/${id}`;
    return this.http.get<Component[]>(url);
  }
}
