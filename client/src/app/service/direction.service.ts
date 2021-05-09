import { HttpClient } from '@angular/common/http';
import { Direction } from '../model/direction';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_URL } from '../constants';

const RESOURCE_URL = `${API_URL}/directions`;

@Injectable({
  providedIn: 'root'
})
export class DirectionService {

  constructor(private http: HttpClient) { }

  getByRecipeId(id: number): Observable<Direction[]> {
    const url = `${RESOURCE_URL}/search/findByRecipeId/${id}`;
    return this.http.get<Direction[]>(url);
  }
}
