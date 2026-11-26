# Demo Shop – Spring Boot 4 + JSP/JSTL

Chức năng:
- Đăng ký / Đăng nhập / Đăng xuất (Spring Security, mật khẩu BCrypt, CSRF)
- Role ADMIN: CRUD **Category** và **User**, có **tìm kiếm** + **phân trang**
- Phân quyền: `/admin/**` chỉ ADMIN

## Yêu cầu
- JDK 17+ (khuyến nghị 21), Maven 3.9+ (hoặc dùng IDE)

## Chạy
```bash
mvn spring-boot:run
```
Mở http://localhost:8080

Tài khoản mẫu (tự tạo khi khởi động):
| Username | Password | Role  |
|----------|----------|-------|
| admin    | admin123 | ADMIN |
| user     | user123  | USER  |

Mặc định dùng H2 in-memory (dữ liệu mất khi tắt app).
Dùng MySQL: sửa `application.properties` (đã có sẵn đoạn comment).

## Chạy unit test
```bash
mvn test
```
