import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
const helper=new JwtHelperService();

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router) { }

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
}

