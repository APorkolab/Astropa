# 🌟 Astropa - Chinese Zodiac Information Platform

[![CI/CD Pipeline](https://github.com/yourusername/astropa-2/actions/workflows/ci-cd.yml/badge.svg)](https://github.com/yourusername/astropa-2/actions/workflows/ci-cd.yml)
[![codecov](https://codecov.io/gh/yourusername/astropa-2/branch/main/graph/badge.svg)](https://codecov.io/gh/yourusername/astropa-2)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=yourusername_astropa-2&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=yourusername_astropa-2)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A sophisticated, enterprise-grade full-stack web application that provides comprehensive Chinese zodiac information with modern architecture, security best practices, and production-ready infrastructure.

## 🏗️ Architecture Overview

Astropa follows a microservices-inspired architecture with clear separation of concerns:

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Angular SPA   │───▶│   Spring Boot    │───▶│   MariaDB/H2    │
│   (Frontend)    │    │   (Backend API)  │    │   (Database)    │
└─────────────────┘    └──────────────────┘    └─────────────────┘
         │                       │                       │
         ▼                       ▼                       ▼
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│     Nginx       │    │   JWT Security   │    │     Redis       │
│  (Reverse Proxy)│    │   Spring Sec.    │    │   (Caching)     │
└─────────────────┘    └──────────────────┘    └─────────────────┘
```

## 🚀 Features

### Core Features
- **🔮 Zodiac Calculator**: Determines Chinese zodiac sign based on birth date
- **📊 Compatibility Checker**: Analyzes relationships between different zodiac signs
- **🍀 Lucky/Unlucky Years**: Provides fortune predictions for each sign
- **💼 Career Recommendations**: Suggests suitable professions and career paths
- **🧠 Personality Insights**: Detailed personality trait analysis
- **🎯 Life Guidance**: Provides guidance for different life stages
- **📈 Project Suitability**: Recommends business and personal projects
- **🌟 Goals & Dreams**: Helps align aspirations with zodiac characteristics
- **🔮 Divination Techniques**: Matches zodiac signs with suitable divination practices

### Technical Features
- **🔐 JWT Authentication**: Secure token-based authentication system
- **📝 OpenAPI Documentation**: Comprehensive Swagger/OpenAPI 3.0 documentation
- **🔄 Database Migrations**: Flyway-based database version control
- **📊 Health Monitoring**: Spring Boot Actuator endpoints
- **🚀 Production-Ready**: Docker containers, CI/CD pipeline, monitoring
- **🛡️ Security-First**: Security headers, input validation, dependency scanning
- **📱 Responsive Design**: Mobile-first, accessible user interface

## 🛠️ Technology Stack

### Backend Stack
| Technology | Version | Purpose |
|-----------|---------|----------|
| **Java** | 21 | Core language with modern features |
| **Spring Boot** | 3.5.5 | Application framework |
| **Spring Security** | 6.x | Authentication & authorization |
| **Spring Data JPA** | 3.x | Data persistence layer |
| **JWT** | 0.11.5 | Token-based authentication |
| **MariaDB** | 10.11 | Production database |
| **H2 Database** | 2.x | Development & testing |
| **Flyway** | 9.12.0 | Database migrations |
| **SpringDoc OpenAPI** | 2.5.0 | API documentation |
| **Maven** | 3.9+ | Build automation |

### Frontend Stack
| Technology | Version | Purpose |
|-----------|---------|----------|
| **Angular** | 20 | SPA framework |
| **TypeScript** | 5.8 | Type-safe JavaScript |
| **RxJS** | 7.5 | Reactive programming |
| **Angular CLI** | 20 | Development tooling |
| **ESLint** | 8.57 | Code quality |
| **Karma/Jasmine** | Latest | Unit testing |
| **Protractor** | 7.0 | E2E testing |

### DevOps & Infrastructure
| Tool | Purpose |
|------|----------|
| **Docker** | Containerization |
| **GitHub Actions** | CI/CD pipeline |
| **Kubernetes** | Container orchestration |
| **Nginx** | Reverse proxy & static content |
| **Redis** | Caching layer |
| **Trivy** | Security scanning |
| **JaCoCo** | Code coverage |
| **SonarQube** | Code quality analysis |

## Getting Started

### Prerequisites

-   Java 21 or later
-   Maven 3.6 or later
-   Node.js 16 or later
-   Angular CLI 20 or later

### Backend Setup

1.  Navigate to the `backend` directory:
    ```bash
    cd backend
    ```
2.  Build the project:
    ```bash
    ./mvnw clean install
    ```
3.  Run the Spring Boot application:
    ```bash
    ./mvnw spring-boot:run
    ```
    The backend will be running on `http://localhost:8080`.

### Frontend Setup

1.  Navigate to the `frontend` directory:
    ```bash
    cd frontend
    ```
2.  Install the dependencies:
    ```bash
    npm install
    ```
3.  Run the Angular development server:
    ```bash
    ng serve
    ```
    The frontend will be running on `http://localhost:4200`. The application will automatically proxy requests to the backend.

## API Documentation

This project uses Swagger for automatic API documentation. Once the backend is running, you can access the Swagger UI at the following URL:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

The OpenAPI 3.0 specification is available in JSON format at:

[http://localhost:8080/api-docs](http://localhost:8080/api-docs)

## Security

This application uses JSON Web Tokens (JWT) for authentication. The backend is secured with Spring Security.

### Authentication Flow

1.  **Register:** New users can register via the `/api/auth/signup` endpoint.
2.  **Login:** Users can log in via the `/api/auth/signin` endpoint with their username and password. Upon successful authentication, the API returns a JWT.
3.  **Authenticated Requests:** The frontend application must include the received JWT in the `Authorization` header for all subsequent requests to protected API endpoints. The header should be in the format `Bearer {jwt_token}`.

### Public vs. Protected Endpoints

-   **Public Endpoints:**
    -   `/api/auth/**`: User registration and login.
    -   `/swagger-ui.html` and `/api-docs/**`: API documentation.
-   **Protected Endpoints:** All other API endpoints require authentication.

## API Endpoints

-   `GET /api/zodiacs/findByDate?date={YYYY-MM-DD}`: Get zodiac sign by date.
-   `GET /api/compatibilities/{zodiac1Name}/{zodiac2Name}`: Get compatibility between two zodiac signs.
-   `GET /api/lucky-years/{zodiacName}/{year}`: Get lucky/unlucky years for a zodiac sign.
-   `GET /api/careers/{zodiacName}`: Get career recommendations.
-   `GET /api/personalities/{zodiacName}`: Get personality traits.
-   `GET /api/lifestages/{zodiacName}`: Get life stage guidance.
-   `GET /api/projects/{zodiacName}`: Get project suitability.
-   `GET /api/goals/{zodiacName}`: Get guidance on goals and dreams.
-   `GET /api/divinations/{zodiacName}`: Get divination techniques.

## Deployment

This project includes a Continuous Integration (CI) pipeline using GitHub Actions, defined in the `.github/workflows/ci-cd.yml` file. The pipeline is triggered on every push and pull request to the `main` branch.

The CI pipeline consists of two jobs:
-   `build-backend`: Builds the Spring Boot application.
-   `build-frontend`: Builds the Angular application.
