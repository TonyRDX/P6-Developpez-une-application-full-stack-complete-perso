import { Component, Input } from "@angular/core";
import { Topic } from "src/app/core/models/Topic";
import { input } from '@angular/core';
import { CommonModule, DatePipe } from  '@angular/common';


@Component({
  selector: 'topic-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './topic-card.component.html',
  styleUrls: ['./topic-card.component.scss'],
})
export class TopicCardComponent {
  topic = input.required<Topic>();
}