import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ComponentService} from './component.service';
import {forkJoin, Observable} from 'rxjs';
import {mergeMap, map} from 'rxjs/operators';
import {ComponentCategory} from '../model/component-category';
import {API_URL} from '../constants';

const RESOURCE_URL = `${API_URL}/componentCategories`;

@Injectable({
  providedIn: 'root'
})
export class ComponentCategoryService {

  constructor(private http: HttpClient,
              private componentService: ComponentService) {
  }

  getAll(): Observable<ComponentCategory[]> {
    const url = `${RESOURCE_URL}`;
    return this.http.get<ComponentCategory[]>(url);
  }

  getAllWithComponentLists(): Observable<ComponentCategory[]> {
    return this.getAll().pipe(
      mergeMap(categories => {
        return forkJoin(
          categories.map(category => {
            return this.componentService.getByCategoryId(category.id).pipe(
              map(components => {
                category.components = components;
                return category;
              })
            );
          }));
      })
    );
  }
}
