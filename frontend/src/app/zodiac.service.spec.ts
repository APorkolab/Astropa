import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ZodiacService } from './zodiac.service';

describe('ZodiacService', () => {
  let service: ZodiacService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ZodiacService]
    });
    service = TestBed.inject(ZodiacService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch zodiac by date', () => {
    const mockZodiac = { name: 'Rat', description: '...' };
    const testDate = '2020-01-01';

    service.getZodiacByDate(testDate).subscribe(zodiac => {
      expect(zodiac).toEqual([mockZodiac]);
    });

    const req = httpMock.expectOne(`/api/zodiacs/findByDate?date=${testDate}`);
    expect(req.request.method).toBe('GET');
    req.flush([mockZodiac]);
  });

  it('should fetch compatibility', () => {
    const mockCompatibility = { compatibilityType: 'Good', description: '...' };
    const zodiac1 = 'Rat';
    const zodiac2 = 'Ox';

    service.getCompatibility(zodiac1, zodiac2).subscribe(compatibility => {
      expect(compatibility).toEqual([mockCompatibility]);
    });

    const req = httpMock.expectOne(`/api/compatibilities/findByZodiacs?zodiac1Name=${zodiac1}&zodiac2Name=${zodiac2}`);
    expect(req.request.method).toBe('GET');
    req.flush([mockCompatibility]);
  });

  it('should fetch lucky years', () => {
    const mockLuckyYears = [{ year: 2020, isLucky: true, description: '...' }];
    const zodiacName = 'Rat';
    const year = 2020;

    service.getLuckyYears(zodiacName, year).subscribe(luckyYears => {
      expect(luckyYears).toEqual(mockLuckyYears);
    });

    const req = httpMock.expectOne(`/api/lucky-years/findByZodiacAndYear?zodiacName=${zodiacName}&year=${year}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockLuckyYears);
  });

  it('should fetch careers', () => {
    const mockCareers = [{ career: { name: 'Doctor', description: '...' } }];
    const zodiacName = 'Rat';

    service.getCareers(zodiacName).subscribe(careers => {
      expect(careers).toEqual(mockCareers);
    });

    const req = httpMock.expectOne(`/api/careers/findByZodiac?zodiacName=${zodiacName}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockCareers);
  });

  it('should fetch personalities', () => {
    const mockPersonalities = [{ personality: { name: 'Ambitious', description: '...' } }];
    const zodiacName = 'Rat';

    service.getPersonalities(zodiacName).subscribe(personalities => {
      expect(personalities).toEqual(mockPersonalities);
    });

    const req = httpMock.expectOne(`/api/personalities/findByZodiac?zodiacName=${zodiacName}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockPersonalities);
  });

  it('should fetch life stages', () => {
    const mockLifeStages = [{ name: 'Adulthood', description: '...' }];
    const zodiacName = 'Rat';

    service.getLifeStages(zodiacName).subscribe(lifeStages => {
      expect(lifeStages).toEqual(mockLifeStages);
    });

    const req = httpMock.expectOne(`/api/lifestages/${zodiacName}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockLifeStages);
  });

  it('should fetch projects', () => {
    const mockProjects = [{ name: 'Start a business', description: '...' }];
    const zodiacName = 'Rat';

    service.getProjects(zodiacName).subscribe(projects => {
      expect(projects).toEqual(mockProjects);
    });

    const req = httpMock.expectOne(`/api/projects/${zodiacName}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockProjects);
  });

  it('should fetch goals', () => {
    const mockGoals = [{ name: 'Achieve financial independence', description: '...' }];
    const zodiacName = 'Rat';

    service.getGoals(zodiacName).subscribe(goals => {
      expect(goals).toEqual(mockGoals);
    });

    const req = httpMock.expectOne(`/api/goals/${zodiacName}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockGoals);
  });

  it('should fetch divinations', () => {
    const mockDivinations = [{ name: 'Tarot Reading', description: '...' }];
    const zodiacName = 'Rat';

    service.getDivinations(zodiacName).subscribe(divinations => {
      expect(divinations).toEqual(mockDivinations);
    });

    const req = httpMock.expectOne(`/api/divinations/${zodiacName}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockDivinations);
  });
});
