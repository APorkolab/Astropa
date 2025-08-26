import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZodiacCalculatorComponent } from './zodiac-calculator.component';

describe('ZodiacCalculatorComponent', () => {
  let component: ZodiacCalculatorComponent;
  let fixture: ComponentFixture<ZodiacCalculatorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ZodiacCalculatorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ZodiacCalculatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
