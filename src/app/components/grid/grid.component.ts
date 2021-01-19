import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Book } from 'src/models/book.model';
import { CartBookModule } from 'src/models/cart-book/cart-book.module';
import { CartModule } from 'src/models/cart/cart.module';
import { BookService } from 'src/services/book.service';
import { CartServiceService } from 'src/services/cart.service';
import { MessageService } from 'src/services/message.service';

@Component({
  selector: 'app-grid',
  templateUrl: './grid.component.html',
  styleUrls: ['./grid.component.scss']
})
export class GridComponent implements OnInit {
  gridForm: any;
  pageIndex: number;
  countResult: any;
  books = [];
  cart: CartModule;
  data: any;
  cartBook: CartBookModule;

  constructor(
    private bookservice: BookService,
    private snackBar: MatSnackBar,
    private cartService: CartServiceService,
    private messageService: MessageService,
    public dialog: MatDialog,
    public router: Router,
    private formBuilder: FormBuilder
  ) {
    this.gridForm = this.formBuilder.group({
      value: ['']
    });
  }

  ngOnInit() {
    this.messageService.onGetAllBooks();
    this.getItems();
    //this.getCartCount();
    this.messageService.cartMessage.subscribe((data: any) => {
      this.displayBooksInCart(data);
    });
  }

  private getItems() {
    this.bookservice.getNumberOfItems().subscribe((data: any) => {
      this.countResult = data.data;
    });
  }
  displayBooksInCart(data) {
    if (data.status === 200) {
      this.cart = data.data;
    }
    this.messageService.currentUserMessage.subscribe((data) => {
      this.books = [];
      this.loadAllBooks(data);
    });
  }
  onChange() {
    console.log(this.gridForm.value.value)
    if (this.gridForm.value.value === 'high') {
      console.log('high sort')
      this.messageService.changeoptionMessage();

    }
    if (this.gridForm.value.value === 'low') {
      this.messageService.changeoptionMessage1();
    }

    if (this.gridForm.value.value === 'NewArrivals') {
      this.messageService.changeoptionMessage2();
    }
  }

  private loadAllBooks(data) {
    if (data.status === 200) {
      data.data.forEach((bookData) => {
        this.books.push(bookData);
      });
    }
  }
  addToCart(book: Book) {
    if (localStorage.getItem('token') == '' || localStorage.getItem('token') == null) {
      this.snackBar.open('User should login first', 'ok', { duration: 2000 });
      this.router.navigate(["/login"]);
    }
    else {
      this.cartService.addToCart(book.bookId).subscribe((data: any) => {
        if (data.status === 200) {
          console.log(data);
          this.snackBar.open('Book added to cart successfully', 'ok', { duration: 2000 });
        } else {
          this.snackBar.open(data.message, 'ok', { duration: 2000, });
        }
      });
      if (localStorage.getItem('token') == null || localStorage.getItem('token') == '') {
        this.messageService.sendNumber(0);
      }
      else {
        this.cartService.cartCount().subscribe(response => {
          console.log(response.message);
          this.messageService.sendNumber(response+1);
        }, error => { })
      }
    }
  }
  // getCartCount() {
  //   if (localStorage.getItem('token') == null || localStorage.getItem('token') == '') {
  //     this.messageService.sendNumber(0);
  //   }
  //   else {
  //     this.cartService.cartCount().subscribe(response => {
  //       console.log(response.message);
  //       this.messageService.sendNumber(response);
  //     }, error => { })
  //   }
  // }
  addToWishList(){
    this.snackBar.open('Book added to wishlist successfully', 'ok', { duration: 2000 });
    console.log("hello to wishlist");
  }
}