import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { Product } from 'src/app/types/product';
import { LocalStorageService } from 'src/app/services/localstorage.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css'],
})
export class ProductsComponent implements OnInit {
  products!: Product[];

  constructor(
    private productService: ProductService,
    private localStorage: LocalStorageService
  ) { }

  ngOnInit(): void {
    const token = this.localStorage.getUser();
    this.productService.getProducts(token).subscribe((data) => {
      this.products = data.products;
      console.log(this.products);
    });
  }
}
