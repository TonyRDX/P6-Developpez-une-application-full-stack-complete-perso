import { HttpClient } from '@angular/common/http';
import { inject, Injectable, NgZone, OnDestroy } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, finalize, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Post } from '../models/Post';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root',
})
export class PostService extends BaseService {
  private http = inject(HttpClient);

  private readonly postUrl = environment.postUrl;

  createPost(body: any): Observable<Boolean> {
    return this.http.post(this.postUrl, body, {headers: this.getHeaders()}).pipe(
        map(() => true),
        catchError(() => {
          return of(false);
        })
    );
  }
}
