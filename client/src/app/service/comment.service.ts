import { Comment } from '../model/comment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_URL } from '../constants';
import { Observable } from 'rxjs';

const RESOURCE_URL = `${API_URL}/comments`;

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) { }

  getByRecipeId(id: number): Observable<Comment[]> {
    const url = `${RESOURCE_URL}/search/findByRecipeId/${id}`;
    return this.http.get<Comment[]>(url);
  }
}
