import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from 'src/services/user.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit {

  forgotPasswordForm: any;

  constructor(private userService: UserService, private snackBar: MatSnackBar,private formBuilder: FormBuilder) { 
    this.forgotPasswordForm=this.formBuilder.group({
      emailId:['',Validators.required],
    });
  }

  ngOnInit(): void {
  }

  onSubmit() {
    console.log(this.forgotPasswordForm.value);
    this.userService.forgotPassword(this.forgotPasswordForm.value).subscribe(
      (response: any) => {
        console.log(response);
        if (response.status === 200) {
          console.log('Reset link send to your mail id,please your check mail');
          this.snackBar.open(response.message, 'ok', { duration: 5000 });
          console.log(response.object);
        }
        else{
          this.snackBar.open(response.message, 'ok', { duration: 5000 });
        }
      }, (error: any) => {
        console.log(error);
        if (error.status === 400) {
          this.snackBar.open(error.error.error, 'ok', { duration: 2000 });
        }
      });
  }

}
