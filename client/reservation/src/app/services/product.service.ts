import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Product } from '../types/product';
import { ProductResponse } from '../types/productResponse';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private url: string = "https://localhost:443/api/v1/products";
  products!: Product[];


  constructor(private http: HttpClient) { }

  getProducts(): Observable<ProductResponse> {
    
    return this.http.get<ProductResponse>(this.url)
  }
}
