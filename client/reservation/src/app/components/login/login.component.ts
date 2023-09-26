import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/authentication.service';
import { AuthenticationResponse } from 'src/app/types/authenticationResponse';
import { LocalStorageService } from 'src/app/services/localstorage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  form: any = {
    email: null,
    password: null,
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  // roles: string[] = [];

  constructor(
    private authService: AuthService,
    private router: Router,
    private localStorageService: LocalStorageService
  ) { }

  onSubmit(): void {
    this.authService.login(this.form.email, this.form.password).subscribe({
      next: (data: AuthenticationResponse) => {
        console.log(data);
        this.localStorageService.saveUser(data);
        this.router.navigate(['/']).then(() => window.location.reload());
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
}
