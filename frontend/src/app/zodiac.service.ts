import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ZodiacService {
  private apiUrl = '/api/zodiacs';

  constructor(private http: HttpClient) { }

  getZodiacByDate(date: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/findByDate`, { params: { date } });
  }
}
