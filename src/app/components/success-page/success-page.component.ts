import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-success-page',
  templateUrl: './success-page.component.html',
  styleUrls: ['./success-page.component.scss']
})
export class SuccessPageComponent implements OnInit {

  constructor() { }
  id = localStorage.getItem('orderId');
  orderId = parseInt(this.id, 4) * 7893;
  ngOnInit() {
  }
}
