import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LocalStorageService } from './localstorage.service';
import { Product } from '../types/product';
import { User } from '../types/user';
import { Observable } from 'rxjs';
import { UserResponse } from '../types/userResponse';
import { ProductResponse } from '../types/productResponse';

@Injectable({
  providedIn: 'root'
})
export class PageableService {
  private url: string = 'https://localhost:443/api/v1/pageable';
  products!: Product[];
  users!: User[];

  constructor(private http: HttpClient, private localStorage: LocalStorageService) { }

  findAllUsers(
    firstName: string,
    page: number,
    size: number,
    sortBy: string,
    direction: string,
    token?: string): Observable<UserResponse> {

    const queryParams = new HttpParams()
      .set('firstName', firstName)
      .set('page', page)
      .set('size', size)
      .set('sortBy', sortBy)
      .set('direction', direction);

    if (token) {
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      });
      return this.http.get<UserResponse>(`${this.url}/users`, { headers: headers, params: queryParams });
    } else {
      return this.http.get<UserResponse>(`${this.url}/users`);
    }
  }

  findAllProducts(
    name?: string,
    page?: number,
    size?: number,
    sortBy?: string,
    direction?: string,
    token?: string): Observable<ProductResponse> {
    if (token) {
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      });
      const queryParams = new HttpParams()
        .set('name', name as string)
        .set('page', page as number)
        .set('size', size as number)
        .set('sortBy', sortBy as string)
        .set('direction', direction as string);
      return this.http.get<ProductResponse>(`${this.url}/products`, { headers: headers, params: queryParams });
    } else {
      return this.http.get<ProductResponse>(`${this.url}/products`);
    }
  }
}
