import { Injectable } from '@angular/core';
import { User } from '../types/user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserResponse } from '../types/userResponse';
import { LocalStorageService } from './localstorage.service';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private url: string = 'https://localhost:443/api/v1/users';
  users!: User[];

  // cache

  constructor(
    private http: HttpClient,
    private localStorage: LocalStorageService
  ) { }

  getUsers(token: string | null): Observable<UserResponse> {
    if (token) {
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      });
      return this.http.get<UserResponse>(this.url, { headers: headers });
    } else {
      return this.http.get<UserResponse>(this.url); //403 - Forbidden
    }
  }
}
