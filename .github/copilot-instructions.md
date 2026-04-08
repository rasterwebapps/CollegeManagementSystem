# College Management System - GitHub Copilot Instructions

This document provides context and guidelines for GitHub Copilot when working on the College Management System (CMS) codebase.

## Project Overview

The College Management System is a full-stack web application for managing college operations including students, faculty, departments, courses, and lab resources. The system uses a modern tech stack with Angular on the frontend and Spring Boot on the backend.

## Technology Stack

### Frontend (Angular 21)
- **Framework**: Angular 21 with standalone components
- **UI Library**: Angular Material 21 with Material Design 3
- **Language**: TypeScript 5.9 with strict mode enabled
- **Styling**: SCSS
- **Build Tool**: Angular CLI with `@angular/build`
- **Testing**: Vitest for unit tests
- **Code Formatting**: Prettier (single quotes, 100 char width)
- **Authentication**: Keycloak JS SDK for OAuth2/OIDC

### Backend (Spring Boot 3.4)
- **Framework**: Spring Boot 3.4.5
- **Language**: Java 21 with virtual threads enabled
- **Build Tool**: Gradle (Kotlin DSL)
- **Database**: PostgreSQL 16 with Flyway migrations
- **ORM**: Spring Data JPA with Hibernate
- **Security**: Spring Security with OAuth2 Resource Server (JWT)
- **Authentication Provider**: Keycloak 26.0

### Infrastructure
- **Authentication**: Keycloak 26.0 (realm: `cms`)
- **Database**: PostgreSQL 16
- **Container Orchestration**: Docker Compose

## Project Structure

```
CollegeManagementSystem/
├── backend/                    # Spring Boot backend
│   ├── src/main/java/com/cms/
│   │   ├── config/            # Configuration classes (Security, etc.)
│   │   ├── controller/        # REST controllers
│   │   ├── service/           # Business logic services
│   │   ├── repository/        # JPA repositories
│   │   ├── model/             # JPA entities
│   │   │   └── enums/         # Enum types
│   │   ├── dto/               # Data Transfer Objects (Java records)
│   │   └── exception/         # Custom exceptions
│   └── src/main/resources/
│       ├── application.yml    # Application configuration
│       └── db/migration/      # Flyway SQL migrations
├── frontend/                   # Angular frontend
│   └── src/app/
│       ├── core/              # Core services, guards, interceptors
│       │   ├── auth/          # Authentication service
│       │   ├── guards/        # Route guards
│       │   └── interceptors/  # HTTP interceptors
│       ├── features/          # Feature modules (dashboard, etc.)
│       └── shared/            # Shared components
├── infrastructure/             # Infrastructure configs
│   └── keycloak/              # Keycloak realm configuration
└── docker-compose.yml          # Local development setup
```

## Coding Conventions

### Backend (Java/Spring Boot)

1. **DTOs**: Use Java records for all DTOs in `com.cms.dto`
   ```java
   public record StudentRequest(
       @NotBlank String firstName,
       @NotBlank String lastName,
       @Email String email
   ) {}
   ```

2. **Entities**: Place JPA entities in `com.cms.model` with proper annotations
   ```java
   @Entity
   @Table(name = "students")
   public class Student {
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long id;
       // ...
   }
   ```

3. **Repositories**: Use JpaRepository interfaces in `com.cms.repository`
   ```java
   public interface StudentRepository extends JpaRepository<Student, Long> {
       Optional<Student> findByEmail(String email);
   }
   ```

4. **Controllers**: Follow REST conventions with `/api/v1` prefix
   ```java
   @RestController
   @RequestMapping("/api/v1/students")
   public class StudentController {
       // Use constructor injection
   }
   ```

5. **Security**: Use method-level security with `@PreAuthorize`
   ```java
   @PreAuthorize("hasRole('ROLE_ADMIN')")
   @PostMapping
   public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest request) {
       // ...
   }
   ```

6. **Database Migrations**: Create Flyway migrations in `src/main/resources/db/migration`
   - Naming: `V{version}__{description}.sql` (e.g., `V1__create_students_table.sql`)

### Frontend (Angular/TypeScript)

1. **Components**: Use standalone components with explicit imports
   ```typescript
   @Component({
     selector: 'app-student-list',
     standalone: true,
     imports: [MatTable, MatPaginator, ...],
     templateUrl: './student-list.html',
     styleUrl: './student-list.scss',
   })
   export class StudentListComponent {
     // Use inject() function over constructor injection
     private readonly studentService = inject(StudentService);
   }
   ```

2. **Services**: Use signals for reactive state management
   ```typescript
   @Injectable({ providedIn: 'root' })
   export class StudentService {
     private readonly _students = signal<Student[]>([]);
     readonly students = this._students.asReadonly();
   }
   ```

3. **HTTP Calls**: Use HttpClient with typed responses
   ```typescript
   getStudents(): Observable<Student[]> {
     return this.http.get<Student[]>(`${environment.apiUrl}/students`);
   }
   ```

4. **Templates**: Use separate `.html` template files (not inline templates)

5. **Styling**: Use SCSS with component-scoped styles

6. **Forms**: Use reactive forms with validators
   ```typescript
   this.form = this.fb.group({
     firstName: ['', [Validators.required]],
     email: ['', [Validators.required, Validators.email]],
   });
   ```

## Authentication & Authorization

### User Roles
- `ROLE_ADMIN` - Full system access
- `ROLE_FACULTY` - Faculty-specific operations
- `ROLE_STUDENT` - Student-specific access
- `ROLE_LAB_INCHARGE` - Lab management access
- `ROLE_TECHNICIAN` - Technical support access

### Frontend Authentication
- Keycloak JS handles authentication flow
- `AuthService` provides user profile and role information
- `authInterceptor` attaches JWT tokens to API requests
- Route guards protect authenticated routes

### Backend Security
- JWT tokens validated against Keycloak issuer
- Roles extracted from `realm_access.roles` claim
- Method-level security with `@PreAuthorize`

## Build & Test Commands

### Backend
```bash
cd backend

# Build
./gradlew compileJava

# Run tests
./gradlew test

# Run application
./gradlew bootRun
```

### Frontend
```bash
cd frontend

# Install dependencies
npm install

# Build
npm run build
# or: npx ng build

# Run tests
npm run test

# Development server
npm run start
```

### Docker Compose (Local Development)
```bash
# Start all services (PostgreSQL, Keycloak)
docker compose up -d

# Stop services
docker compose down
```

## API Conventions

- Base path: `/api/v1`
- Use standard HTTP methods: GET, POST, PUT, DELETE
- Return appropriate HTTP status codes
- Use pagination for list endpoints
- Validate request bodies with Bean Validation (`@Valid`)

## Best Practices

1. **Prefer composition over inheritance**
2. **Use constructor injection for dependencies (backend)** and `inject()` function (frontend)
3. **Write unit tests for services and controllers**
4. **Keep controllers thin** - business logic belongs in services
5. **Use meaningful variable and method names**
6. **Document public APIs with Javadoc (backend)** and JSDoc (frontend)
7. **Handle errors gracefully** with appropriate error responses
8. **Follow existing patterns** in the codebase when adding new features
