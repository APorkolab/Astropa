import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ZodiacCalculatorComponent } from './zodiac-calculator/zodiac-calculator.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { authInterceptorProviders } from './_helpers/auth.interceptor';

@NgModule({ declarations: [
        AppComponent,
        ZodiacCalculatorComponent,
        LoginComponent,
        RegisterComponent
    ],
    bootstrap: [AppComponent], imports: [BrowserModule,
        AppRoutingModule,
        FormsModule], providers: [authInterceptorProviders, provideHttpClient(withInterceptorsFromDi())] })
export class AppModule { }
