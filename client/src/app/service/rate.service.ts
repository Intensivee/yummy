import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {API_URL} from '../constants';


const RESOURCE_URL = `${API_URL}/rates`;

@Injectable({
  providedIn: 'root'
})
export class RateService {

  constructor(private http: HttpClient) {
  }


  addOrReplaceRate(rate: number, recipeId: number): Observable<number> {
    return this.http.post<number>(RESOURCE_URL, {value: rate, recipeId});
  }
}
