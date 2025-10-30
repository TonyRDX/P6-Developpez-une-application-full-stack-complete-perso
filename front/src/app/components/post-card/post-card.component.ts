import { Component, Input } from "@angular/core";
import { Post } from "src/app/core/models/Post";
import { input } from '@angular/core';


@Component({
  selector: 'post-card',
  standalone: true,
  imports: [],
  templateUrl: './post-card.component.html',
  styleUrls: ['./post-card.component.scss'],
})
export class PostCardComponent {
  post = input.required<Post>();
}