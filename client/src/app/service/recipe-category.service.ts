import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {RecipeCategory} from '../model/recipe-category';
import {API_URL} from '../constants';

const RESOURCE_URL = `${API_URL}/recipeCategories`;

@Injectable({
  providedIn: 'root'
})
export class RecipeCategoryService {

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<RecipeCategory[]> {
    return this.http.get<RecipeCategory[]>(RESOURCE_URL);
  }

  get6MostPopularWithImages(): Observable<RecipeCategory[]> {
    const url = `${RESOURCE_URL}/top6-with-images`;
    return this.http.get<RecipeCategory[]>(url);
  }
}
