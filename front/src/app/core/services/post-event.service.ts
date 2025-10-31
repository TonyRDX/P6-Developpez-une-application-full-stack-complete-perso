// src/app/services/post-events.service.ts
import { Injectable, NgZone } from '@angular/core';
import { Observable } from 'rxjs';

export interface PostEvent {
  type: string;
  postId: number;
  timestamp: string;
}

@Injectable({ providedIn: 'root' })
export class PostEventService {

  private readonly url = 'http://localhost:8080/api/posts/stream';

  constructor(private zone: NgZone) {}

  listen(): Observable<PostEvent> {
    return new Observable<PostEvent>(observer => {
      const es = new EventSource(this.url);

      es.onmessage = event => {
        this.zone.run(() => {
          const data: PostEvent = JSON.parse(event.data);
          observer.next(data);
        });
      };

      es.onerror = err => {
        this.zone.run(() => observer.error(err));
        es.close();
      };

      return () => es.close();
    });
  }
}
