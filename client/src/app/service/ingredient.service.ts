import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_URL } from '../constants';
import { Ingredient } from '../model/ingredient';

const RESOURCE_URL = `${API_URL}/ingredients`;

@Injectable({
  providedIn: 'root'
})
export class IngredientService {

  constructor(private http: HttpClient) { }

  getByRecipeId(id: number): Observable<Ingredient[]> {
    const url = `${RESOURCE_URL}/search/findByRecipeId/${id}`;
    return this.http.get<Ingredient[]>(url);
  }
}
