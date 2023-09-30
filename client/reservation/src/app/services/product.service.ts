import { Injectable } from '@angular/core';
import { Product } from '../types/product';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductResponse } from '../types/productResponse';
import { LocalStorageService } from './localstorage.service';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private url: string = 'https://localhost:443/api/v1/products';
  products!: Product[];

  constructor(
    private http: HttpClient,
    private localStorage: LocalStorageService
  ) { }

  getProducts(token?: string): Observable<ProductResponse> {
    if (token) {
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      });
      return this.http.get<ProductResponse>(this.url, { headers: headers });
    } else {
      return this.http.get<ProductResponse>(this.url);
    }
  }

  addProduct(product: Product, token?: string) {
    if (token) {
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      });
      return this.http.post<Product>(this.url, product, { headers: headers });
    } else {
      return null;
    }
  }
}
