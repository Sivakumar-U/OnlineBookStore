import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
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
    private formBuilder: FormBuilder
  ) {
    this.gridForm = this.formBuilder.group({
      value: ['']
    });
  }


  ngOnInit() {
    this.getItems();
    //this.getBooks();
    this.messageService.onGetAllBooks();
    this.messageService.cartMessage.subscribe((data: any) => {
      this.displayBooksInCart(data);
    });
  }
  ngOnDestroy() {
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
  // getServerData(pageIndex: number) {
  //   this.messageService.sendByPage(pageIndex);
  // }

  onChange() {
    console.log(this.gridForm.value.value)
    if (this.gridForm.value.value === 'high') {
      console.log('high sort')
      this.messageService.changeoptionMessage();

    } else {

      if (this.gridForm.value.value === 'low') {
        this.messageService.changeoptionMessage1();
      }
    }
  }

  private loadAllBooks(data) {
    if (data.status === 200) {
      data.data.forEach((bookData) => {
        this.books.push(bookData);
      });
      // this.snackBar.open(data.message, 'ok', {
      //   duration: 2000,
      // });
    }
  }

  private getItems() {
    this.bookservice.getNumberOfItems().subscribe((data: any) => {
      this.countResult = data.data;
    });
  }
  private getBooks() {
    this.bookservice.getAllbooks().subscribe((data: any) => {
      console.log(data);
      console.log(data.data);

      this.loadAllBooks(data);
    });
  }

  checkAddedToCart(bookId): boolean {
    let addedTocart = false;
    if (localStorage.getItem('cart') !== null) {
      this.cart = JSON.parse(localStorage.getItem('cart'));
      this.cart.cartBooks.forEach((element) => {
        if (element.book.bookId === bookId) {
          addedTocart = true;
        }
      });
    }
    // if (localStorage.getItem('token') !== null) {
    //   this.cart.cartBooks.forEach((element) => {
    //     if (element.book.bookId === bookId) {
    //       addedTocart = true;
    //     }
    //   });
    // }
    return addedTocart;
  }

  // openDialog(book) {
  //   const dialogRef = this.dialog.open(ViewWishlistComponent, {
  //     width: '500px',
  //     data: {
  //       id: book.bookId,
  //       bookname: book.bookName,
  //       bookauthor: book.authorName,
  //       bookprice: book.price,
  //       bookinfo: book.description,
  //       bookImage: book.imageURL
  //     },
  //   });
  // }

  addToCart(book: Book) {
    // localStorage.setItem('cartSize', String(0));
    // console.log("cart size:", localStorage.setItem('cartSize', String(0)));
    if (localStorage.getItem('token') === null) {
      this.cartBook = new CartBookModule();
      this.cartBook.bookQuantity = 1;
      if (localStorage.getItem('cart') === null) {
        this.cart = new CartModule();
        this.cart.totalBooksInCart = 0;
      } else {
        this.cart = JSON.parse(localStorage.getItem('cart'));
      }
      if (this.cart.totalBooksInCart < 5) {
        this.cartBook.book = book;
        this.cartBook.totalBookPrice = Number(book.price);
        this.cart.cartBooks.forEach((element) => {
          if (element.book.bookId === book.bookId) {
            this.cart.cartBooks.splice(this.cart.cartBooks.indexOf(element), 1);
            this.cart.totalBooksInCart--;
            this.snackBar.open('Book Already Added to Cart', 'ok', {
              duration: 2000,
            });
          }
        });
        this.cart.cartBooks.push(this.cartBook);
        this.cart.totalBooksInCart++;
        localStorage.setItem('cart', JSON.stringify(this.cart));
        this.snackBar.open('Book Added to Cart', 'ok', { duration: 2000 });
        localStorage.setItem('cartSize', String(this.cart.totalBooksInCart));
        this.messageService.onRefresh();
      } else {
        this.snackBar.open('Your Cart is full', 'ok', { duration: 2000 });
      }
    } else {
      this.cartService.addToCart(book.bookId).subscribe(
        (data: any) => {
          if (data.status === 200) {
            localStorage.setItem('cartSize', data.data.totalBooksInCart);
            this.messageService.cartBooks();
            this.messageService.onCartCount();
            this.snackBar.open(data.message, 'ok', {
              duration: 2000,
            });
          } else if (data.status === 208) {
            this.snackBar.open(data.message, 'ok', {
              duration: 2000,
            });
          }
        },
        (error: any) => {
          if (error.status === 500) {
            this.snackBar.open('Internal Server Error', 'ok', {
              duration: 2000,
            });
          } else {
            this.snackBar.open(error.error.message, 'ok', {
              duration: 2000,
            });
          }
        }
      );
    }
    this.messageService.sendNumber((this.cart.totalBooksInCart));
  }
}