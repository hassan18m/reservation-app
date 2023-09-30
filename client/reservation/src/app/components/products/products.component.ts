import { Component, OnInit, SimpleChange } from '@angular/core';
import { LocalStorageService } from 'src/app/services/localstorage.service';
import { ProductService } from 'src/app/services/product.service';
import { UserService } from 'src/app/services/user.service';
import { Product } from 'src/app/types/product';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { DialogAddProductComponent } from '../dialog-add-product/dialog-add-product.component';
import { PageableService } from 'src/app/services/pageable.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css'],
})
export class ProductsComponent implements OnInit {
  products!: Product[];
  totalCount: number = 0;
  currentPage: number = 0;
  pageSize: number = 12;

  constructor(
    private productService: ProductService,
    private localStorage: LocalStorageService,
    private userService: UserService,
    public dialog: MatDialog,
    private pageableService: PageableService,

  ) { }

  ngOnInit(): void {
    const token = this.localStorage.getUser()?.token;

    this.pageableService.findAllProducts("", this.currentPage, this.pageSize, "", "", token).subscribe((data => {
      console.log(data);
      console.log(Math.floor(data.totalCount / this.pageSize) + 1);
      this.products = data.products;
      this.totalCount = data.totalCount;
    }));
  }

  ngOnChanges(changes: SimpleChange) {
    console.log("Changes");
  }

  setCurrentPage(currentPage: number) {
    console.log("Event");
    this.currentPage = currentPage;

    const token = this.localStorage.getUser()?.token;

    this.pageableService.findAllProducts("", this.currentPage, this.pageSize, "", "", token).subscribe((data => {
      console.log(data);
      console.log(Math.floor(data.totalCount / this.pageSize) + 1);
      this.products = data.products;
      this.totalCount = data.totalCount;
    }));

  }


  openDialog() {
    const dialogRef = this.dialog.open(DialogAddProductComponent);

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }

  addProductToUser(productId: number) {
    const user = this.localStorage.getUser();
    this.userService.addProductToUser(productId, user?.token, user?.id).subscribe();
  }
}
