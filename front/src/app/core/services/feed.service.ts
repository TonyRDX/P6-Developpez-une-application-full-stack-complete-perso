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

  // REST : ex. http://localhost:8080/api/feed
  private readonly feedUrl = environment.feedUrl;

  // SSE : ex. http://localhost:8080/api/posts/stream
  private readonly feedStreamUrl = "http://localhost:8080/api/posts/stream";

  private readonly feed$ = new BehaviorSubject<Post[]>([]);
  private eventSource?: EventSource;

  /**
   * À appeler une seule fois (par ex. dans un resolver, ou dans le composant root)
   */
  loadInitialData(): void {
    // 1) on charge la liste initiale
    this.http.get<Post[]>(this.feedUrl).pipe(
      tap((posts) => this.feed$.next(posts)),
      catchError((err) => {
        console.error('[FeedService] loadInitialData error', JSON.stringify(err));
        this.feed$.next([]);
        return of([] as Post[]);
      })
    ).subscribe();

    // 2) on ouvre le flux SSE
    this.openSse();
  }

  private openSse(): void {
    // sécurité : éviter d’ouvrir deux fois
    if (this.eventSource) {
      return;
    }

    this.eventSource = new EventSource(this.feedStreamUrl);

    this.eventSource.onmessage = (event) => {
      // on est hors Angular → on repasse dans la zone
      this.zone.run(() => {
        const newPost: Post = JSON.parse(event.data);
        const current = this.feed$.value;

        // selon ce que tu veux faire : prepend ou append
        const updated = [newPost, ...current];

        this.feed$.next(updated);
        console.log('[FeedService] nouveau post reçu', newPost);
      });
    };

    this.eventSource.onerror = (error) => {
      console.error('[FeedService] SSE error', error);
      // tu peux éventuellement fermer
      // this.eventSource?.close();
      // this.eventSource = undefined;
    };
  }

  getFeed(): Observable<Post[]> {
    return this.feed$.asObservable();
  }

  ngOnDestroy(): void {
    this.eventSource?.close();
  }
}
