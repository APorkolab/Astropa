import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ZodiacCalculatorComponent } from './zodiac-calculator/zodiac-calculator.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AuthGuard } from './_helpers/auth.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'zodiac', component: ZodiacCalculatorComponent, canActivate: [AuthGuard] },
  { path: '', redirectTo: 'zodiac', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
