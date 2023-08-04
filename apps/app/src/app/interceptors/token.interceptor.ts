import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
  HttpStatusCode,
} from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from '../../environments/environment';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if (request.url.includes(environment.backend.serviceUrl)) {
      let idToken = localStorage.getItem('user-token');

      request = request.clone({
        setHeaders: {
          Authorization: 'Bearer ' + idToken,
        },
      });

      return next.handle(request).pipe(
        map((event: HttpEvent<any>) => {
          return event;
        }),
        catchError(
          (
            httpErrorResponse: HttpErrorResponse,
            _: Observable<HttpEvent<any>>
          ) => {
            if (httpErrorResponse.status === HttpStatusCode.Unauthorized) {
              this.authService.redirectToLogin();
            }
            return throwError(httpErrorResponse);
          }
        )
      );
    }

    return next.handle(request);
  }
}
