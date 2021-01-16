import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CartComponent } from './components/cart/cart.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { AuthGuard } from 'src/services/auth.guard';
import { SuccessPageComponent } from './components/success-page/success-page.component';


const routes: Routes = [
  { path: '', redirectTo:'/home',pathMatch:'full'},
  { path: 'home', component: HomeComponent  },
  { path: 'login',  component: LoginComponent },
  { path: 'register', component: RegisterComponent  },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'reset-password/:token', component: ResetPasswordComponent },
  { path: 'cart', component: CartComponent, canActivate:[AuthGuard] },
  { path: 'success-page', component: SuccessPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
