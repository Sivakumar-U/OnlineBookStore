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
        localStorage.setItem("token", response.data);
          this.router.navigate(['home']);
      }
}, err => {
      this.snackBar.open('Not logged in successfully', 'ok', { duration: 3000 });
      this.router.navigateByUrl(`login`);

    })
  }
}