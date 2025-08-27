import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { ZodiacCalculatorComponent } from './zodiac-calculator.component';
import { ZodiacService } from '../zodiac.service';
import { FormsModule } from '@angular/forms';

describe('ZodiacCalculatorComponent', () => {
  let component: ZodiacCalculatorComponent;
  let fixture: ComponentFixture<ZodiacCalculatorComponent>;
  let zodiacService: ZodiacService;

  const mockZodiac = { name: 'Rat', description: '...' };

  beforeEach(async () => {
    const zodiacServiceSpy = jasmine.createSpyObj('ZodiacService', [
      'getZodiacByDate',
      'getCompatibility',
      'getLuckyYears',
      'getCareers',
      'getPersonalities',
      'getLifeStages',
      'getProjects',
      'getGoals',
      'getDivinations'
    ]);

    zodiacServiceSpy.getZodiacByDate.and.returnValue(of([mockZodiac]));
    zodiacServiceSpy.getCompatibility.and.returnValue(of([{ compatibilityType: 'Good' }]));
    zodiacServiceSpy.getLuckyYears.and.returnValue(of([{ year: 2020, isLucky: true }]));
    zodiacServiceSpy.getCareers.and.returnValue(of([{ career: { name: 'Doctor' } }]));
    zodiacServiceSpy.getPersonalities.and.returnValue(of([{ personality: { name: 'Ambitious' } }]));
    zodiacServiceSpy.getLifeStages.and.returnValue(of([{ name: 'Adulthood' }]));
    zodiacServiceSpy.getProjects.and.returnValue(of([{ name: 'Start a business' }]));
    zodiacServiceSpy.getGoals.and.returnValue(of([{ name: 'Financial Independence' }]));
    zodiacServiceSpy.getDivinations.and.returnValue(of([{ name: 'Tarot Reading' }]));


    await TestBed.configureTestingModule({
      declarations: [ ZodiacCalculatorComponent ],
      imports: [ HttpClientTestingModule, FormsModule ],
      providers: [
        { provide: ZodiacService, useValue: zodiacServiceSpy }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ZodiacCalculatorComponent);
    component = fixture.componentInstance;
    zodiacService = TestBed.inject(ZodiacService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get zodiac and call other methods on getZodiac()', () => {
    component.birthdate = '2020-01-01';
    component.getZodiac();
    expect(zodiacService.getZodiacByDate).toHaveBeenCalledWith('2020-01-01');
    expect(component.zodiac).toEqual(mockZodiac);
    expect(zodiacService.getLifeStages).toHaveBeenCalled();
    expect(zodiacService.getProjects).toHaveBeenCalled();
    expect(zodiacService.getGoals).toHaveBeenCalled();
    expect(zodiacService.getDivinations).toHaveBeenCalled();
  });

  it('should get compatibility', () => {
    component.zodiac = mockZodiac;
    component.getCompatibility();
    expect(zodiacService.getCompatibility).toHaveBeenCalledWith('Rat', 'Rat');
    expect(component.compatibility).toEqual({ compatibilityType: 'Good' });
  });

  it('should get lucky years', () => {
    component.zodiac = mockZodiac;
    component.getLuckyYears();
    expect(zodiacService.getLuckyYears).toHaveBeenCalled();
    expect(component.luckyYears).toEqual([{ year: 2020, isLucky: true }]);
  });

  it('should get careers', () => {
    component.zodiac = mockZodiac;
    component.getCareers();
    expect(zodiacService.getCareers).toHaveBeenCalledWith('Rat');
    expect(component.careers).toEqual([{ career: { name: 'Doctor' } }]);
  });

  it('should get personalities', () => {
    component.zodiac = mockZodiac;
    component.getPersonalities();
    expect(zodiacService.getPersonalities).toHaveBeenCalledWith('Rat');
    expect(component.personalities).toEqual([{ personality: { name: 'Ambitious' } }]);
  });

  it('should get life stages', () => {
    component.zodiac = mockZodiac;
    component.getLifeStages();
    expect(zodiacService.getLifeStages).toHaveBeenCalledWith('Rat');
    expect(component.lifeStages).toEqual([{ name: 'Adulthood' }]);
  });

  it('should get projects', () => {
    component.zodiac = mock.zodiac;
    component.getProjects();
    expect(zodiacService.getProjects).toHaveBeenCalledWith('Rat');
    expect(component.projects).toEqual([{ name: 'Start a business' }]);
  });

  it('should get goals', () => {
    component.zodiac = mockZodiac;
    component.getGoals();
    expect(zodiacService.getGoals).toHaveBeenCalledWith('Rat');
    expect(component.goals).toEqual([{ name: 'Financial Independence' }]);
  });

  it('should get divinations', () => {
    component.zodiac = mockZodiac;
    component.getDivinations();
    expect(zodiacService.getDivinations).toHaveBeenCalledWith('Rat');
    expect(component.divinations).toEqual([{ name: 'Tarot Reading' }]);
  });
});
