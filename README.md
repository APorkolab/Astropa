# Chinese Zodiac Information App

A full-stack web application that provides users with detailed information about their Chinese zodiac sign. Users can enter their birth date to find out their zodiac sign and explore various aspects related to it, such as compatibility with other signs, lucky and unlucky years, suitable careers, personality traits, and more.

## Features

-   **Zodiac Calculator:** Determines a user's Chinese zodiac sign based on their birth date.
-   **Zodiac Data:** Provides information about zodiac signs for every year since 1900.
-   **Detailed Characteristics:** Displays detailed descriptions of each zodiac sign.
-   **Compatibility Checker:** Shows compatibility between different zodiac signs for personal and business relationships.
-   **Lucky/Unlucky Years:** Lists the lucky and unlucky years for each sign.
-   **Career Recommendations:** Suggests suitable careers and professions.
-   **Personality Insights:** Matches zodiac signs with specific personality traits.
-   **Life Stage Guidance:** Associates zodiac signs with different life stages.
-   **Project Suitability:** Recommends personal or business projects based on zodiac signs.
-   **Goals & Dreams:** Provides guidance on achieving goals and realizing dreams.
-   **Divination & Vedic Techniques:** Matches zodiac signs with suitable divination practices.
-   **Authentication:** Secure user registration and login using JWT.

## Tech Stack

-   **Backend:**
    -   Java 21
    -   Spring Boot 3
    -   Spring Security
    -   JWT (JSON Web Tokens)
    -   Maven
    -   JPA (Hibernate)
    -   H2 Database (for development)
    -   Flyway (for database migrations)
    -   SpringDoc OpenAPI (for Swagger API documentation)
-   **Frontend:**
    -   Angular 16
    -   TypeScript
    -   HTML/SCSS
    -   Node.js
    -   Angular CLI
-   **CI/CD:**
    -   GitHub Actions

## Getting Started

### Prerequisites

-   Java 21 or later
-   Maven 3.6 or later
-   Node.js 16 or later
-   Angular CLI 16 or later

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

**Note:** The test execution steps are currently disabled in the CI pipeline due to an environment issue.
