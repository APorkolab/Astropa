import { Component, OnInit } from '@angular/core';
import { AuthService, AuthResponse, LoginCredentials } from '../auth.service';
import { TokenStorageService } from '../token-storage.service';

interface LoginForm {
  username: string;
  password: string;
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  standalone: false
})
export class LoginComponent implements OnInit {
  form: LoginForm = { username: '', password: '' };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];

  constructor(
    private readonly authService: AuthService,
    private readonly tokenStorage: TokenStorageService
  ) { }

  ngOnInit(): void {
    const token = this.tokenStorage.getToken?.();
    if (typeof token === 'string' && token.length > 0) {
      this.isLoggedIn = true;
      const user = this.tokenStorage.getUser?.();
      this.roles = Array.isArray(user?.roles) ? user.roles as string[] : [];
    }
  }

  onSubmit(): void {
    const credentials: LoginCredentials = {
      username: this.form.username ?? '',
      password: this.form.password ?? ''
    };

    this.authService.login(credentials).subscribe({
      next: (data: AuthResponse) => {
        // Token több néven is érkezhet; biztosítsuk a string típust mentés előtt
        const token =
          (typeof data.accessToken === 'string' && data.accessToken) ||
          (typeof (data as any).token === 'string' && (data as any).token) ||
          (typeof (data as any).access_token === 'string' && (data as any).access_token) ||
          '';

        if (token) {
          this.tokenStorage.saveToken(token);
        }

        this.tokenStorage.saveUser(data as any);

        this.isLoginFailed = false;
        this.isLoggedIn = true;

        const user = this.tokenStorage.getUser?.();
        this.roles = Array.isArray(user?.roles) ? user.roles as string[] : [];

        this.reloadPage();
      },
      error: (err: any) => {
        this.errorMessage = err?.error?.message ?? 'Bejelentkezési hiba.';
        this.isLoginFailed = true;
      }
    });
  }

  reloadPage(): void {
    window.location.reload();
  }
}
