import { Injectable } from '@angular/core';
import { HttpService } from './http.service';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class CartServiceService {
    private addToCartApi = 'cart/add/';
    private removeFromCartApi = 'cart/remove/';
    private displayItemsApi = 'cart/get';
    private addQuantityApi = 'cart/addQuantity/';
    private removeQuantityApi = 'cart/removeQuantity/';
    private placeOrderApi = 'cart/placeOrder/';
    private updateQuantityApi = 'cart/update/';
    private cartCountApi = 'cart/getCount';
    constructor(private http: HttpService) { }

    addToCart(bookId: any): Observable<any> {
        return this.http.POST(this.addToCartApi + bookId, '', {
            headers: new HttpHeaders().set('token', localStorage.getItem('token'))
        });
    }

    displayBooksInCart(): Observable<any> {
        return this.http.GET(this.displayItemsApi, {
            headers: new HttpHeaders().set('token', localStorage.getItem('token'))
        });
    }

    removeFromCart(cartBookId: any): Observable<any> {
        return this.http.DELETE(this.removeFromCartApi + cartBookId, '', {
            headers: new HttpHeaders().set('token', localStorage.getItem('token'))
        });
    }

    addQuantity(cartBookId: any): Observable<any> {
        return this.http.PUT(this.addQuantityApi + cartBookId, '', {
            headers: new HttpHeaders().set('token', localStorage.getItem('token'))
        });
    }

    removeQuantity(cartBookId: any): Observable<any> {
        return this.http.PUT(this.removeQuantityApi + cartBookId, '', {
            headers: new HttpHeaders().set('token', localStorage.getItem('token'))
        });
    }

    placeOrder(cart: any): Observable<any> {
        return this.http.POST(this.placeOrderApi, cart, {
            headers: new HttpHeaders().set('token', localStorage.getItem('token'))
        });
    }

    addToOrder(): Observable<any> {
        return this.http.POST('orders/addMyOrder', '', {
            headers: new HttpHeaders().set('token', localStorage.getItem('token'))
        });
    }

    updateQuantity(quantity: any, cartBookId): Observable<any> {
        return this.http.PUT(this.updateQuantityApi + cartBookId + '/' + quantity, '', {
            headers: new HttpHeaders().set('token', localStorage.getItem('token'))
        });
    }

    cartCount(): Observable<any> {
        return this.http.GET(this.cartCountApi, {
            headers: new HttpHeaders().set('token', localStorage.getItem('token'))
        });
    }
}
