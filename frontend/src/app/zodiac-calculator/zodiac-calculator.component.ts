import { Component } from '@angular/core';
import { ZodiacService } from '../zodiac.service';
import { Observable } from 'rxjs';

@Component({
    selector: 'app-zodiac-calculator',
    templateUrl: './zodiac-calculator.component.html',
    styleUrls: ['./zodiac-calculator.component.scss'],
    standalone: false
})
export class ZodiacCalculatorComponent {
  birthdate: string = '';
  zodiac: any;
  compatibility: any;
  luckyYears: any;
  careers: any;
  personalities: any;
  lifeStages: any;
  projects: any;
  goals: any;
  divinations: any;
  error: any;

  constructor(private zodiacService: ZodiacService) { }

  getZodiac(): void {
    if (this.birthdate) {
      this.error = null;
      this.zodiacService.getZodiacByDate(this.birthdate)
        .subscribe({
          next: (zodiacs) => {
            this.zodiac = zodiacs[0];
            this.getLifeStages();
            this.getProjects();
            this.getGoals();
            this.getDivinations();
          },
          error: (err) => {
            this.error = err.error;
            this.zodiac = null;
          }
        });
    }
  }

  private fetchZodiacData(observable: Observable<any>, property: keyof ZodiacCalculatorComponent) {
    if (this.zodiac) {
      observable.subscribe(data => {
        (this as any)[property] = data;
      });
    }
  }

  private fetchCompatibility(observable: Observable<any>, property: keyof ZodiacCalculatorComponent) {
    if (this.zodiac) {
      observable.subscribe(data => {
        (this as any)[property] = data[0];
      });
    }
  }

  getCompatibility(): void {
    this.fetchCompatibility(this.zodiacService.getCompatibility(this.zodiac.name, 'Rat'), 'compatibility');
  }

  getLuckyYears(): void {
    const currentYear = new Date().getFullYear();
    this.fetchZodiacData(this.zodiacService.getLuckyYears(this.zodiac.name, currentYear), 'luckyYears');
  }

  getCareers(): void {
    this.fetchZodiacData(this.zodiacService.getCareers(this.zodiac.name), 'careers');
  }

  getPersonalities(): void {
    this.fetchZodiacData(this.zodiacService.getPersonalities(this.zodiac.name), 'personalities');
  }

  getLifeStages(): void {
    this.fetchZodiacData(this.zodiacService.getLifeStages(this.zodiac.name), 'lifeStages');
  }

  getProjects(): void {
    this.fetchZodiacData(this.zodiacService.getProjects(this.zodiac.name), 'projects');
  }

  getGoals(): void {
    this.fetchZodiacData(this.zodiacService.getGoals(this.zodiac.name), 'goals');
  }

  getDivinations(): void {
    this.fetchZodiacData(this.zodiacService.getDivinations(this.zodiac.name), 'divinations');
  }
}
