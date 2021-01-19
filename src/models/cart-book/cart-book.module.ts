import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Book } from '../book.model';

@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ]
})
export class CartBookModule {
  cartBookId: number;
  orderQuantity: number;
  book: Book;
  totalBookPrice: number;
}
