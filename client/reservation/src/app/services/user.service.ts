import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { User } from '../types/user';
import { UserResponse } from '../types/userResponse';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url: string = "https://localhost:443/api/v1/users";
  users!: User[];

  // cache

  constructor(private http: HttpClient) {}
  
  getUsers(): Observable<UserResponse> {
    return this.http.get<UserResponse>(this.url);
    
  }

}