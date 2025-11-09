import { Component, inject, Input, OnInit } from '@angular/core';
import { FeedService } from 'src/app/core/services/feed.service';
import { MatIconModule } from '@angular/material/icon';
import { BehaviorSubject, combineLatest, map } from 'rxjs';
import { AsyncPipe, CommonModule } from '@angular/common';
import { PostCardComponent } from 'src/app/components/post-card/post-card.component';
import { MatButtonModule  } from '@angular/material/button';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  imports: [MatIconModule, CommonModule, PostCardComponent, MatButtonModule, RouterModule],
  styleUrls: ['./feed.component.scss'],
  standalone: true
})
export class FeedComponent implements OnInit {
  constructor() {}

  @Input() active = true;
  @Input() direction: 'asc' | 'desc' | '' = 'asc';

  private feedService: FeedService = inject(FeedService);

  protected feed$ = this.feedService.getFeed();

  ngOnInit(): void {}

  start() {
    alert('Commencez par lire le README et à vous de jouer !');
  }

  private sortDirectionSubject = new BehaviorSubject<'asc' | 'desc'>('desc');
  sortDirection$ = this.sortDirectionSubject.asObservable();

  sortedItems$ = combineLatest([this.feed$, this.sortDirection$]).pipe(
    map(([feed, direction]) =>
      [...feed].sort((a, b) => {
        const da = new Date(a.created_at).getTime();
        const db = new Date(b.created_at).getTime();
        return direction === 'asc' ? da - db : db - da;
      })
    )
  );

  toggleSort() {
    const current = this.sortDirectionSubject.value;
    this.sortDirectionSubject.next(current === 'asc' ? 'desc' : 'asc');
  }
}
