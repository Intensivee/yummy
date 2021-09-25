import {Comment} from '../model/comment';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {API_URL} from '../constants';
import {Observable} from 'rxjs';

const RESOURCE_URL = `${API_URL}/comments`;

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) {
  }

  createComment(body: string, recipeId: number): Observable<Comment> {
    return this.http.post<Comment>(RESOURCE_URL, {body, recipeId});
  }

  getByRecipeId(id: number): Observable<Comment[]> {
    const url = `${RESOURCE_URL}/search/findByRecipeId/${id}`;
    return this.http.get<Comment[]>(url);
  }

  deleteCommentById(id: number): Observable<any> {
    const url = `${RESOURCE_URL}/` + id;
    return this.http.delete<any>(url);
  }
}
