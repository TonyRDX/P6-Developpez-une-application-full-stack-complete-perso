import { Component, inject } from '@angular/core';
import { FeedService } from './core/services/feed.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'front';

  private feedService: FeedService = inject(FeedService);

  ngOnInit(): void {
    //this.feedService.loadInitialData();
  }

}
