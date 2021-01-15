import {HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from './http.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpService,private htt:HttpClient) { }

  public register(user: any): Observable<any> {
    console.log(user);
    return this.http.POST('bookstore/signup', user, '');
  }

  forgotPassword(email: string): Observable<any> {
    return this.http.POST('bookstore/forget/password', email, '');
  }

  public login(login: any): Observable<any> {
    return this.http.POST('bookstore/signin', login, '');
  }

  resetPassword(data: any, token: string): Observable<any> {
    return this.http.POST('bookstore/reset/password/' + token, data, '');
  
  }

  private orderCheckoutApi = 'orders/checkOut'

  onCheckOut(): Observable<any> {
    return this.http.POST(this.orderCheckoutApi, '', {
      headers: new HttpHeaders().set('token', localStorage.getItem('token'))
    });
  }
  // checkout(bookId,quantity)
  // {
  //   console.log("in user service for checkout",bookId,quantity);
  //   return this.http.POST('orders/checkout/'+bookId+'/'+quantity,'',{ params:new HttpParams().set('token',localStorage.getItem('token'))});
  // }
  checkout(bookSum) {
    // console.log("in user service for checkout",bookId,quantity);
    console.log('books', bookSum);
    return this.http.POST('orders/checkout/', { params: new HttpParams().set('books', bookSum) }, { params: new HttpParams().set('token', localStorage.getItem('token')) });
  }
  getmyOrders(): Observable<any> {
    return this.http.GET('orders/myorders', { headers: new HttpHeaders().set('token', localStorage.getItem('token')) });
  }
  Address(data) {
    console.log("address in user service:", data);
    return this.http.POST('address/addAddress', data, { headers: new HttpHeaders().set('token', localStorage.getItem('token')) });
  }
  getAddress(addresstype) {
    return this.http.GET('address/getAddressByType', { params: new HttpParams().set('addressType', addresstype), headers: new HttpHeaders().set('token', localStorage.getItem('token')) });
  }

}