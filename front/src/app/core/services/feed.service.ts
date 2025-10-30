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
    protected readonly feed$ = new BehaviorSubject<Post[]>([
        {id: 1, title: "Titre 1", content: "contenu de l'article"}, 
        {id: 2, title: "Titre 2", content: "contenu de l'article 2"}
      ]); 
    protected http = inject(HttpClient);

    loadInitialData() {
      // this.feed$.next([
      //   {id: 1, title: "Titre 1", content: "contenu de l'article"}, 
      //   {id: 2, title: "Titre 2", content: "contenu de l'article 2"}
      // ]);
    }

    getFeed(): Observable<Post[]> {
      return this.feed$.asObservable();
    }

}