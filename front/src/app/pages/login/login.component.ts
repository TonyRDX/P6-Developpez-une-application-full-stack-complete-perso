import { Component, inject, Input, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { tap } from 'rxjs';
import { CommonModule } from '@angular/common';
import { PostCardComponent } from 'src/app/components/post-card/post-card.component';
import { MatButtonModule  } from '@angular/material/button';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  imports: [MatIconModule, FormsModule, CommonModule, PostCardComponent, MatButtonModule, RouterModule],
  styleUrls: ['./login.component.scss'],
  standalone: true
})
export class LoginComponent {
  constructor(private router: Router) {}

  formData = {
    identifier: '', 
    password: ''
  };

  failed: Boolean = false;
  private authService: AuthService = inject(AuthService);

  onSubmit() {
    this.authService.login(this.formData).pipe(
      tap((success) => {
          if (success) {this.router.navigate(['/feed'])} 
          else {this.failed = true;}
        })
    )
    .subscribe();
  }

}
