import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {API_URL} from '../constants';
import {Recipe} from '../model/recipe';
import {Observable} from 'rxjs';
import {PdfDetails} from '../model/pdf-details';

const RESOURCE_URL = `${API_URL}/recipes`;

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  constructor(private http: HttpClient) {
  }

  getById(id: number): Observable<Recipe> {
    const url = `${RESOURCE_URL}/${id}`;
    return this.http.get<Recipe>(url);
  }

  getPaged(page: number, pageSize: number): Observable<GetPagedResponse> {
    const url = `${RESOURCE_URL}?page=${page}&size=${pageSize}`;
    return this.http.get<GetPagedResponse>(url);
  }

  getPagedByCategoryName(category: string, page: number, pageSize: number)
    : Observable<GetPagedResponse> {
    const url = `${RESOURCE_URL}/search/findByCategoryName/${category}?page=${page}&size=${pageSize}`;
    return this.http.get<GetPagedResponse>(url);
  }

  getPagedByComponentName(component: string, page: number, pageSize: number)
    : Observable<GetPagedResponse> {
    const url = `${RESOURCE_URL}/search/findByComponentName/${component}?page=${page}&size=${pageSize}`;
    return this.http.get<GetPagedResponse>(url);
  }

  getPagedBySearchKey(searchKey: string, page: number, pageSize: number)
    : Observable<GetPagedResponse> {
    const url = `${RESOURCE_URL}/search/findByTitleContaining/${searchKey}?page=${page}&size=${pageSize}`;
    return this.http.get<GetPagedResponse>(url);
  }

  getByUsernamePaged(username: string, page: number, pageSize: number): Observable<GetPagedResponse> {
    const url = `${RESOURCE_URL}/search/findByUsername/${username}?page=${page}&size=${pageSize}`;
    return this.http.get<GetPagedResponse>(url);
  }

  get3TopRatedRecipes(): Observable<Recipe[]> {
    const url = `${RESOURCE_URL}/search/top3`;
    return this.http.get<Recipe[]>(url);
  }

  getRecipesByTimeTypePaged(timeType: string, page: number, pageSize: number): Observable<GetPagedResponse> {
    const url = `${RESOURCE_URL}/search/findByTimeType/${timeType}?page=${page}&size=${pageSize}`;
    return this.http.get<GetPagedResponse>(url);
  }

  createRecipe(recipeCreateRequest: any): Observable<Recipe> {
    console.log(recipeCreateRequest);
    return this.http.post<Recipe>(RESOURCE_URL, recipeCreateRequest);
  }

  editRecipe(recipeEditRequest: any): Observable<Recipe> {
    const url = `${RESOURCE_URL}/` + recipeEditRequest.id;
    return this.http.put<Recipe>(url, recipeEditRequest);
  }

  deleteById(id: number): Observable<any> {
    const url = `${RESOURCE_URL}/${id}`;
    return this.http.delete<any>(url);
  }

  exportToPdf(id: number, pdfDetails: PdfDetails): Observable<any> {
    const httpOptions = {
      responseType: 'arraybuffer' as 'json',
      params: JSON.parse(JSON.stringify(pdfDetails))
    };
    const url = `${RESOURCE_URL}/${id}/export/pdf`;
    return this.http.get<any>(url, httpOptions);
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
