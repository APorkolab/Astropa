import { Component } from '@angular/core';
import { ZodiacService } from '../zodiac.service';

@Component({
  selector: 'app-zodiac-calculator',
  templateUrl: './zodiac-calculator.component.html',
  styleUrls: ['./zodiac-calculator.component.scss']
})
export class ZodiacCalculatorComponent {
  birthdate: string = '';
  zodiac: any;

  constructor(private zodiacService: ZodiacService) { }

  getZodiac(): void {
    if (this.birthdate) {
      this.zodiacService.getZodiacByDate(this.birthdate)
        .subscribe(zodiacs => {
          this.zodiac = zodiacs[0];
        });
    }
  }
}
