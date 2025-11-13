import { HttpClient } from '@angular/common/http';
import { inject, Injectable, OnInit } from '@angular/core';
import { BehaviorSubject, concat, Observable, of } from 'rxjs';
import { catchError, map, switchMap, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { User } from '../models/User';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root',
})
export class MeService extends BaseService {
  protected http = inject(HttpClient);

  private readonly meUrl = environment.meUrl;
  updateUser(body: any): Observable<boolean> {
    return this.http.patch<User>(this.meUrl, body, {withCredentials: true,  headers: this.getHeaders() }).pipe(
          map(() => true),
          catchError(() => of(false))
    );
  }

  getMe(): Observable<User> {
    return this.http.get<User>(this.meUrl, { headers: this.getHeaders() }).pipe();
  }
}