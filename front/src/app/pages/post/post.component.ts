import { Component, inject, OnInit } from '@angular/core';
import { FeedService } from 'src/app/core/services/feed.service';
import { TopicService } from 'src/app/core/services/topic.service';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { combineLatest, filter, map, Observable, zip } from 'rxjs';
import { MatIconModule } from '@angular/material/icon';
import { Topic } from 'src/app/core/models/Topic';
import { Post } from 'src/app/core/models/Post';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss'],
  imports: [ CommonModule, MatButtonModule, RouterModule, MatIconModule],
  standalone: true
})
export class PostComponent {

  constructor(private route: ActivatedRoute) {

  }

  private topicService: TopicService = inject(TopicService);
  private feedService: FeedService = inject(FeedService);
  protected topics$ = this.topicService.getTopics();
  protected feed$ = this.feedService.getFeed();
  protected post$ = combineLatest([this.route.paramMap, this.feed$]).pipe(
      map(([params, feed]) => {
        const id = Number(params.get('id') || '-1');
        return feed.find(post => post.id === id) ?? null;
      }),
      filter((post): post is Post => post !== null)
    );

  protected topic$ = combineLatest([this.post$, this.topics$]).pipe(
      map(([post, topics]) => {
        return topics.find(topic => topic.id === post.topic_id) ?? null;
      }),
      filter((topic): topic is Topic => topic !== null)
    );

  onSubscribe(topicId: number) {
    this.topicService.subscribe(topicId);
  }
}
