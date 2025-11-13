import { HttpClient } from '@angular/common/http';
import { inject, Injectable, OnInit } from '@angular/core';
import { BehaviorSubject, concat, Observable, of } from 'rxjs';
import { catchError, map, switchMap, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  protected http = inject(HttpClient);
  // easy to implements, and is useful to load data before login end
  public postLoginEffect: () => Observable<unknown> = () => of(null);
  public postLogoutEffect: () => Observable<unknown> = () => of(null);

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
          map(e => {
            this.setToken(e.token);
            return true;
          }),
          switchMap(() => this.postLoginEffect().pipe(map(() => true))),
          catchError(err => of(false))
    );
  }

  logout(): void {
    this.removeToken();
    this.postLogoutEffect().subscribe();
  }

  tryReloadAuth(): void {
    let hasValidToken: boolean = this.getIsLogged();
    this.isLogged$.next(hasValidToken);
    if (hasValidToken) this.postLoginEffect().subscribe();
  }

  private setToken(token: string): void {
    localStorage.setItem('token', token);
    this.isLogged$.next(true);
  }

  private removeToken(): void {
    localStorage.removeItem('token');
    this.isLogged$.next(false);
  }

  getIsLogged$(): Observable<boolean> {
    return this.isLogged$.asObservable();
  }

  getIsLogged(): boolean {
    let token: string | null = localStorage.getItem('token');
    if (token === null) return false;
    let payload: any = JSON.parse(atob(token.split('.')[1]));
    return payload.exp < Date.now();
  }

  private isLogged$ = new BehaviorSubject<boolean>(false);
}

interface LoginResponse {
  token: string;
}