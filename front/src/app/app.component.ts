import { Component, inject } from '@angular/core';
import { FeedService } from './core/services/feed.service';
import { TopicService } from './core/services/topic.service';
import { AuthService } from './core/services/auth.service';
import { merge } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'front';

  private feedService: FeedService = inject(FeedService);
  private topicService: TopicService = inject(TopicService);
  private authService: AuthService = inject(AuthService);

  ngOnInit(): void {
    this.authService.postLoginEffect = () => merge(
      this.feedService.loadInitialData(),
      this.topicService.loadInitialData()
    );

    this.authService.postLogoutEffect = () => merge(
      this.feedService.resetData(),
      this.topicService.resetData()
    );

    // this.authService.login({
    //       "identifier": "a",
    //       "password": "pw123456789"
    //     })
    //   .subscribe();
  }

}
