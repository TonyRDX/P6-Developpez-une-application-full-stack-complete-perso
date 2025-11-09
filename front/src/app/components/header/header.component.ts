import { Component, Input } from "@angular/core";
import { Post } from "src/app/core/models/Post";
import { input } from '@angular/core';
import { RouterModule } from "@angular/router";

@Component({
  selector: 'header',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent {
  
}