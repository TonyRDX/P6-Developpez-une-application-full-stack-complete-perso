import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  protected http = inject(HttpClient);
  // easy to implements, and is useful to load data before login end
  public postLoginEffect: () => Observable<unknown> = () => of(null);

  private readonly registerUrl = environment.registerUrl;
  private readonly loginUrl = environment.loginUrl;
  private readonly logoutUrl = environment.logoutUrl;

  register(body: any): Observable<Boolean> {
    return this.http.post(this.registerUrl, body).pipe(
        map(() => true),
        catchError(() => {
          return of(false);
        })
    );
  }

  login(body: any): Observable<boolean> {
    return this.http.post<LoginResponse>(this.loginUrl, body, { withCredentials: true }).pipe(
      map((e => {
          localStorage.setItem('token', e.token);
          return true;
      })
    ));
  }
}

interface LoginResponse {
  token: string;
}