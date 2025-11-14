import { Component, EventEmitter, Input, Output } from "@angular/core";
import { Comment } from "src/app/core/models/Comment";
import { input } from '@angular/core';
import { CommonModule, DatePipe } from  '@angular/common';
import { MatButtonModule } from "@angular/material/button";

@Component({
  selector: 'comment-card',
  standalone: true,
  imports: [CommonModule, MatButtonModule],
  templateUrl: './comment-card.component.html',
  styleUrls: ['./comment-card.component.scss'],
})
export class CommentCardComponent {
  comment = input.required<Comment>();
}