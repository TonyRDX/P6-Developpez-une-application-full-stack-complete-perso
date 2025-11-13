import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { FeedComponent } from './pages/feed/feed.component';
import { TopicComponent } from './pages/topics/topics.component';
import { CreatePostComponent } from './pages/create-post/create-post.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { LoginComponent } from './pages/login/login.component';
import { authGuard } from './core/guards/auth.guard';
import { PostComponent } from './pages/post/post.component';

const routes: Routes = [
  {
    path: '',
    canActivate: [authGuard],
    children: [
      { path: '', component: HomeComponent },
      { path: 'feed', component: FeedComponent },
      { path: 'topics', component: TopicComponent },
      { path: 'create-post', component: CreatePostComponent },
      { path: 'profile', component: ProfileComponent },
      { path: 'post/:id', component: PostComponent },
    ],
  },
  { path: 'login', component: LoginComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
