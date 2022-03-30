import {Router} from '@angular/router';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {HttpStatusCode} from '../shared/HttpStatusCode';

@Injectable({
  providedIn: 'root'
})
export class ErrorHttpInterceptorService implements HttpInterceptor {

  constructor(private router: Router) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError((error) => {
        if (error instanceof HttpErrorResponse && error?.status === HttpStatusCode.INTERNAL_SERVER_ERROR) {
          this.handleServerSideError();
        } else {
          return throwError(error);
        }
      })
    );
  }

  private handleServerSideError(): void {
    this.router.navigate(['/error']);
  }
}
