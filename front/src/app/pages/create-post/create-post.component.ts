import { Component, inject, Input, OnInit } from '@angular/core';
import { FeedService } from 'src/app/core/services/feed.service';
import { MatIconModule } from '@angular/material/icon';
import { BehaviorSubject, catchError, combineLatest, finalize, map, Observable, of, tap } from 'rxjs';
import { AsyncPipe, CommonModule } from '@angular/common';
import { PostCardComponent } from 'src/app/components/post-card/post-card.component';
import { MatButtonModule  } from '@angular/material/button';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { TopicService } from 'src/app/core/services/topic.service';
import { PostService } from 'src/app/core/services/post.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  imports: [MatIconModule, FormsModule, CommonModule, PostCardComponent, MatButtonModule, RouterModule],
  styleUrls: ['./create-post.component.scss'],
  standalone: true
})
export class CreatePostComponent implements OnInit {
  constructor(private router: Router) {}

  ngOnInit(): void {}

  start() {
  }

  formData = {
    topic_id: 0,
    title: '',
    content: ''
  };

  private topicService: TopicService = inject(TopicService);
  private postService: PostService = inject(PostService);
  failed: Boolean = false;
  topics = this.topicService.getTopics();

  onSubmit() {
    this.postService.createPost(this.formData).pipe(
      tap((success) => {
          if (success) {this.router.navigate(['/feed'])} 
          else {this.failed = true;}
        } )
    )
    .subscribe();
    console.log('Formulaire envoyé :', this.formData);
  }

}
