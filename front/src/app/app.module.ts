import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { PostCardComponent } from './components/post-card/post-card.component';
import { HttpClientModule } from '@angular/common/http';
import { FeedComponent } from './pages/feed/feed.component';
import { HeaderComponent } from './components/header/header.component';
import { TopicComponent } from './pages/topics/topics.component';
import { TopicCardComponent } from './components/topic-card/topic-card.component';
import { ProfileComponent } from './pages/profile/profile.component';

@NgModule({
  declarations: [
    AppComponent, 
    HomeComponent, 
    TopicComponent,
    ProfileComponent 
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    PostCardComponent,
    TopicCardComponent,
    FeedComponent,
    HeaderComponent
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
