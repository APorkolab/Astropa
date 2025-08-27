import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const AUTH_API = '/api/auth/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
} as const;

export interface LoginCredentials {
  username: string;
  password: string;
}

export interface RegisterUser {
  username: string;
  password: string;
  email?: string;
}

/** Igazítsd a backend válaszához, ha szükséges. */
export interface AuthResponse {
  accessToken?: string;
  refreshToken?: string;
  user?: {
    username: string;
    roles?: string[];
  };
  // Engedjük az ismeretlen mezőket is, ne dőljön el miatta a fordítás
  [key: string]: unknown;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(private readonly http: HttpClient) { }

  login(credentials: LoginCredentials): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(
      `${AUTH_API}signin`,
      {
        username: credentials.username,
        password: credentials.password
      },
      httpOptions
    );
  }

  register(user: RegisterUser): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(
      `${AUTH_API}signup`,
      {
        username: user.username,
        password: user.password,
        ...(user.email ? { email: user.email } : {})
      },
      httpOptions
    );
  }
}
