import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_URL } from '../constants';
import { Recipe } from '../model/recipe';
import {Observable} from 'rxjs';

const RESOURCE_URL = `${API_URL}/recipes`;

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  constructor(private http: HttpClient) { }

  getPaged(page: number, pageSize: number): Observable<GetPagedResponse> {
    const url = `${RESOURCE_URL}?page=${page}&size=${pageSize}`;
    return this.http.get<GetPagedResponse>(url);
  }

  getByUserIdPaged(userId: number, page: number, pageSize: number): Observable<GetPagedResponse> {
    const url = `${RESOURCE_URL}/search/findByUserId/${userId}?page=${page}&size=${pageSize}`;
    return this.http.get<GetPagedResponse>(url);
  }

}

interface GetPagedResponse {
  content: {
    recipes: Recipe[];
  };
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
}
