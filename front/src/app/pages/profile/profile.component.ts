import { Component, inject, OnInit } from '@angular/core';
import { TopicCardComponent } from 'src/app/components/topic-card/topic-card.component';
import { TopicService } from 'src/app/core/services/topic.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}

  start() {
  }

  private topicService: TopicService = inject(TopicService);
  protected topics$ = this.topicService.getSubscribedTopic();

  onSubscribe(topicId: number) {
    this.topicService.unsubscribe(topicId);
  }
}
