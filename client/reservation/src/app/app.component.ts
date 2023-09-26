import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from './services/localstorage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = 'reservation';
  isLoggedIn: boolean = false;

  constructor(
    private localStorage: LocalStorageService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.isLoggedIn = this.localStorage.isLoggedIn();
  }

  logout() {
    // this.localStorage.removeUser();
    this.localStorage.clearStorage();
    this.router.navigate(['/login']).then(() => window.location.reload());
  }
}
