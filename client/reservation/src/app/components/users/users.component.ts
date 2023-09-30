import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from 'src/app/services/localstorage.service';
import { UserService } from 'src/app/services/user.service';

import { User } from 'src/app/types/user';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
})
export class UsersComponent implements OnInit {
  users!: User[];

  // cache

  constructor(
    private userService: UserService,
    private localStorage: LocalStorageService
  ) { }

  ngOnInit(): void {
    const token = this.localStorage.getUser()?.token;
    this.userService
      .getUsers(token)
      .subscribe((data) => (this.users = data.users));
  }
}
