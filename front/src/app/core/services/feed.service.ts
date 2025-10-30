import { HttpClient} from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, delay, finalize, retry, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Post } from '../models/Post';

@Injectable({
  providedIn: 'root',
})
export class FeedService {
    protected feedUrl = environment.feedUrl;
    protected readonly feed$ = new BehaviorSubject<Post[]>([]); 
    protected http = inject(HttpClient);

    loadInitialData() {
      this.http.get<Post[]>(this.feedUrl).pipe(
        tap((value) => {
          this.feed$.next(value);
        }),
        catchError((error) => {
          console.error(error);
          return of([]);
        })
      ).subscribe();
    }

    getFeed(): Observable<Post[]> {
      return this.feed$.asObservable();
    }

}