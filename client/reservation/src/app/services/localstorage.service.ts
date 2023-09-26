import { Injectable } from '@angular/core';

const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root',
})
export class LocalStorageService {
  constructor() {}

  public saveUser(token: string) {
    localStorage.setItem(USER_KEY, token);
  }

  public getUser() {
    return localStorage.getItem(USER_KEY);
  }
  public removeUser() {
    localStorage.removeItem(USER_KEY);
  }

  public clearStorage() {
    localStorage.clear();
  }

  public isLoggedIn(): boolean {
    const user = this.getUser();
    if (user) {
      return true;
    }
    return false;
  }
}
