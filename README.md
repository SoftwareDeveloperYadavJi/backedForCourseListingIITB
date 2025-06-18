# IIT Bombay Courses API

This project implements a RESTful API for managing courses and course delivery instances for the IIT Bombay Application Software Centre.

## Features

- Create, list, view, and delete courses 
- Manage course prerequisites with validation
- Create, list, view, and delete course delivery instances
- Comprehensive error handling
- API documentation with OpenAPI/Swagger

## Technologies Used

- Java 17
- Spring Boot 3.1
- Spring Data JPA
- H2 Database (for development)
- SpringDoc OpenAPI for API documentation
- Maven for dependency management
- Docker for containerization

## API Endpoints

### Course Management

- `POST /api/courses` - Create a new course
- `GET /api/courses` - List all courses with their prerequisites
- `GET /api/courses/{courseId}` - Get details of a specific course
- `DELETE /api/courses/{courseId}` - Delete a course (if not a prerequisite for other courses)

### Course Instance Management

- `POST /api/instances` - Create a new course delivery instance
- `GET /api/instances/{year}/{semester}` - List all courses delivered in specified year and semester
- `GET /api/instances/{year}/{semester}/{courseId}` - Get details of a specific course instance
- `DELETE /api/instances/{year}/{semester}/{courseId}` - Delete a specific course instance

## Getting Started

### Prerequisites

- JDK 17 or higher
- Maven 3.8 or higher
- Docker and Docker Compose (for containerized deployment)

### Running Locally

Clone the repository:

```bash
git clone https://github.com/SoftwareDeveloperYadavJi/backedForCourseListingIITB
cd courses-api
```

Build the application:

```bash
mvn clean package
```

Run the application:

```bash
java -jar target/courses-api-0.0.1-SNAPSHOT.jar
```

The API will be available at `http://localhost:8080` and the Swagger UI will be available at `http://localhost:8080/swagger-ui.html`.

## Docker Deployment

Build the Docker image:

```bash
docker build -t courses-api .
```

Run the container:

```bash
docker run -p 8080:8080 courses-api
```

### Using Docker Compose

```bash
docker-compose up
```

## API Documentation

The API is documented using OpenAPI/Swagger and can be accessed at `http://localhost:8080/swagger-ui.html` when running the application.

## Error Handling

The API uses HTTP status codes to indicate the outcome of requests:

- 200/201 - Success
- 400 - Bad Request (e.g., invalid input, missing field)
- 404 - Not Found (e.g., course or course instance not found)
- 409 - Conflict (e.g., duplicate course ID, deletion not allowed due to dependencies)
- 500 - Server Error
