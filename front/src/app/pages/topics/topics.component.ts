import { Component, inject, OnInit } from '@angular/core';
import { FeedService } from 'src/app/core/services/feed.service';
import { TopicCardComponent } from 'src/app/components/topic-card/topic-card.component';
import { TopicService } from 'src/app/core/services/topic.service';

@Component({
  selector: 'app-topic',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss'],
})
export class TopicComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}

  start() {
  }

  private topicService: TopicService = inject(TopicService);
  protected topics$ = this.topicService.getTopics();
}
