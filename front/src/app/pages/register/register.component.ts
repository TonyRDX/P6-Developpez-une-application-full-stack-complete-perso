import { Component, inject} from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { tap } from 'rxjs';
import { CommonModule } from '@angular/common';
import { PostCardComponent } from 'src/app/components/post-card/post-card.component';
import { MatButtonModule  } from '@angular/material/button';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth.service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  imports: [
    FormsModule, 
    CommonModule, 
    PostCardComponent, 
    MatIconModule, 
    MatButtonModule, 
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    RouterModule
  ],
  styleUrls: ['./register.component.scss'],
  standalone: true
})
export class RegisterComponent {
  constructor(private router: Router) {}

  formData = {
    email: '', 
    name: '', 
    password: ''
  };

  failed: Boolean = false;
  private authService: AuthService = inject(AuthService);

  onSubmit() {
    this.authService.register(this.formData).pipe(
      tap((success) => {
          if (success) {this.router.navigate(['/login'])} 
          else {this.failed = true;}
        })
    )
    .subscribe();
  }

}
