import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators,FormGroup } from '@angular/forms';
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

  constructor(private formBuilder: FormBuilder,private router:Router,private userService:UserService) { 
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
      alert('Welcome');
      this.router.navigateByUrl(`cart`);
      }
    }, err => {
      alert('Not logged in successfully');
      this.router.navigateByUrl(`login`);

    })
  }
}
