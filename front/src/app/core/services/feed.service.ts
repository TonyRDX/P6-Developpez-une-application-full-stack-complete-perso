import { HttpClient } from '@angular/common/http';
import { inject, Injectable, OnDestroy } from '@angular/core';
import { BehaviorSubject, concat, Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Post } from '../models/Post';
import { BaseService } from './base.service';
import { SseClient } from 'ngx-sse-client';


@Injectable({
  providedIn: 'root',
})
export class FeedService extends BaseService implements OnDestroy {
  private http = inject(HttpClient);
  private sseClient = inject(SseClient);

  private readonly feedUrl = environment.feedUrl;
  private readonly feedStreamUrl = `${this.feedUrl}/stream`;

  private readonly flux$ = new BehaviorSubject<Post[]>([]);
  private eventSource?: EventSource;

  resetData(): Observable<void> {
    return new Observable<void>(subscriber => {
      this.flux$.next([]); 
      subscriber.complete();
    });
  }

  loadInitialData(): Observable<unknown> {
    return concat(this.fetchData(), this.openSse());
  }

  private fetchData(): Observable<unknown> {
    return this.http.get<Post[]>(this.feedUrl, { headers: this.getHeaders() }).pipe(
        tap((posts) => this.flux$.next(posts)),
        catchError((err) => {
          console.error('[FeedService] loadInitialData error', JSON.stringify(err));
          this.flux$.next([]);
          return of(null);
        })
      )
  }

  private openSse(): Observable<unknown> {
    return this.sseClient.stream(this.feedStreamUrl, 
      {},
      { headers: this.getHeaders() }
    )
    .pipe(
      tap(event => {
        if (event.type !== 'error') {
          const messageEvent = event as MessageEvent;
          const newPost: Post = JSON.parse(messageEvent.data);

          const updated = [newPost, ...this.flux$.value];
          this.flux$.next(updated);

          console.info(`SSE request with type "${messageEvent.type}" and data "${messageEvent.data}"`);
        }  else {
          const errorEvent = event as ErrorEvent;
          console.error(errorEvent.error, errorEvent.message);
        }
      })
    )
  }

  getFeed(): Observable<Post[]> {
    return this.flux$.asObservable();
  }

  ngOnDestroy(): void {
    this.eventSource?.close();
  }
}
