import { AsyncPipe, CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule  } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { RouterModule } from '@angular/router';
import { tap } from 'rxjs';
import { TopicCardComponent } from 'src/app/components/topic-card/topic-card.component';
import { User } from 'src/app/core/models/User';
import { MeService } from 'src/app/core/services/me.service';
import { TopicService } from 'src/app/core/services/topic.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
  imports: [
    FormsModule, 
    CommonModule, 
    TopicCardComponent, 
    RouterModule, 
    MatButtonModule, 
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule
  ],
  standalone: true
})
export class ProfileComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {
    this.meService.getMe().subscribe((user: User) => {
      this.formData = {
        email: user.email,
        name: user.name,
        password: ''
      };
    });
  }

  start() {
  }

  private topicService: TopicService = inject(TopicService);
  protected topics$ = this.topicService.getSubscribedTopic();
  
  private meService: MeService = inject(MeService);
  failed: Boolean = false;
  topics = this.topicService.getTopics();
  
  formData = {
    email: '',
    name: '',
    password: ''
  };
  
  onUnsubscribe(topicId: number) {
    this.topicService.unsubscribe(topicId);
  }

  onSubmitUser() {
    this.meService.updateUser(this.formData).pipe(
      tap((success: boolean) => {
          if (success) {console.log('sucess')} 
          else {this.failed = true;}
        } )
    )
    .subscribe();
    console.log('Formulaire envoyé :', this.formData);
  }
  
}
