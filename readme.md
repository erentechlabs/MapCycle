# MapCycle 🚴

**MapCycle** is a modern, production-ready REST API for managing bicycle rides and routes, built with Java 24 and Spring Boot. It features JWT-based authentication, gamification mechanics, and a clean layered architecture designed for scalability and maintainability.

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Database Schema](#-database-schema)
- [API Documentation](#-api-documentation)
- [Security](#-security)
- [Monitoring](#-monitoring)
- [Project Structure](#-project-structure)
- [Contributing](#-contributing)

## ✨ Features

### Core Functionality
- **User Management**: Complete user registration, authentication, and profile management
- **Ride Tracking**: Create, update, and complete bicycle rides with distance and time tracking
- **Route Management**: Define and share bicycle routes with difficulty levels and geographic data
- **Gamification**: Level progression system with experience points based on riding activity

### Technical Features
- **RESTful API** with Spring MVC
- **JWT Authentication** with Spring Security
- **Database Persistence** using Spring Data JPA with PostgreSQL
- **DTO Pattern** with MapStruct for efficient object mapping
- **Bean Validation** for request payload validation
- **Exception Handling** with global error handler
- **Actuator Endpoints** for health monitoring and metrics
- **Clean Architecture** with separation of concerns (Controllers → Services → Repositories)

## 🛠 Tech Stack

| Layer | Technology |
|-------|------------|
| **Language** | Java 24 |
| **Framework** | Spring Boot 3.5.4 |
| **Security** | Spring Security + JWT (io.jsonwebtoken 0.12.6) |
| **Data Access** | Spring Data JPA + Hibernate |
| **Database** | PostgreSQL 14+ |
| **Object Mapping** | MapStruct 1.5.5 |
| **Code Reduction** | Lombok |
| **Build Tool** | Maven 3.9+ |
| **Monitoring** | Spring Boot Actuator |
| **Validation** | Jakarta Bean Validation |

## 🏗 Architecture

The application follows a **layered architecture** pattern:

```
┌─────────────────────────────────────┐
│        Controllers Layer            │  ← REST endpoints, request/response handling
├─────────────────────────────────────┤
│          Services Layer             │  ← Business logic, transaction management
├─────────────────────────────────────┤
│       Repositories Layer            │  ← Data access, JPA operations
├─────────────────────────────────────┤
│         Database (PostgreSQL)       │  ← Data persistence
└─────────────────────────────────────┘
```

**Key Components**:
- **DTOs**: Data Transfer Objects for API contracts
- **Entities**: JPA entities representing database tables
- **Mappers**: MapStruct interfaces for entity ↔ DTO conversion
- **Security**: JWT-based authentication filter chain
- **Exception Handler**: Centralized error handling

## 📦 Prerequisites

Ensure you have the following installed:

- **JDK 24** (or compatible Java version)
- **Maven 3.9+**
- **PostgreSQL 14+**
- **Git**
- **Docker** (optional, for containerized database)

## 🚀 Installation

### 1. Clone the Repository

```bash
git clone https://github.com/your-org/mapcycle.git
cd mapcycle
```

### 2. Set Up PostgreSQL Database

**Option A: Using Docker**

```bash
docker run --name mapcycle-db \
  -e POSTGRES_DB=map_cycle \
  -e POSTGRES_USER=mapcycle_user \
  -e POSTGRES_PASSWORD=your_secure_password \
  -p 5432:5432 \
  -d postgres:16
```

**Option B: Local PostgreSQL Installation**

```sql
CREATE DATABASE map_cycle;
CREATE USER mapcycle_user WITH PASSWORD 'your_secure_password';
GRANT ALL PRIVILEGES ON DATABASE map_cycle TO mapcycle_user;
```

### 3. Configure Environment Variables

**Linux/macOS:**

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/map_cycle
export SPRING_DATASOURCE_USERNAME=mapcycle_user
export SPRING_DATASOURCE_PASSWORD=your_secure_password
export SPRING_JPA_HIBERNATE_DDL_AUTO=update
export JWT_SECRET=your_jwt_secret_key_here_minimum_256_bits
export JWT_EXPIRATION=86400000
```

**Windows (PowerShell):**

```powershell
$env:SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/map_cycle"
$env:SPRING_DATASOURCE_USERNAME="mapcycle_user"
$env:SPRING_DATASOURCE_PASSWORD="your_secure_password"
$env:SPRING_JPA_HIBERNATE_DDL_AUTO="update"
$env:JWT_SECRET="your_jwt_secret_key_here_minimum_256_bits"
$env:JWT_EXPIRATION="86400000"
```

### 4. Build and Run the Application

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The API will be available at: **http://localhost:8080**

## ⚙️ Configuration

### Application Properties

Key configuration properties (can be set via environment variables or `application.properties`):

| Property | Description | Default |
|----------|-------------|---------|
| `spring.datasource.url` | PostgreSQL JDBC URL | - |
| `spring.datasource.username` | Database username | - |
| `spring.datasource.password` | Database password | - |
| `spring.jpa.hibernate.ddl-auto` | Schema generation strategy | `validate` |
| `spring.jpa.show-sql` | Log SQL queries | `false` |
| `server.port` | Application port | `8080` |
| `jwt.secret` | JWT signing key (256+ bits) | - |
| `jwt.expiration` | JWT expiration time (ms) | `86400000` |
| `management.endpoints.web.exposure.include` | Actuator endpoints | `health,info,metrics` |

### DDL Auto Options

- `validate`: Validates schema (recommended for production)
- `update`: Updates schema automatically
- `create`: Creates schema on startup
- `create-drop`: Creates and drops schema
- `none`: No schema management

⚠️ **Production Note**: Use `validate` with proper database migration tools (Flyway/Liquibase).

## 🗄 Database Schema

### Core Entities

#### Users Table
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    role VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER',
    total_distance DECIMAL(10,2) DEFAULT 0.00,
    total_time BIGINT DEFAULT 0,
    level INTEGER DEFAULT 1,
    experience_points INTEGER DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

#### Routes Table
```sql
CREATE TABLE routes (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    difficulty_level VARCHAR(20),
    estimated_distance_km DECIMAL(10,2),
    estimated_time_in_minutes INTEGER,
    route_data TEXT,
    creating_user_id BIGINT REFERENCES users(id),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

#### Rides Table
```sql
CREATE TABLE rides (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    route_id BIGINT REFERENCES routes(id),
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
    distance_km DECIMAL(10,2),
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

## 📡 API Documentation

### Authentication

All endpoints (except registration) require JWT authentication via `Authorization: Bearer <token>` header.

### 👤 User Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/users` | Register new user | ❌ |
| GET | `/api/users` | List all users | ✅ |
| GET | `/api/users/{id}` | Get user by ID | ✅ |
| PUT | `/api/users/{id}` | Update user | ✅ |
| DELETE | `/api/users/{id}` | Deactivate user | ✅ |

**Example: Register User**

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "password": "SecureP@ss123",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe"
  }'
```

### 🗺️ Route Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/routes` | Create new route | ✅ |
| GET | `/api/routes` | List all routes | ✅ |
| GET | `/api/routes/{id}` | Get route by ID | ✅ |
| PUT | `/api/routes/{id}` | Update route | ✅ |
| DELETE | `/api/routes/{id}` | Delete route | ✅ |

**Example: Create Route**

```bash
curl -X POST http://localhost:8080/api/routes \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your_jwt_token>" \
  -d '{
    "name": "Riverside Loop",
    "description": "Scenic 18km route along the river",
    "difficultyLevel": "EASY",
    "estimatedDistanceKm": 18.5,
    "estimatedTimeInMinutes": 60,
    "routeData": "{\"type\":\"LineString\",\"coordinates\":[[29.0,41.0],[29.1,41.1]]}",
    "creatingUserId": 1
  }'
```

### 🚴 Ride Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/rides` | Start new ride | ✅ |
| GET | `/api/rides` | List all rides | ✅ |
| GET | `/api/rides/{id}` | Get ride by ID | ✅ |
| PUT | `/api/rides/{id}` | Update ride | ✅ |
| DELETE | `/api/rides/{id}` | Delete ride | ✅ |
| PATCH | `/api/rides/{id}/complete` | Complete ride | ✅ |

**Example: Start Ride**

```bash
curl -X POST http://localhost:8080/api/rides \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your_jwt_token>" \
  -d '{
    "userId": 1,
    "routeId": 5,
    "startTime": "2025-10-19T14:30:00",
    "status": "IN_PROGRESS",
    "distanceKm": 0.0
  }'
```

**Example: Complete Ride**

```bash
# With final distance and end time
curl -X PATCH "http://localhost:8080/api/rides/1/complete?finalDistanceKm=18.7&endTime=2025-10-19T15:45:00" \
  -H "Authorization: Bearer <your_jwt_token>"

# With distance only (end time defaults to current time)
curl -X PATCH "http://localhost:8080/api/rides/1/complete?finalDistanceKm=18.7" \
  -H "Authorization: Bearer <your_jwt_token>"
```

### Response Formats

**Success Response (200 OK)**

```json
{
  "id": 1,
  "username": "johndoe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "level": 1,
  "experiencePoints": 0,
  "totalDistance": 0.00,
  "totalTime": 0
}
```

**Error Response (4xx/5xx)**

```json
{
  "timestamp": "2025-10-19T14:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/users"
}
```

## 🔐 Security

### JWT Authentication Flow

1. **Register**: User creates account via `POST /api/users`
2. **Login**: User authenticates and receives JWT token
3. **Request**: Client includes token in `Authorization: Bearer <token>` header
4. **Validation**: Server validates token signature and expiration
5. **Access**: If valid, request proceeds; otherwise returns 401 Unauthorized

### Security Configuration

- **Password Encoding**: BCrypt with strength 10
- **Token Expiration**: Configurable (default 24 hours)
- **CORS**: Configured for cross-origin requests
- **CSRF**: Disabled for stateless API
- **Session Management**: Stateless (no server-side sessions)

### JWT Token Structure

```
Header: {"alg": "HS256", "typ": "JWT"}
Payload: {"sub": "username", "iat": 1234567890, "exp": 1234654290}
Signature: HMACSHA256(base64UrlEncode(header) + "." + base64UrlEncode(payload), secret)
```

## 📊 Monitoring

### Actuator Endpoints

Health and metrics endpoints are available via Spring Boot Actuator:

| Endpoint | Description |
|----------|-------------|
| `/actuator/health` | Application health status |
| `/actuator/info` | Application information |
| `/actuator/metrics` | Application metrics |

**Example**:

```bash
curl http://localhost:8080/actuator/health
```

Response:
```json
{
  "status": "UP",
  "components": {
    "db": {"status": "UP"},
    "diskSpace": {"status": "UP"}
  }
}
```

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/mapcycle/mapcycle/
│   │   ├── MapCycleApplication.java          # Main application class
│   │   ├── controllers/                       # REST controllers
│   │   │   ├── UserController.java
│   │   │   ├── RideController.java
│   │   │   └── RouteController.java
│   │   ├── services/                          # Business logic
│   │   │   ├── UserService.java
│   │   │   ├── RideService.java
│   │   │   └── RouteService.java
│   │   ├── repositories/                      # Data access layer
│   │   │   ├── UserRepository.java
│   │   │   ├── RideRepository.java
│   │   │   └── RouteRepository.java
│   │   ├── models/
│   │   │   ├── entities/                      # JPA entities
│   │   │   │   ├── User.java
│   │   │   │   ├── Ride.java
│   │   │   │   └── Route.java
│   │   │   ├── dtos/                          # Data Transfer Objects
│   │   │   │   ├── UserRegistrationDto.java
│   │   │   │   ├── UserResponseDto.java
│   │   │   │   ├── RideDto.java
│   │   │   │   └── RouteDto.java
│   │   │   ├── mappers/                       # MapStruct mappers
│   │   │   │   ├── UserMapper.java
│   │   │   │   ├── RideMapper.java
│   │   │   │   └── RouteMapper.java
│   │   │   └── enums/                         # Enumerations
│   │   │       ├── RideStatus.java
│   │   │       └── DifficultyLevel.java
│   │   └── shared/
│   │       ├── config/                        # Configuration classes
│   │       │   └── SecurityConfig.java
│   │       ├── security/                      # Security components
│   │       │   ├── jwt/
│   │       │   │   ├── JWTProvider.java
│   │       │   │   └── JwtFilter.java
│   │       │   ├── MapCycleUserDetailsService.java
│   │       │   └── UserPrincipal.java
│   │       └── exception/                     # Exception handling
│   │           └── GlobalExceptionHandler.java
│   └── resources/
│       ├── application.properties             # Configuration properties
│       └── application-dev.properties         # Dev profile properties
└── test/
    └── java/com/mapcycle/mapcycle/
        └── MapCycleApplicationTests.java      # Integration tests
```

## 🤝 Contributing

Contributions are welcome! Please follow these guidelines:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/AmazingFeature`)
3. **Commit** your changes (`git commit -m 'Add some AmazingFeature'`)
4. **Push** to the branch (`git push origin feature/AmazingFeature`)
5. **Open** a Pull Request

### Code Style
- Follow Java naming conventions
- Use Lombok annotations to reduce boilerplate
- Write comprehensive JavaDoc for public APIs
- Maintain test coverage above 80%
