import { HttpClient } from '@angular/common/http';
import { inject, Injectable, OnInit } from '@angular/core';
import { BehaviorSubject, concat, Observable, of } from 'rxjs';
import { catchError, map, switchMap, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { User } from '../models/User';
import { BaseService } from './base.service';
import { Comment } from  '../models/Comment';

@Injectable({
  providedIn: 'root',
})
export class CommentService extends BaseService {
  protected http = inject(HttpClient);

  private readonly postUrl = environment.postUrl;
  addComment(postId: number, body: any): Observable<boolean> {
    return this.http.post<Comment>(this.postUrl + "/" + postId + "/comments", body, { headers: this.getHeaders() }).pipe(
          map(() => true),
          catchError(() => of(false))
    );
  }

  getComments(postId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(this.postUrl + "/" + postId + "/comments", { headers: this.getHeaders() }).pipe();
  }
}