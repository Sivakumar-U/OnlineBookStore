import { Component, OnInit, OnChanges } from '@angular/core';
import { Router } from '@angular/router';
import { CartModule } from 'src/models/cart/cart.module';
import { CartBookModule } from 'src/models/cart-book/cart-book.module';
import { CartServiceService } from 'src/services/cart.service';
import { MatSnackBar } from '@angular/material/snack-bar';
//import { MatDialogModule } from '@angular/material/dialog';
import { MessageService } from 'src/services/message.service';
import { FormBuilder, FormGroup, FormsModule } from '@angular/forms';
import { UserService } from 'src/services/user.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit, OnChanges {
  public show = false;
  public buttonName: any = 'Show';
  cartSize: number = 0;
  cartBooks: any = [];
  quantity: number;
  bookSum: any = [];
  disp = false;
  cart: CartModule;
  totalPrice: number;

  constructor(
    private cartService: CartServiceService,
    private snackBar: MatSnackBar,
    private messageService: MessageService,
    private fb: FormBuilder,
    private userService: UserService,
    private route: Router
  ) { }

  addressGroup = this.fb.group({
    fullName: [],
    phoneNumber: [],
    pinCode: [],
    locality: [],
    address: [],
    city: [],
    landMark: [],
    locationType: [],
  });
  ngOnInit() {
    this.messageService.cartMessage.subscribe((data) => {
      this.cartBooks = [];
      this.totalPrice = 0;
      this.displayBooksInCart(data);
      localStorage.setItem('cartSize', localStorage.getItem('cartSize'));
    });
    this.cartSize = Number(localStorage.getItem('cartSize'));
    console.log("Cart size", this.cartSize);
    this.messageService.quantityMessage.subscribe((data) => {
      this.onUpdateQuantity(data);
      localStorage.setItem('cartSize', localStorage.getItem('cartSize'));
    });
  }
  ngOnChanges() {
    this.messageService.onCartRefresh();
  }

  removeFromCart(cartBook: any) {
    if (
      localStorage.getItem('token') === null &&
      localStorage.getItem('cart') != null
    ) {
      this.cart = JSON.parse(localStorage.getItem('cart'));
      this.cart.cartBooks.forEach((element) => {
        if (element.book.bookId === cartBook.book.bookId) {
          this.cart.totalBooksInCart =
            this.cart.totalBooksInCart - element.bookQuantity;
          this.cart.cartBooks.splice(this.cart.cartBooks.indexOf(element), 1);
        }
      });
      localStorage.setItem('cart', JSON.stringify(this.cart));
      console.log(localStorage.getItem('cartSize'));
      localStorage.setItem('cartSize', String(this.cart.totalBooksInCart));
      this.messageService.cartBooks();
      this.messageService.onCartRefresh();
      this.snackBar.open('Book Removed From Cart', 'ok', {
        duration: 2000,
      });
    } else {
      this.cartService.removeFromCart(cartBook.cartBookId).subscribe(
        (data: any) => {
          if (data.status === 200) {
            localStorage.setItem('cartSize', data.data.totalBooksInCart);
            this.messageService.cartBooks();
            this.messageService.onCartCount();
            this.snackBar.open(data.message, 'ok', { duration: 2000, });
          }
        },
        (error: any) => {
          this.snackBar.open(error.error.message, 'ok', {
            duration: 2000,
          });
        }
      );
    }
  }

  displayBooksInCart(data) {
    if (localStorage.getItem('token') === null) {
      this.cartSize =  Number(localStorage.getItem('cartSize'));
      //data.totalBooksInCart;
      this.cart = data.cartBooks.forEach((cartBookData) => {
        this.cartBooks.push(cartBookData);

      });
    } else {
      if (data.status === 200) {
        this.cartSize = Number(localStorage.getItem('cartSize'));
        //data.data.totalBooksInCart;
        console.log("data data: ", data.data);
        console.log("data data cartBooks: ", data.data.cartBooks);
        data.data.forEach((cartBookData) => {
          console.log("cart book data: ", cartBookData);
          this.totalPrice = this.totalPrice + cartBookData.totalBookPrice;
          this.cartBooks.push(cartBookData);
          console.log("cart books: ", this.cartBooks, " cart book data: ", cartBookData);
        });
      }
    }
  }

  addQuantity(cartBook: any) {
    if (
      localStorage.getItem('token') === null &&
      localStorage.getItem('cart') != null
    ) {
      this.cart = JSON.parse(localStorage.getItem('cart'));
      if (this.cart.totalBooksInCart < 5) {
        this.cart.cartBooks.forEach((element) => {
          if (element.book.bookId === cartBook.book.bookId) {
            if (element.bookQuantity < cartBook.book.quantity) {
              element.bookQuantity++;
              element.totalBookPrice += cartBook.book.price;
              this.cart.totalBooksInCart++;
            } else {
              this.snackBar.open('Book Out Of Stock', 'ok', { duration: 2000 });
            }
          }
        });
        localStorage.setItem('cart', JSON.stringify(this.cart));
        localStorage.setItem('cartSize', String(this.cart.totalBooksInCart));
        this.messageService.cartBooks();
        this.messageService.onCartRefresh();
        this.snackBar.open('Book Quantity Increased', 'ok', {
          duration: 2000,
        });
      } else {
        this.snackBar.open('Cart is full', 'ok', { duration: 2000 });
      }
    } else {
      this.cartService.addQuantity(cartBook.cartBookId).subscribe(
        (data: any) => {
          if (data.status === 200) {
            localStorage.setItem('cartSize', String(data.data.totalBooksInCart));
            this.messageService.cartBooks();
            this.messageService.onCartCount();
            this.snackBar.open(data.message, 'ok', {
              duration: 2000,
            });
          }
        },
        (error: any) => {
          this.snackBar.open(error.error.message, 'ok', {
            duration: 20000,
          });
        }
      );
    }
  }

  onPlaceOrder() {
    if (localStorage.getItem('token') === null) {
      //this.dialog.open(LoginComponent);
      this.route.navigate(['/login']);
    }
    this.show = true;
    this.checkAddressExistornot();
    this.snackBar.open('Fill the details', 'ok', { duration: 2000 });
    console.log("Fill the details to place an order");
  }

  continue() {
    this.cartService.displayBooksInCart().subscribe((response: any) => {
      console.log('book in cart:', response);
      this.bookSum = response.data.cartBooks;
      this.bookSum.forEach(function (val) {
        console.log('book:', val);
        console.log('name:', val.book.bookName);
      });
    });
    this.disp = true;
  }

  removeQuantity(cartBook: any) {
    if (
      localStorage.getItem('token') === null &&
      localStorage.getItem('cart') != null
    ) {
      this.cart = JSON.parse(localStorage.getItem('cart'));
      if (this.cart.totalBooksInCart > 0) {
        this.cart.cartBooks.forEach((element) => {
          if (element.book.bookId === cartBook.book.bookId) {
            if (element.bookQuantity > 1) {
              element.bookQuantity--;
              element.totalBookPrice -= cartBook.book.price;
              this.cart.totalBooksInCart--;
            } else {
              this.snackBar.open('Cart items cant be less than 1', 'ok', {
                duration: 2000,
              });
            }
          }
        });
        localStorage.setItem('cart', JSON.stringify(this.cart));
        localStorage.setItem('cartSize', String(this.cart.totalBooksInCart));
        this.messageService.cartBooks();
        this.messageService.onCartRefresh();
        this.snackBar.open('Book Quantity Decreased', 'ok', {
          duration: 2000,
        });
      } else {
        this.snackBar.open('No Items In cart To remove quantity', 'ok', {
          duration: 2000,
        });
      }
    } else {
      this.cartService.removeQuantity(cartBook.cartBookId).subscribe(
        (data: any) => {
          if (data.status === 200) {
            localStorage.setItem('cartSize', data.data.totalBooksInCart);
            this.messageService.cartBooks();
            this.messageService.onCartCount();
            this.snackBar.open(data.message, 'ok', {
              duration: 2000,
            });
          }
        },
        (error: any) => {
          this.snackBar.open(error.message, 'ok', {
            duration: 20000,
          });
        }
      );
    }
  }
  onCheckOut() {
    const data = {
      fullName: this.addressGroup.get('fullName').value,
      phoneNumber: this.addressGroup.get('phoneNumber').value,
      pinCode: this.addressGroup.get('pinCode').value,
      locality: this.addressGroup.get('locality').value,
      address: this.addressGroup.get('address').value,
      city: this.addressGroup.get('city').value,
      landMark: this.addressGroup.get('landMark').value,
      locationType: this.addressGroup.get('locationType').value,
      state: this.addressGroup.get('state').value
    };
    this.userService.Address(data).subscribe((result: any) => {
      if (result.status == 200) {
        this.snackBar.open('Address added', 'ok', { duration: 5000 });
      }
    });
    this.userService.onCheckOut().subscribe(
      (data) => {
        if (data.status === 200) {
          this.messageService.onCartCount();
          localStorage.setItem('orderId', data.data.orderId);
          this.snackBar.open(data.message, 'ok', {
            duration: 2000,
          });
          this.route.navigate(['/success-page']);
        }
      },
      (error: any) => {
        this.snackBar.open(error.error.message, 'ok', {
          duration: 2000,
        });
      }
    );
  }
  onShowNow() {
    this.route.navigate(['/home']);
  }

  onKey(event: any, cartBook: CartBookModule) {
    if (localStorage.getItem('token') === null && localStorage.getItem('cart') !== null) {
      this.quantity = 0;
      this.quantity = Number(event.target.value);
      if (this.quantity === 0 || event.target.value === '') {
        this.snackBar.open('Cart items cannot be less than one', 'cancel', {
          duration: 2000
        });
      } else {
        this.cart = JSON.parse(localStorage.getItem('cart'));
        this.cart.totalBooksInCart = this.cart.totalBooksInCart - cartBook.bookQuantity;
        if ((this.cart.totalBooksInCart + this.quantity) < 6) {
          this.cart.cartBooks.forEach(element => {
            if (element.book.bookId === cartBook.book.bookId) {
              if (Number(cartBook.book.quantity) > this.quantity) {
                element.bookQuantity = this.quantity;
                element.totalBookPrice = element.book.price * this.quantity;
                this.cart.totalBooksInCart = this.cart.totalBooksInCart + this.quantity;
              } else {
                this.snackBar.open('Book Out Of Stock', 'ok', {
                  duration: 2000
                });
              }
            }
          });
          localStorage.setItem('cart', JSON.stringify(this.cart));
          localStorage.setItem('cartSize', String(this.cart.totalBooksInCart));
          this.messageService.cartBooks();
          this.messageService.onCartRefresh();
          this.snackBar.open('Quantity Updated SuccessFully', 'ok', {
            duration: 2000,
          });
        } else {
          this.messageService.cartBooks();
          this.messageService.onCartRefresh();
          this.snackBar.open('Cart Books Exceeded limit of 5 Books', 'ok', {
            duration: 2000
          });
        }
      }
    }
    if (localStorage.getItem('token') !== null) {
      this.messageService.onUpdateQuantity(event, cartBook.cartBookId);
    }
  }

  onUpdateQuantity(data) {
    if (data.status === 200) {
      this.cartSize = data.totalBooksInCart;
      this.messageService.cartBooks();
      this.messageService.onCartCount();
      this.snackBar.open(data.message, 'ok', {
        duration: 2000
      });
    } else if (data.status === 400) {
      this.messageService.cartBooks();
      this.snackBar.open(data.error.message, 'cancel', {
        duration: 2000
      });
    }
  }

  checkAddressExistornot() {
    console.log("checking address exist or not");
    this.userService.getAddress('home').subscribe((result: any) => {
      if (result.status == 200) {
        console.log("Entered to get home address");
        console.log("home address:", result)
        this.addAddress(result.data);
      }
    },
      (error => {
        console.log("Entered to get work address");
        this.userService.getAddress('work').subscribe((response: any) => {
          if (response.status == 200) {
            console.log("office Address:", response);
            this.addAddress(response.data);
          }
        },
          (error => {
            this.snackBar.open('Please fill your address', 'ok', { duration: 5000 });
          }));
      }));
  }
  addAddress(addr) {
    console.log("address need to add in address:", addr);
    this.addressGroup.get('fullName').setValue(addr.fullName);
    this.addressGroup.get('phoneNumber').setValue(addr.phoneNumber);
    this.addressGroup.get('pinCode').setValue(addr.pinCode);
    this.addressGroup.get('locality').setValue(addr.locality);
    this.addressGroup.get('address').setValue(addr.address);
    this.addressGroup.get('city').setValue(addr.city);
    this.addressGroup.get('landMark').setValue(addr.landMark);
    this.addressGroup.get('locationType').setValue(addr.locationType);
    this.addressGroup.disable();
  }
  onedit() {
    console.log("to enable fiedls");
    this.addressGroup.enable();
  }
  selectAddrType(event: any) {
    this.addressGroup.reset();
    this.addressGroup.get('locationType').setValue(event.value);
    this.userService.getAddress(event.value).subscribe((result: any) => {
      if (result.status == 200)
        this.addAddress(result.data);
    },
      (error: any) => {
        this.snackBar.open(error.error.message, 'ok', { duration: 3000 });
      });
  }
}
