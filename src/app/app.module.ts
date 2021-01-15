import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatToolbarModule } from '@angular/material/toolbar';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { CartComponent } from './components/cart/cart.component';
import { WishlistComponent } from './components/wishlist/wishlist.component';
import { MatIconModule } from '@angular/material/icon';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatSelectModule } from '@angular/material/select';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatCardModule } from '@angular/material/card';
import {MatInputModule} from '@angular/material/input';
import { GridComponent } from './components/grid/grid.component';
import { PaginationComponent } from './components/pagination/pagination.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import {MatDialogModule} from '@angular/material/dialog';
import {MatRadioModule} from '@angular/material/radio';
import { CartServiceService } from 'src/services/cart.service';
import { RegisterComponent } from './components/register/register.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { ReactiveFormsModule,FormsModule } from '@angular/forms';
import { HttpClientModule} from '@angular/common/http';
import { UserService } from '../services/user.service';
import { AuthGuard } from 'src/services/auth.guard';
import {JwtModule} from '@auth0/angular-jwt';
import { SuccessPageComponent } from './components/success-page/success-page.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    LoginComponent,
    CartComponent,
    WishlistComponent,
    GridComponent,
    PaginationComponent,
    RegisterComponent,
    ResetPasswordComponent,
    ForgotPasswordComponent,
    SuccessPageComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatIconModule,
    MatGridListModule,
    MatSelectModule,
    MatPaginatorModule,
    MatCardModule,
    MatSnackBarModule,
    MatInputModule,
    MatDialogModule,
    MatRadioModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    JwtModule
  ],
  providers: [CartServiceService,UserService,AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
