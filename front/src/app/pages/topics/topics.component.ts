import { Component, inject, OnInit } from '@angular/core';
import { FeedService } from 'src/app/core/services/feed.service';
import { PostCardComponent } from 'src/app/components/post-card/post-card.component';

@Component({
  selector: 'app-topic',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss'],
})
export class TopicComponent implements OnInit {
  constructor() {}

  private feedService: FeedService = inject(FeedService);

  protected feed$ = this.feedService.getFeed();

  ngOnInit(): void {}

  start() {
    alert('Commencez par lire le README et à vous de jouer !');
  }

  post1 = {id: 1, title: "Titre 1", content: "contenu de l'article"};

}
