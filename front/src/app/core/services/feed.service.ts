import { HttpClient } from '@angular/common/http';
import { inject, Injectable, NgZone, OnDestroy } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Post } from '../models/Post';

@Injectable({
  providedIn: 'root',
})
export class FeedService implements OnDestroy {
  private http = inject(HttpClient);
  private zone = inject(NgZone);

  private readonly feedUrl = environment.feedUrl;
  private readonly feedStreamUrl = `${this.feedUrl}/stream`;

  private readonly feed$ = new BehaviorSubject<Post[]>([]);
  private eventSource?: EventSource;

  loadInitialData(): void {
    this.http.get<Post[]>(this.feedUrl).pipe(
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

    this.eventSource = new EventSource(this.feedStreamUrl);

    this.eventSource.addEventListener("post", event => {
      this.zone.run(() => {
        const newPost: Post = JSON.parse(event.data);
        const updated = [newPost, ...this.feed$.value];

        this.feed$.next(updated);
        console.log('[FeedService] nouveau post reçu', newPost);
      });
    });

    this.eventSource.onerror = (error) => {
      console.error('[FeedService] SSE error', error);
    };
  }

  getFeed(): Observable<Post[]> {
    return this.feed$.asObservable();
  }

  ngOnDestroy(): void {
    this.eventSource?.close();
  }
}
