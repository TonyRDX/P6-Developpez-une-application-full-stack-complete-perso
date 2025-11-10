import { HttpClient } from '@angular/common/http';
import { inject, Injectable, NgZone, OnDestroy } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Topic } from '../models/Topic';

@Injectable({
  providedIn: 'root',
})
export class TopicService {
  getFeed() {
    throw new Error('Method not implemented.');
  }
  private http = inject(HttpClient);

  private readonly topicUrl = environment.topicUrl;
  private readonly flux$ = new BehaviorSubject<Topic[]>([]);

  loadInitialData(): void {
    this.http.get<Topic[]>(this.topicUrl).pipe(
      tap((posts) => this.flux$.next(posts)),
      catchError((err) => {
        console.error('[TopicService] loadInitialData error', JSON.stringify(err));
        this.flux$.next([]);
        return of([] as Topic[]);
      })
    ).subscribe();
  }

  getTopics(): Observable<Topic[]> {
    return this.flux$.asObservable();
  }

  subscribe(topicId: number): Observable<boolean> {
    return this.http.put(this.topicUrl + "/" + topicId + "/subscription", "").pipe(
        map(() => true),
        catchError(() => {
          return of(false);
        }));
  }

}
