# MapCycle

MapCycle is a Java 24 application built with Spring MVC and Spring Data JPA, backed by PostgreSQL. Its goal is to provide a clean and clear REST API to manage bicycle rides. The project favors less boilerplate with Lombok and environment-based configuration.

## Features
- RESTful API (Spring MVC)
- Persistence layer (Spring Data JPA)
- PostgreSQL database
- Cleaner code with Lombok
- Configuration via environment variables
- Schema validation with DDL `validate`

## Tech Stack
- Java 24
- Spring Boot (Web, Data JPA)
- PostgreSQL
- Lombok
- Maven

## Requirements
- JDK 24
- PostgreSQL 14+ 
- Maven 3.9+
- Git

## Quick Start

1) Clone the repository 
```bash
git clone [https://github.com/your-org/mapcycle.git](https://github.com/your-org/mapcycle.git) cd mapcycle
```

2) Set environment variables
```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/map_cycle 
export SPRING_DATASOURCE_USERNAME=your_user 
export SPRING_DATASOURCE_PASSWORD=your_password 
export SPRING_JPA_HIBERNATE_DDL_AUTO=validate 
export SPRING_PROFILES_ACTIVE=dev
```

3) Start PostgreSQL (Docker example)
```bash
docker run --name mapcycle-db
-e POSTGRES_DB=map_cycle
-e POSTGRES_USER=your_user
-e POSTGRES_PASSWORD=your_password
-p 5432:5432 -d postgres:16

```

4) Run the application
```bash
   mvn spring-boot:run
```

By default, it starts at http://localhost:8080.

## Configuration

Common settings (override via env or properties):
- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`
- `spring.jpa.hibernate.ddl-auto` (validate|none|update|create|create-drop)
- `spring.jpa.show-sql` (true|false)
- `server.port`
- `spring.profiles.active`

Note: With DDL set to `validate`, the schema is not auto-created/updated. Ensure your database schema exists (via migrations like Flyway/Liquibase or manually) before running.

## API Endpoints

### 🚴 Ride Endpoints

| HTTP Method | Endpoint | Description |
| --- | --- | --- |
| GET | /api/rides | Retrieves a list of all rides. |
| GET | /api/rides/{id} | Retrieves a specific ride by its ID. |
| POST | /api/rides | Adds a new ride. |
| PUT | /api/rides/{id} | Updates an existing ride by its ID. |
| DELETE | /api/rides/{id} | Deletes a specific ride by its ID. |
| PATCH | /api/rides/{id}/complete | Completes a ride (optional final distance and end time). |
### 🗺️ Route Endpoints

| HTTP Method | Endpoint | Description |
| --- | --- | --- |
| GET | /api/routes | Retrieves a list of all routes. |
| GET | /api/routes/{id} | Retrieves a specific route by its ID. |
| POST | /api/routes | Adds a new route. |
| PUT | /api/routes/{id} | Updates an existing route by its ID. |
| DELETE | /api/routes/{id} | Deletes a specific route by its ID. |
### 👤 User Endpoints

| HTTP Method | Endpoint | Description |
| --- | --- | --- |
| GET | /api/users | Retrieves a list of all users. |
| GET | /api/users/{id} | Retrieves a specific user by its ID. |
| POST | /api/users | Registers/creates a new user. |
| PUT | /api/users/{id} | Updates an existing user by its ID. |
| DELETE | /api/users/{id} | Deletes or deactivates a specific user. |

### Example Requests

Create a new ride via POST /api/rides:
```shell
curl -X POST http://localhost:8080/api/rides \
-H "Content-Type: application/json" \
-d '{
"userId": 1,
"routeId": 10,
"startTime": "2025-08-17T18:30:00",
"status": "IN_PROGRESS",
"distanceKm": 0.0
}'
```

Complete a ride via PATCH /api/rides/{id}/complete:
```shell
# Distance only
curl -X PATCH "http://localhost:8080/api/rides/1/complete?finalDistanceKm=26.7"

# Distance and end time
curl -X PATCH "http://localhost:8080/api/rides/1/complete?finalDistanceKm=26.7&endTime=2025-08-17T20:45:00"
```

Add a new route via POST /api/routes:
```shell
curl -X POST http://localhost:8080/api/routes \
-H "Content-Type: application/json" \
-d '{
  "name": "Riverside Loop",
  "description": "Scenic route along the river.",
  "difficultyLevel": "EASY",
  "estimatedDistanceKm": 18.5,
  "estimatedTimeInMinutes": 60,
  "routeData": "{\"type\":\"LineString\",\"coordinates\":[[29.0,41.0],[29.1,41.1]]}",
  "creatingUserId": 1
}'
```

Update a route via PUT /api/routes/{id}:

```shell
curl -X PUT http://localhost:8080/api/routes/5 \
-H "Content-Type: application/json" \
-d '{
  "name": "Riverside Loop (Updated)",
  "description": "Updated details.",
  "difficultyLevel": "MODERATE",
  "estimatedDistanceKm": 20.0,
  "estimatedTimeInMinutes": 70,
  "routeData": "{\"type\":\"LineString\",\"coordinates\":[[29.0,41.0],[29.2,41.2]]}",
  "creatingUserId": 1
}'
```

Register a new user via POST /api/users:
```shell
curl -X POST http://localhost:8080/api/users \
-H "Content-Type: application/json" \
-d '{
  "username": "johndoe",
  "password": "StrongP@ssw0rd",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe"
}'
```

Update a user via PUT /api/users/{id}:
```shell
curl -X PUT http://localhost:8080/api/users/3 \
-H "Content-Type: application/json" \
-d '{
  "email": "john.new@example.com",
  "firstName": "John",
  "lastName": "Newman"
}'
```