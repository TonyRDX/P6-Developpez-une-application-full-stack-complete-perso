import { Component, inject } from '@angular/core';
import { FeedService } from './core/services/feed.service';
import { TopicService } from './core/services/topic.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'front';

  private feedService: FeedService = inject(FeedService);
  private topicService: TopicService = inject(TopicService);

  ngOnInit(): void {
    this.feedService.loadInitialData();
    this.topicService.loadInitialData();
  }

}
