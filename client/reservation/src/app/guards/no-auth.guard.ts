import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { LocalStorageService } from '../services/localstorage.service';
@Injectable({
  providedIn: 'root'
})
export class NoAuthGuard implements CanActivate {
  constructor(private localStorageService: LocalStorageService, private router: Router) { };
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
    console.log('CanActivate called');
    let isLoggedIn = this.localStorageService.isLoggedIn();
    if (isLoggedIn) {
      return false
    } else {
      return true;
    }
  }

}