import { Injectable,Injector } from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {UserService} from './user.service';
import { Observable } from 'rxjs';

@Injectable()
export class TokenInterceptorService implements HttpInterceptor {

  constructor(private injector:Injector) { }

  intercept(req: HttpRequest<any>, next: HttpHandler):Observable<HttpEvent<any>>{
    let userService=this.injector.get(UserService)
    let tokenizedRequest=req.clone({
      setHeaders:{
        Authorization:`Bearer ${userService.getToken()}`
      }
    })
    return next.handle(tokenizedRequest);
  }
}
