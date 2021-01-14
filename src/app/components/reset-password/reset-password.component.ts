import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MustMatch } from './must-match.validator';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {

  resetPasswordForm: any;
  passwordType:string='password';

  constructor(private formBuilder: FormBuilder,private router:Router) { 
    this.resetPasswordForm=this.formBuilder.group({
     newPassword:['',[Validators.required,Validators.pattern('^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,20}$')]],
     confirmPassword:['',[Validators.required,Validators.pattern('^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,20}$')]]
    }, {
        validator: MustMatch('newPassword', 'confirmPassword')
    });
  }

  ngOnInit(): void {
  }

  onReset(){
    console.log(this.resetPasswordForm.value);
    alert('Your password reseted successfully ');
    this.router.navigateByUrl(`login`);
  }

  changePasswordType(){
    if(this.passwordType=='password')
      this.passwordType='text'
    else
      this.passwordType='password';
  }

}
