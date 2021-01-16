import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { UserService } from 'src/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  registerForm: any;
  passwordType:string='password';

  constructor(private formBuilder: FormBuilder,private router:Router,private userService:UserService,private snackBar:MatSnackBar) { 
    this.registerForm=this.formBuilder.group({
      fullName:['',[Validators.required,Validators.pattern('^[A-Z][a-z]+\\s?[A-Z][a-z]+$')]],
      emailId:['',[Validators.required,Validators.pattern('^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$')]],
      password:['',[Validators.required,Validators.pattern('^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,20}$')]]
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

  onRegister(){
    this.userService.register(this.registerForm.value).subscribe((response: any) => {
      if(response.status==200)
      {
        this.snackBar.open('Hello ' + this.registerForm.get('fullName').value+' ,you are successfully registered.', 'ok', { duration: 3000 });
        this.router.navigateByUrl(`login`);
      }
      else
      {
        this.snackBar.open('Hello ' + this.registerForm.get('fullName').value+' ,you are not successfully registered.', 'ok', { duration: 3000 });
      }
    });
  }

}
