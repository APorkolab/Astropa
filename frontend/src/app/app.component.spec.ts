import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { TokenStorageService } from './token-storage.service';

describe('AppComponent', () => {
  beforeEach(async () => {
    const tokenStorageServiceSpy = jasmine.createSpyObj('TokenStorageService', [
      'getToken',
      'getUser',
      'signOut'
    ]);

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule
      ],
      declarations: [
        AppComponent
      ],
      providers: [
        { provide: TokenStorageService, useValue: tokenStorageServiceSpy }
      ]
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should have isLoggedIn property', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.isLoggedIn).toBeDefined();
  });

  it('should have username property', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    fixture.detectChanges(); // Initialize the component
    expect(app.username).toBeUndefined(); // Initially undefined when not logged in
  });
});
