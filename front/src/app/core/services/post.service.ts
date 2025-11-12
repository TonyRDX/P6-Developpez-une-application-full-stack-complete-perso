import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
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
