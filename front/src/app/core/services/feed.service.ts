import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable, NgZone, OnDestroy } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
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

  private readonly feed$ = new BehaviorSubject<Post[]>([]);
  private eventSource?: EventSource;

  loadInitialData(): void {
    this.http.get<Post[]>(this.feedUrl, { headers: this.getHeaders() }).pipe(
      tap((posts) => this.feed$.next(posts)),
      catchError((err) => {
        console.error('[FeedService] loadInitialData error', JSON.stringify(err));
        this.feed$.next([]);
        return of([] as Post[]);
      })
    ).subscribe();

    this.openSse();
  }

  private openSse(): void {
    if (this.eventSource) {
      return;
    }
    this.sseClient.stream(this.feedStreamUrl, 
      {},
      { headers: this.getHeaders() }
    ).subscribe((event) => {
      if (event.type !== 'error') {
        const messageEvent = event as MessageEvent;
        const newPost: Post = JSON.parse(messageEvent.data);

        const updated = [newPost, ...this.feed$.value];
        this.feed$.next(updated);

        console.info(`SSE request with type "${messageEvent.type}" and data "${messageEvent.data}"`);
      }  else {
        const errorEvent = event as ErrorEvent;
        console.error(errorEvent.error, errorEvent.message);
      }
    });
  }

  getFeed(): Observable<Post[]> {
    return this.feed$.asObservable();
  }

  ngOnDestroy(): void {
    this.eventSource?.close();
  }
}
