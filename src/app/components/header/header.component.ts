import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/services/auth.service';
import { MessageService } from 'src/services/message.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
//implements  OnInit, OnDestroy
export class HeaderComponent implements OnInit, OnDestroy {
  number: any;
  data: any;
  isCart: boolean;
  subscription: Subscription;
  cartCounter: number;

  constructor(public authService: AuthService, private messageService: MessageService) {
    this.subscription = this.messageService.getNumber().subscribe(number => { this.number = number });
  }
  ngOnInit(): void {
    this.messageService.onCartCount();
    if (localStorage.getItem('cartSize') !== null && localStorage.getItem('token') === null) {
      this.cartCounter = Number(localStorage.getItem('cartSize'));
    } else if (localStorage.getItem('token') !== null) {
      this.messageService.cartCountMessage.subscribe((data: any) => {
        this.cartCount(data);
      });
    }
    this.messageService.onGetAllBooks();
    this.messageService.cartBooks();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  onKey(event: any) {
    this.messageService.searchBook(event);
    this.isCart = false;
  }

  cartCount(data) {
    if (data.status === 200) {
      this.cartCounter = data.data;
    }
  }
}
