import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { LocalStorageService } from 'src/app/services/localstorage.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-dialog-add-product',
  templateUrl: './dialog-add-product.component.html',
  styleUrls: ['./dialog-add-product.component.css']
})
export class DialogAddProductComponent {
  form: any = {
    name: null,
    description: null,
    price: null,
    quantity: null,
  };
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  constructor(public dialog: MatDialog, private productService: ProductService, private localStorage: LocalStorageService) {}

  onSubmit() {
    this.dialog.closeAll();
    const token = this.localStorage.getUser()?.token;
    this.productService.addProduct(this.form, token)?.subscribe();
  }
}
