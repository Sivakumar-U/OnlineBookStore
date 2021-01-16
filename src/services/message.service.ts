import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { CartServiceService } from './cart.service';
import { Router } from '@angular/router';
import { BookService } from './book.service';
import { Subject, Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class MessageService {
    count: number;
    private messageSource = new BehaviorSubject(Response);
    currentMessage = this.messageSource.asObservable();
    private userMessageSource = new BehaviorSubject(Response);
    currentUserMessage = this.userMessageSource.asObservable();
    private cartSource = new BehaviorSubject(Response);
    cartMessage = this.cartSource.asObservable();
    private quantitySource = new BehaviorSubject(Response);
    quantityMessage = this.quantitySource.asObservable();
    private cartCountSource = new BehaviorSubject(Response);
    cartCountMessage = this.cartCountSource.asObservable();
    private booksCountSource = new BehaviorSubject(Response);
    booksCountMessage = this.booksCountSource.asObservable();
    private subject = new Subject<any>();
    constructor(  
        private bookService: BookService,
        private cartService: CartServiceService,
        private route: Router
    ) { }

    cartBooks() {
        if (
            localStorage.getItem('token') === null &&
            localStorage.getItem('cart') != null
        ) {
            this.cartSource.next(JSON.parse(localStorage.getItem('cart')));
        } else {
            this.cartService.displayBooksInCart().subscribe((data: any) => {
                this.cartSource.next(data);
                this.count = data.data.totalBooksInCart;
            });
        }
    }
    onRefresh() {
        this.route.navigate(['/home']);
    }
    onCartRefresh() {
        this.route.navigate(['/home/cart']);
    }

    onUpdateQuantity(event, cartBookId) {
        this.cartService.updateQuantity(event.target.value, cartBookId).subscribe((data) => {
            this.quantitySource.next(data);
        }, (error: any) => {
            this.quantitySource.next(error);
        });
    }

    onCartCount() {
        this.cartService.cartCount().subscribe(data => {
            this.cartCountSource.next(data);
        }, (error: any) => {
            this.cartCountSource.next(error);
        });
    }
    // searchBook(event) {
    //     this.bookService.searchBooks(event.target.value).subscribe((data) => {
    //       this.messageSource.next(data);
    //     });
    //   }

    changeoptionMessage() {
        this.bookService.sortbookByPriceDesc().subscribe((data) => {
            this.userMessageSource.next(data);
          });
      }
    
      changeoptionMessage1() {
        this.bookService.sortbookByPriceAsc().subscribe((data) => {
          this.userMessageSource.next(data);
        });
      }

      changeoptionMessage2() {
        this.bookService.sortbookByNewArrival().subscribe((data) => {
          this.userMessageSource.next(data);
        });
      }

      onGetAllBooks() {
        this.bookService.getAllbooks().subscribe((data) => {
          this.userMessageSource.next(data);
        });
      }
      // for badge on cart icon
      sendNumber(number:number){
        this.subject.next({text:number});
      }

      getNumber():Observable<any>{
        return this.subject.asObservable();
      }
      // sendByPage(pageIndex) {
    //     this.bookService.findByPage(pageIndex).subscribe((data) => {
    //       this.userMessageSource.next(data);
    //     });
    //   }
}