import { Component, inject, Input } from "@angular/core";
import { CommonModule, AsyncPipe } from  '@angular/common';
import { Post } from "src/app/core/models/Post";
import { input } from '@angular/core';
import { NavigationEnd, Router, RouterModule } from "@angular/router";
import { MatButtonModule } from "@angular/material/button";
import { AuthService } from "src/app/core/services/auth.service";
import { filter, map, Observable, of, switchMap } from "rxjs";

@Component({
  selector: 'header',
  standalone: true,
  imports: [RouterModule, MatButtonModule, CommonModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent {
  private authService: AuthService = inject(AuthService);
  constructor(private router: Router) {}

  onLogout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  isLogged$ = this.authService.getIsLogged$();

  isActive$ = this.router.events.pipe(
    filter((event): event is NavigationEnd => event instanceof NavigationEnd),
    map(event => !event.urlAfterRedirects.startsWith('/home')),
  );
}