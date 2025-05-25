# Ứng Dụng Cửa Hàng Thương Mại Điện Tử

Ứng dụng thương mại điện tử dựa trên Spring Boot thể hiện các phương pháp và mẫu thiết kế phát triển web hiện đại.

## Nguyên Tắc, Mẫu Thiết Kế và Thực Hành Phát Triển Phần Mềm

### Mẫu Kiến Trúc
- **Mẫu MVC**: Triển khai mẫu Model-View-Controller sử dụng Spring MVC
- **Mẫu Repository**: Sử dụng Spring Data JPA repositories để truy cập dữ liệu
- **Mẫu DTO**: Triển khai Data Transfer Objects cho phản hồi API
- **Mẫu Builder**: Sử dụng mẫu builder để xây dựng phản hồi

### Nguyên Tắc Thiết Kế
- **Nguyên Tắc SOLID**: Tuân thủ Single Responsibility và Interface Segregation
- **DRY (Don't Repeat Yourself)**: Triển khai các thành phần và bố cục có thể tái sử dụng
- **Dependency Injection**: Sử dụng @Autowired của Spring để quản lý phụ thuộc
- **Phân Tách Mối Quan Tâm**: Phân tách rõ ràng giữa controllers, services và repositories

### Thực Hành Tốt Nhất
- **Xử Lý Ngoại Lệ**: Xử lý ngoại lệ toàn cục sử dụng @ControllerAdvice
- **Bảo Mật**: Triển khai Spring Security cho bảo mật web
- **Di Chuyển Cơ Sở Dữ Liệu**: Sử dụng Hibernate auto-ddl để quản lý schema cơ sở dữ liệu
- **Thiết Kế Responsive**: Giao diện responsive dựa trên Bootstrap

## Cấu Trúc Mã Nguồn

## Công Nghệ Sử Dụng
- Java 17
- Spring Boot 3.4.4
- Spring Security
- Spring Data JPA
- Thymeleaf Template Engine
- H2 Database (Môi trường phát triển)
- PostgreSQL (Môi trường sản xuất)
- Maven
- Bootstrap 5.3
- Docker

## Bắt Đầu

### Yêu Cầu
- JDK 17 trở lên
- Maven 3.8+
- Docker (tùy chọn)

### Cài Đặt Cục Bộ

1. Clone repository:
```bash
git clone <repository-url>
cd midterm
```

2. Build ứng dụng:
```bash
mvn clean package
```

3. Chạy ứng dụng:
```bash
java -jar target/midterm-0.0.1-SNAPSHOT.jar
```

### Cài Đặt Docker

1. Build Docker image:
```bash
docker build -t ecommerce-app .
```

2. Chạy với Docker Compose:
```bash
docker-compose up
```

Ứng dụng sẽ có sẵn tại `http://localhost:8080`

## Kiểm Thử (Testing)

### Kiểm Thử Đơn Vị (Unit Testing)
```bash
# Chạy tất cả các bài kiểm thử đơn vị
mvn test

# Chạy kiểm thử cho một lớp cụ thể
mvn test -Dtest=ProductServiceTest

# Chạy kiểm thử với coverage report
mvn test jacoco:report
```

### Kiểm Thử Tích Hợp (Integration Testing)
```bash
# Chạy tất cả các bài kiểm thử tích hợp
mvn verify

# Chạy kiểm thử tích hợp với profile cụ thể
mvn verify -P integration-test
```

### Kiểm Thử API (API Testing)
Sử dụng Postman collection trong thư mục `src/test/resources/postman` để kiểm thử các API endpoints.

### Kiểm Thử Hiệu Suất (Performance Testing)
```bash
# Chạy kiểm thử hiệu suất với JMeter
jmeter -n -t src/test/jmeter/ecommerce-test-plan.jmx
```

## API Endpoints

### API Sản Phẩm

1. Lấy Tất Cả Sản Phẩm
```bash
curl -X GET http://localhost:8080/products
```

2. Lấy Sản Phẩm Theo ID
```bash
curl -X GET http://localhost:8080/products/{id}
```

3. Lọc Sản Phẩm
```bash
curl -X GET "http://localhost:8080/products?category=1&minPrice=10&maxPrice=100&brand=NIKE&color=WHITE"
```

### API Giỏ Hàng

1. Thêm Vào Giỏ Hàng
```bash
curl -X POST http://localhost:8080/cart/add \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "productId=1&quantity=1"
```

2. Cập Nhật Giỏ Hàng
```bash
curl -X POST http://localhost:8080/cart/update \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "productId=1&quantity=2"
```

3. Xóa Khỏi Giỏ Hàng
```bash
curl -X POST http://localhost:8080/cart/remove \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "productId=1"
```

4. Xóa Giỏ Hàng
```bash
curl -X POST http://localhost:8080/cart/clear
```

### Định Dạng Phản Hồi
Tất cả các API endpoints trả về phản hồi theo định dạng sau:
```json
{
    "code": 200,
    "status": "OK",
    "message": "",
    "data": {}
}
```

## Cấu Hình Cơ Sở Dữ Liệu

### Môi Trường Phát Triển (H2)
```properties
spring.datasource.url=jdbc:h2:mem:midterm
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
```

### Môi Trường Sản Xuất (PostgreSQL)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/spring
spring.datasource.username=postgres
spring.datasource.password=abc123
spring.datasource.driver-class-name=org.postgresql.Driver
```

## Cấu Hình Bảo Mật
Ứng dụng sử dụng Spring Security với cấu hình cơ bản cho phép tất cả các yêu cầu (cho mục đích trình diễn). Trong môi trường sản xuất, cần triển khai xác thực và phân quyền phù hợp.




