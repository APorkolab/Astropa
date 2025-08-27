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

  getCompatibility(zodiac1: string, zodiac2: string): Observable<any> {
    return this.http.get(`/api/compatibilities/${zodiac1}/${zodiac2}`);
  }

  getLuckyYears(zodiacName: string, year: number): Observable<any> {
    return this.http.get(`/api/lucky-years/${zodiacName}/${year}`);
  }

  getCareers(zodiacName: string): Observable<any> {
    return this.http.get(`/api/careers/${zodiacName}`);
  }

  getPersonalities(zodiacName: string): Observable<any> {
    return this.http.get(`/api/personalities/${zodiacName}`);
  }

  getLifeStages(zodiacName: string): Observable<any> {
    return this.http.get(`/api/lifestages/${zodiacName}`);
  }

  getProjects(zodiacName: string): Observable<any> {
    return this.http.get(`/api/projects/${zodiacName}`);
  }

  getGoals(zodiacName: string): Observable<any> {
    return this.http.get(`/api/goals/${zodiacName}`);
  }

  getDivinations(zodiacName: string): Observable<any> {
    return this.http.get(`/api/divinations/${zodiacName}`);
  }
}
