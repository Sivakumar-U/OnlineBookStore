import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { CartServiceService } from './cart.service';
import { Router } from '@angular/router';

@Injectable({
    providedIn: 'root',
})
export class MessageService {
    count: number;
    private cartSource = new BehaviorSubject(Response);
    cartMessage = this.cartSource.asObservable();
    private quantitySource = new BehaviorSubject(Response);
    quantityMessage = this.quantitySource.asObservable();
    private cartCountSource = new BehaviorSubject(Response);
    cartCountMessage = this.cartCountSource.asObservable();

    constructor(
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
}
