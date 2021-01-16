import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { UserService } from 'src/services/user.service';
import { MessageService } from 'src/services/message.service';
import { CartServiceService } from 'src/services/cart.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: any;
  passwordType: string = 'password';

  constructor(private formBuilder: FormBuilder, private router: Router, private userService: UserService, private snackBar: MatSnackBar, private cartService: CartServiceService,
    private messageService: MessageService) {
    this.loginForm = this.formBuilder.group({
      emailId: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  ngOnInit(): void {
  }

  changePasswordType() {
    if (this.passwordType == 'password')
      this.passwordType = 'text'
    else
      this.passwordType = 'password';
  }

  onLogin() {
    this.userService.login(this.loginForm.value).subscribe(response => {
      if (response.status == 200) {
        console.log("response is ", response);
        console.log("token is ", response.data);
        console.log("status is ", response.status);
        console.log("message is ", response.message);
        localStorage.setItem("token", response.data);
        this.snackBar.open('Welcome', 'ok', { duration: 3000 });
        //this.router.navigate(['/cart']);
        //this.router.navigateByUrl(`cart`);
        //this.messageService.onRefresh();
        this.cartService.placeOrder(JSON.parse(localStorage.getItem('cart'))).subscribe((data: any) => {
          if (data.status === 200) {
            this.messageService.cartBooks();
            this.messageService.onCartCount();
            localStorage.removeItem('cart');
          }
        }, (error: any) => {
          if (error.status === 417) {
            this.messageService.cartBooks();
            localStorage.removeItem('cart');
            this.snackBar.open(error.error.message, 'ok', {
              duration: 2000
            });
          }
          this.snackBar.open(error.error.message, 'ok', {
            duration: 2000
          });
        });
      }

    }, err => {
      this.snackBar.open('Not logged in successfully', 'ok', { duration: 3000 });
      this.router.navigateByUrl(`login`);

    })
  }
}