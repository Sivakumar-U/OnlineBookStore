import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
const helper=new JwtHelperService();

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router,private snackBar:MatSnackBar) { }

  isAuthorised(){
    try{
    if(String(localStorage.getItem('token'))== '' || String(localStorage.getItem('token'))== 'null' ){
      this.router.navigate(['login']);
      return false;
    }
    const isExpired=helper.isTokenExpired(String(localStorage.getItem('token')));
    if(isExpired){
      alert("Session Expired, Login Again");
      this.router.navigate(['login']);
      return false;
    }
    return true;
    }catch{
      this.router.navigate(['login']);
    }
  }

  logoutUser(){
    console.log("Logged out");
    localStorage.removeItem('token');
    localStorage.removeItem('cartSize');
    this.snackBar.open('Logged out successfully', 'ok', { duration: 3000 });
    this.router.navigate(['/home']);
  }

  loggedIn(){
    return !!localStorage.getItem('token');
  }
}

