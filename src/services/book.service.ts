import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from './http.service';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private http: HttpService) { }

  public getAllbooks(): Observable<any> {
    return this.http.GET('book/getBooks', '');
  }
 
  public getNumberOfItems(): Observable<any> {
    return this.http.GET('book/count',"");
  }

  public sortbookByPriceDesc(): Observable<any> {
    return this.http.GET('book/sort/price/descending', '');
  }

  public sortbookByPriceAsc(): Observable<any> {
    return this.http.GET('book/sort/price/ascending', '');
  }

  public sortbookByNewArrival(): Observable<any> {
    return this.http.GET('book/sort/newArrivals', '');
  }
}