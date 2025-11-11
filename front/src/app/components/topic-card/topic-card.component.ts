import { Component, EventEmitter, Input, Output } from "@angular/core";
import { Topic } from "src/app/core/models/Topic";
import { input } from '@angular/core';
import { CommonModule, DatePipe } from  '@angular/common';
import { MatButtonModule } from "@angular/material/button";


@Component({
  selector: 'topic-card',
  standalone: true,
  imports: [CommonModule, MatButtonModule],
  templateUrl: './topic-card.component.html',
  styleUrls: ['./topic-card.component.scss'],
})
export class TopicCardComponent {
  topic = input.required<Topic>();
  subscribeMode = input.required<boolean>();
  @Output() clicked = new EventEmitter<number>();
  onClick() {
    this.clicked.emit(this.topic().id);
  }
}