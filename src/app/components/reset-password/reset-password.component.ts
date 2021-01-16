import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/services/user.service';
import { MustMatch } from './must-match.validator';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {

  resetPasswordForm: any;
  passwordType:string='password';
  private token: string;

  constructor(private userService:UserService,private formBuilder: FormBuilder,private router:Router,
    private snackBar: MatSnackBar,private route: ActivatedRoute) { 
    this.resetPasswordForm=this.formBuilder.group({
     newPassword:['',[Validators.required,Validators.pattern('^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,20}$')]],
     confirmPassword:['',[Validators.required,Validators.pattern('^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,20}$')]]
    }, {
        validator: MustMatch('newPassword', 'confirmPassword')
    });
  }

  ngOnInit(): void {
    this.token = this.route.snapshot.paramMap.get('token');
  }

  onReset(){
    this.userService.resetPassword(this.resetPasswordForm.value, this.token).subscribe(
      (response: any) => {
        console.log(response);
        if (response.status === 200) {
          this.snackBar.open(response.message, 'ok', { duration: 3000 });
          this.router.navigate(['login']);
        }
      },
      (error: any) => {
        this.snackBar.open(error.error, 'ok', { duration: 3000 });
      }
    );
  }

  changePasswordType(){
    if(this.passwordType=='password')
      this.passwordType='text'
    else
      this.passwordType='password';
  }

}
