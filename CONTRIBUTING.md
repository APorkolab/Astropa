# Contributing to Astropa

We welcome contributions to Astropa! This document provides guidelines for contributing to this project.

## Table of Contents
- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Workflow](#development-workflow)
- [Coding Standards](#coding-standards)
- [Testing Requirements](#testing-requirements)
- [Pull Request Process](#pull-request-process)
- [Release Process](#release-process)

## Code of Conduct

This project and everyone participating in it is governed by our Code of Conduct. By participating, you are expected to uphold this code.

## Getting Started

### Prerequisites
- Java 21+
- Node.js 20+
- Docker & Docker Compose
- Maven 3.9+
- Git

### Development Environment Setup

1. **Fork and Clone**
   ```bash
   git clone https://github.com/yourusername/astropa-2.git
   cd astropa-2
   ```

2. **Environment Configuration**
   ```bash
   cp .env.example .env
   # Edit .env with your local configuration
   ```

3. **Start Development Environment**
   ```bash
   docker-compose up -d
   ```

4. **Verify Setup**
   ```bash
   # Backend health check
   curl http://localhost:8080/actuator/health
   
   # Frontend accessibility
   curl http://localhost:4200
   ```

## Development Workflow

### Branch Strategy

We follow **Git Flow** with the following branches:
- `main`: Production-ready code
- `develop`: Integration branch for features
- `feature/*`: Feature development branches
- `hotfix/*`: Critical fixes for production
- `release/*`: Release preparation branches

### Feature Development

1. **Create Feature Branch**
   ```bash
   git checkout develop
   git pull origin develop
   git checkout -b feature/ISSUE-123-amazing-feature
   ```

2. **Development**
   - Write code following our [coding standards](#coding-standards)
   - Add/update tests
   - Update documentation
   - Commit frequently with meaningful messages

3. **Commit Message Format**
   ```
   type(scope): description
   
   body (optional)
   
   footer (optional)
   ```
   
   **Types:** `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`
   
   **Examples:**
   ```
   feat(zodiac): add compatibility calculation endpoint
   fix(auth): resolve JWT token expiration issue
   docs(api): update swagger documentation for new endpoints
   ```

## Coding Standards

### Backend (Java/Spring Boot)

#### Code Style
- Follow **Google Java Style Guide**
- Use **4 spaces** for indentation
- Maximum line length: **120 characters**
- Use **meaningful variable and method names**

#### Best Practices
```java
// ✅ Good
@Service
@Slf4j
@RequiredArgsConstructor
public class ZodiacService {
    
    private final ZodiacRepository zodiacRepository;
    
    @Cacheable("zodiac-by-date")
    public ZodiacDto findByDate(LocalDate date) {
        log.debug("Finding zodiac for date: {}", date);
        
        return zodiacRepository.findByDateRange(date)
            .map(ZodiacMapper::toDto)
            .orElseThrow(() -> new ZodiacNotFoundException(date));
    }
}

// ❌ Bad
@Service
public class ZodiacService {
    @Autowired
    private ZodiacRepository repo;
    
    public ZodiacDto find(LocalDate d) {
        return repo.findByDateRange(d).map(ZodiacMapper::toDto).orElseThrow(() -> new ZodiacNotFoundException(d));
    }
}
```

#### Architecture Patterns
- **Controller** → **Service** → **Repository** layering
- Use **DTOs** for API contracts
- Implement **proper exception handling**
- Follow **SOLID principles**

### Frontend (Angular/TypeScript)

#### Code Style
- Follow **Angular Style Guide**
- Use **2 spaces** for indentation
- Maximum line length: **120 characters**
- Use **PascalCase** for classes, **camelCase** for methods/properties

#### Component Structure
```typescript
// ✅ Good
@Component({
  selector: 'app-zodiac-calculator',
  templateUrl: './zodiac-calculator.component.html',
  styleUrls: ['./zodiac-calculator.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ZodiacCalculatorComponent implements OnInit, OnDestroy {
  private readonly destroy$ = new Subject<void>();
  
  readonly form = this.fb.nonNullable.group({
    birthDate: ['', [Validators.required]]
  });
  
  constructor(
    private readonly fb: FormBuilder,
    private readonly zodiacService: ZodiacService,
    private readonly cdr: ChangeDetectorRef
  ) {}
  
  ngOnInit(): void {
    this.form.valueChanges
      .pipe(
        debounceTime(300),
        takeUntil(this.destroy$)
      )
      .subscribe(value => this.calculateZodiac(value));
  }
  
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
  
  private calculateZodiac(formValue: FormValue): void {
    // Implementation
  }
}
```

#### Best Practices
- Use **OnPush change detection**
- Implement **proper unsubscription**
- Use **reactive forms**
- Follow **smart/dumb component pattern**
- Use **TypeScript strict mode**

## Testing Requirements

### Minimum Coverage
- **Backend:** 80% line coverage
- **Frontend:** 75% line coverage

### Backend Testing

#### Unit Tests
```java
@ExtendWith(MockitoExtension.class)
class ZodiacServiceTest {
    
    @Mock
    private ZodiacRepository zodiacRepository;
    
    @InjectMocks
    private ZodiacService zodiacService;
    
    @Test
    @DisplayName("Should return zodiac when date is valid")
    void shouldReturnZodiacWhenDateIsValid() {
        // Given
        LocalDate date = LocalDate.of(1990, 1, 15);
        Zodiac zodiac = ZodiacTestData.createHorse();
        when(zodiacRepository.findByDateRange(date)).thenReturn(Optional.of(zodiac));
        
        // When
        ZodiacDto result = zodiacService.findByDate(date);
        
        // Then
        assertThat(result)
            .hasFieldOrPropertyWithValue("name", "Horse")
            .hasFieldOrPropertyWithValue("element", "Metal");
    }
}
```

#### Integration Tests
```java
@SpringBootTest
@Testcontainers
@Sql("/test-data.sql")
class ZodiacControllerIT {
    
    @Container
    static MariaDBContainer<?> mariadb = new MariaDBContainer<>("mariadb:10.11");
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void shouldReturnZodiacForValidDate() {
        // Given
        String date = "1990-01-15";
        
        // When
        ResponseEntity<ZodiacDto> response = restTemplate.getForEntity(
            "/api/zodiacs/findByDate?date=" + date, 
            ZodiacDto.class
        );
        
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("Horse");
    }
}
```

### Frontend Testing

#### Unit Tests
```typescript
describe('ZodiacCalculatorComponent', () => {
  let component: ZodiacCalculatorComponent;
  let fixture: ComponentFixture<ZodiacCalculatorComponent>;
  let zodiacService: jasmine.SpyObj<ZodiacService>;

  beforeEach(() => {
    const spy = jasmine.createSpyObj('ZodiacService', ['calculateZodiac']);

    TestBed.configureTestingModule({
      declarations: [ZodiacCalculatorComponent],
      providers: [
        { provide: ZodiacService, useValue: spy }
      ]
    });

    fixture = TestBed.createComponent(ZodiacCalculatorComponent);
    component = fixture.componentInstance;
    zodiacService = TestBed.inject(ZodiacService) as jasmine.SpyObj<ZodiacService>;
  });

  it('should calculate zodiac when valid date is entered', () => {
    // Given
    const expectedZodiac = { name: 'Dragon', element: 'Wood' };
    zodiacService.calculateZodiac.and.returnValue(of(expectedZodiac));

    // When
    component.form.patchValue({ birthDate: '1988-01-15' });

    // Then
    expect(zodiacService.calculateZodiac).toHaveBeenCalledWith('1988-01-15');
  });
});
```

#### E2E Tests
```typescript
describe('Zodiac Calculator', () => {
  it('should calculate zodiac sign from birth date', async () => {
    await page.goto('/zodiac-calculator');
    
    await page.fill('[data-testid="birth-date"]', '1990-01-15');
    await page.click('[data-testid="calculate-button"]');
    
    await expect(page.locator('[data-testid="zodiac-result"]'))
      .toContainText('Horse');
  });
});
```

## Pull Request Process

### Before Submitting

1. **Self-Review Checklist**
   - [ ] Code follows style guidelines
   - [ ] Tests are included and passing
   - [ ] Documentation is updated
   - [ ] No hardcoded values or secrets
   - [ ] Performance considerations addressed
   - [ ] Security implications reviewed

2. **Local Validation**
   ```bash
   # Backend
   cd backend
   mvn clean verify
   mvn spotbugs:check
   
   # Frontend
   cd frontend
   npm run lint
   npm run test:ci
   npm run build:prod
   ```

### Pull Request Template

When creating a PR, use this template:

```markdown
## Description
Brief description of changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Testing
- [ ] Unit tests pass
- [ ] Integration tests pass
- [ ] E2E tests pass
- [ ] Manual testing completed

## Checklist
- [ ] Code follows style guidelines
- [ ] Self-review completed
- [ ] Documentation updated
- [ ] Tests added/updated

## Screenshots (if applicable)

## Related Issues
Closes #123
```

### Review Process

1. **Automated Checks**
   - CI pipeline must pass
   - Code coverage requirements met
   - Security scans pass
   - Quality gates pass

2. **Human Review**
   - At least 2 approvals required
   - Focus on architecture, logic, security
   - Performance implications
   - Documentation completeness

3. **Merge Requirements**
   - All conversations resolved
   - Branch up to date with target
   - Squash commits for clean history

## Release Process

### Versioning
We follow **Semantic Versioning** (SemVer):
- `MAJOR.MINOR.PATCH`
- `MAJOR`: Breaking changes
- `MINOR`: New features (backward compatible)
- `PATCH`: Bug fixes (backward compatible)

### Release Checklist

1. **Pre-Release**
   ```bash
   git checkout develop
   git pull origin develop
   git checkout -b release/v1.2.0
   ```

2. **Version Updates**
   - Update version numbers in `pom.xml` and `package.json`
   - Update `CHANGELOG.md`
   - Update documentation

3. **Testing**
   - Run full test suite
   - Perform manual testing
   - Security scan
   - Performance testing

4. **Release**
   ```bash
   git checkout main
   git merge release/v1.2.0
   git tag v1.2.0
   git push origin main --tags
   ```

5. **Post-Release**
   ```bash
   git checkout develop
   git merge main
   git push origin develop
   ```

## Quality Gates

### Automated Quality Gates

| Metric | Threshold | Tool |
|--------|-----------|------|
| Code Coverage | 80% (Backend), 75% (Frontend) | JaCoCo, Karma |
| Security Issues | 0 High/Critical | Trivy, OWASP |
| Code Smells | Grade A | SonarQube |
| Duplication | < 3% | SonarQube |
| Technical Debt | < 30min | SonarQube |

### Manual Review Criteria

- [ ] **Architecture**: Follows established patterns
- [ ] **Security**: No security vulnerabilities
- [ ] **Performance**: No performance regressions
- [ ] **Maintainability**: Easy to understand and modify
- [ ] **Documentation**: Complete and accurate

## Getting Help

- **Documentation**: [Project Wiki](https://github.com/yourusername/astropa-2/wiki)
- **Issues**: [GitHub Issues](https://github.com/yourusername/astropa-2/issues)
- **Discussions**: [GitHub Discussions](https://github.com/yourusername/astropa-2/discussions)
- **Slack**: #astropa-dev channel

Thank you for contributing to Astropa! 🌟