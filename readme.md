# E-Commerce Store Application

A Spring Boot-based e-commerce application that demonstrates modern web development practices and patterns.

## Software Development Principles, Patterns and Practices

### Architecture Patterns
- **MVC Pattern**: Implements Model-View-Controller pattern using Spring MVC
- **Repository Pattern**: Uses Spring Data JPA repositories for data access
- **DTO Pattern**: Implements Data Transfer Objects for API responses
- **Builder Pattern**: Utilizes builder pattern for response construction

### Design Principles
- **SOLID Principles**: Follows Single Responsibility and Interface Segregation
- **DRY (Don't Repeat Yourself)**: Implements reusable components and layouts
- **Dependency Injection**: Uses Spring's @Autowired for dependency management
- **Separation of Concerns**: Clear separation between controllers, services, and repositories

### Best Practices
- **Exception Handling**: Global exception handling using @ControllerAdvice
- **Security**: Implements Spring Security for web security
- **Database Migration**: Uses Hibernate auto-ddl for database schema management
- **Responsive Design**: Bootstrap-based responsive UI

## Code Structure

## Technologies Used
- Java 17
- Spring Boot 3.4.4
- Spring Security
- Spring Data JPA
- Thymeleaf Template Engine
- H2 Database (Development)
- PostgreSQL (Production)
- Maven
- Bootstrap 5.3
- Docker

## Getting Started

### Prerequisites
- JDK 17 or later
- Maven 3.8+
- Docker (optional)

### Local Setup

1. Clone the repository:
```bash
git clone <repository-url>
cd midterm
```

2. Build the application:
```bash
mvn clean package
```

3. Run the application:
```bash
java -jar target/midterm-0.0.1-SNAPSHOT.jar
```

### Docker Setup

1. Build the Docker image:
```bash
docker build -t ecommerce-app .
```

2. Run with Docker Compose:
```bash
docker-compose up
```

The application will be available at `http://localhost:8080`

## API Endpoints

### Products API

1. Get All Products
```bash
curl -X GET http://localhost:8080/products
```

2. Get Product by ID
```bash
curl -X GET http://localhost:8080/products/{id}
```

3. Filter Products
```bash
curl -X GET "http://localhost:8080/products?category=1&minPrice=10&maxPrice=100&brand=NIKE&color=WHITE"
```

### Cart API

1. Add to Cart
```bash
curl -X POST http://localhost:8080/cart/add \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "productId=1&quantity=1"
```

2. Update Cart
```bash
curl -X POST http://localhost:8080/cart/update \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "productId=1&quantity=2"
```

3. Remove from Cart
```bash
curl -X POST http://localhost:8080/cart/remove \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "productId=1"
```

4. Clear Cart
```bash
curl -X POST http://localhost:8080/cart/clear
```

### Response Format
All API endpoints return responses in the following format:
```json
{
    "code": 200,
    "status": "OK",
    "message": "",
    "data": {}
}
```

## Database Configuration

### Development (H2)
```properties
spring.datasource.url=jdbc:h2:mem:midterm
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
```

### Production (PostgreSQL)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/spring
spring.datasource.username=postgres
spring.datasource.password=abc123
spring.datasource.driver-class-name=org.postgresql.Driver
```

## Security Configuration
The application uses Spring Security with basic configuration allowing all requests (for demonstration purposes). In production, implement proper authentication and authorization




