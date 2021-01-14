import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit {

  forgotPasswordForm: any;

  constructor(private formBuilder: FormBuilder) { 
    this.forgotPasswordForm=this.formBuilder.group({
      emailId:['',Validators.required],
    });
  }

  ngOnInit(): void {
  }

  onSubmit(){
    console.log(this.forgotPasswordForm.value);
    alert('Check your mail for reset password link ');
  }

}
