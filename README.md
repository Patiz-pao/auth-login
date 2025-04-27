# Authentication & Authorization API

เอกสารนี้อธิบายขั้นตอนการติดตั้งและใช้งาน **Authentication & Authorization API** ซึ่งพัฒนาด้วย **Spring Boot** พร้อมระบบความปลอดภัยด้วย **JWT (JSON Web Token)**

---

## Tech Stack

- **Java 21**
- **Lombok**
- **Spring Security**
- **JSON Web Token (JWT)**
- **Spring Data JPA**
- **PostgreSQL**
- **Swagger / OpenAPI**
- **Docker & Docker Compose**
- **Unit Test & Mockito**

---

## การติดตั้งและรันด้วย Docker

1. โคลนหรือดาวน์โหลดโปรเจกต์จาก GitHub Repository
   ```bash
   git clone https://github.com/Patiz-pao/auth-login.git
   cd auth-login
   ```

2. สร้างและเริ่มต้นแอปพลิเคชันและฐานข้อมูล
   ```bash
   docker-compose up -d
   ```

3. ตรวจสอบสถานะ Container
   ```bash
   docker ps
   ```

✅ เมื่อทุกอย่างทำงานเรียบร้อย API Server จะรันที่:

> **http://localhost:8080/swagger-ui/index.html**

---

## API Endpoints

| Method | Endpoint | คำอธิบาย | ADMIN | USER |
|:------:|:--------:|:--------:|:-----:|:----:|
| POST   | `/login` | เข้าสู่ระบบและรับ JWT Token เพื่อนำไปกรอกใน Authorize Header | ✅ | ✅ |
| POST   | `/create` | สร้างข้อมูลผู้ใช้ใหม่ในระบบ | ✅ | ❌ |
| GET    | `/user`  | ดึงข้อมูลโปรไฟล์ของผู้ใช้ที่กำลังล็อกอินอยู่ (อ้างอิงจาก Access Token) | ✅ | ✅ |
| GET    | `/admin` | ดึงข้อมูลผู้ใช้ทั้งหมดในระบบ | ✅ | ❌ |

---

## วิธีการใช้งาน

1. **เข้าสู่ระบบ** เพื่อขอรับ JWT Token:
   ```bash
   curl -X POST http://localhost:8080/login
   ```

2. **Username และ Password ที่ใช้ทดสอบ**

   - **Role: User**
     ```
     username: user
     password: 1234
     ```

   - **Role: Admin**
     ```
     username: admin
     password: 1234
     ```

3. **นำ Token ที่ได้รับ** ไปกรอกในปุ่ม **Authorize** บน Swagger UI เพื่อใช้งาน API อื่น ๆ

4. **เรียกใช้งาน API** ได้ตามสิทธิ์ที่กำหนดไว้ตาม Role ของผู้ใช้
