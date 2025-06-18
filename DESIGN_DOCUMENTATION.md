# IIT Bombay Course Management System - Design Documentation

## Architecture Overview

The project follows a standard Spring Boot MVC architecture with clear separation of concerns:

- **Controllers**: Handle HTTP requests, validate input, and return HTTP responses
- **Services**: Contain business logic and coordinate operations 
- **Repositories**: Provide data access abstraction
- **Models/Entities**: Map to database tables
- **DTOs**: Transfer data between the API and the application

```
└── com.iitb.coursesapi
    ├── controller         # REST API endpoints
    ├── service            # Business logic
    ├── repository         # Data access layer
    ├── model              # Domain entities
    ├── dto                # Data transfer objects
    ├── exception          # Custom exceptions and handler
    ├── config             # Application configuration
    └── CoursesApiApplication.java
```

## Design Decisions & Justifications

### 1. Entity Design

#### Course Entity
- The course model follows a self-referential many-to-many relationship to handle prerequisites
- Prerequisites are implemented as a bidirectional relationship to enable:
  - Getting all prerequisites for a course
  - Preventing course deletion if it's a prerequisite for other courses
- Database IDs and business IDs are separated:
  - `id`: Auto-generated integer primary key for database efficiency
  - `courseId`: Business identifier (e.g., "CS 209") for API operations

#### Course Instance Entity
- Uses a composite key `CourseInstanceId` to uniquely identify instances by course + year + semester
- Implements a many-to-one relationship with Course to ensure referential integrity
- Composite key design ensures uniqueness and enables direct lookups by (year, semester, courseId)

### 2. API Design

- **REST Principles**: Follows standard HTTP methods (GET, POST, DELETE) with appropriate status codes
- **Resource Hierarchy**: 
  - `/api/courses` for course management
  - `/api/instances` for course delivery instances
- **Validation**:
  - Input validation using Bean Validation (JSR 380)
  - Business rule validation in services

### 3. Error Handling

- **Global Exception Handler**: Centralized exception handling for consistent error responses
- **Custom Exceptions**: Specific exceptions for different error scenarios
- **HTTP Status Codes**: Appropriate status codes for different error conditions:
  - 404: Resource not found
  - 409: Conflict (duplication or dependency issues)
  - 400: Bad request (validation errors)

### 4. Security Considerations

- Input validation to prevent injection attacks
- Error handlers that don't expose internal details
- Could be extended with Spring Security for authentication and authorization

### 5. Database Choice

- **H2 Database** for development/testing
  - In-memory for fast testing
  - Persistence option for local development
- Production deployment would use a persistent database (PostgreSQL, MySQL, etc.)

### 6. API Documentation

- **OpenAPI/Swagger**: Self-documenting API using SpringDoc
- **Annotations**: Descriptions and example values for clear documentation

### 7. Testing Strategy (Recommended Implementation)

- **Unit Tests**: For services and utility classes
- **Integration Tests**: For repository and API endpoints
- **End-to-End Tests**: Complete API flow testing

### 8. Containerization

- **Multi-stage Docker build** for optimized image size
- **Docker Compose** for container orchestration
- **GitHub Actions** workflow for CI/CD automation

## Trade-offs and Future Improvements

### Trade-offs

1. **Simplified Authentication**: Current implementation focuses on core functionality without authentication
2. **In-memory Database**: H2 is used for simplicity but isn't suitable for production
3. **Single Module Architecture**: Monolithic for simplicity, could be split into microservices later

### Potential Improvements

1. **Authentication & Authorization**: Implement Spring Security
2. **Pagination**: Add pagination for listing endpoints
3. **Caching**: Implement caching for frequently accessed data
4. **Advanced Search**: Add filtering and search functionality
5. **Audit Logging**: Track changes to courses and instances
6. **Event-driven Updates**: Implement events for cross-service communication
7. **Comprehensive Testing**: Add more unit and integration tests
