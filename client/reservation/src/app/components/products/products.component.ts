import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { Product } from 'src/app/types/product';
import { LocalStorageService } from 'src/app/services/localstorage.service';
import { UserService } from 'src/app/services/user.service';
import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import { DialogAddProductComponent } from '../dialog-add-product/dialog-add-product.component';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css'],
})
export class ProductsComponent implements OnInit {
  products!: Product[];

  constructor(
    private productService: ProductService,
    private localStorage: LocalStorageService,
    private userSerivce: UserService,
    public dialog: MatDialog
  ) { }

  ngOnInit(): void {
    const token = this.localStorage.getUser()?.token;
    this.productService.getProducts(token).subscribe((data) => {
      this.products = data.products;
      console.log(this.products);
    });
  }


  openDialog() {
    const dialogRef = this.dialog.open(DialogAddProductComponent);

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }

  addProductToUser(productId: number) {
    const user = this.localStorage.getUser();
    this.userSerivce.addProductToUser(productId,user?.token,user?.id).subscribe();
  }
}
