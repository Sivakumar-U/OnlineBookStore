import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators,FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { UserService } from 'src/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: any;
  passwordType:string='password';

  constructor(private formBuilder: FormBuilder,private router:Router,private userService:UserService,private snackBar:MatSnackBar) { 
    this.loginForm=this.formBuilder.group({
      emailId:['',Validators.required],
      password:['',Validators.required],
    });
  }

  ngOnInit(): void {
  }

  changePasswordType(){
    if(this.passwordType=='password')
      this.passwordType='text'
    else
      this.passwordType='password';
  }

  onLogin(){
    this.userService.login(this.loginForm.value).subscribe(response => {
      if(response.status==200){
      console.log("response is ", response);
      console.log("token is ", response.data);
      console.log("status is ", response.status);
      console.log("message is ", response.message);
      localStorage.setItem("token", response.data);
      this.snackBar.open('Welcome ', 'ok', { duration: 3000 });
      this.router.navigateByUrl(`cart`);
      }
    }, err => {
      this.snackBar.open('Not logged in successfully', 'ok', { duration: 3000 });
     // alert('Not logged in successfully');
      this.router.navigateByUrl(`login`);

    })
  }
}
