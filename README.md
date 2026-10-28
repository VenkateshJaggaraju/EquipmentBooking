Equipment Booking
A REST API built with Java 17, Spring Boot 4.1.2, Spring Data
JPA, and PostgreSQL for booking shared equipment, with a daily
capacity of five laptops.
Features
- Create a booking and retrieve it by ID.
- Check availability for a selected date.
- Validate instructor name, booking date, quantity, and purpose.
- Accept bookings for today or a future date only.
- Track availability independently for each date.
- Set new bookings to PENDING.
- Prevent bookings from exceeding the daily capacity.
Technology stack
- Java 17
- Spring Boot 4.1.2
- Spring Web MVC
- Spring Data JPA / Hibernate
- PostgreSQL
- Jakarta Bean Validation
- Lombok
- MapStruct
- Maven
- Postman for manual API testing
Prerequisites
Install JDK 17 or later, PostgreSQL, and Maven. If the project includes
Maven Wrapper files (mvnw / mvnw.cmd), you can use those instead of
installing Maven separately. Postman is optional.
Database setup
1. Start PostgreSQL.
2. Create the database:
CREATE DATABASE equipment_booking;
3. Update src/main/resources/application.properties with your local
   database credentials. Example:
server.port=1008

spring.datasource.url=jdbc:postgresql://localhost:5432/equipment_booking
spring.datasource.username=YOUR_POSTGRES_USERNAME
spring.datasource.password=YOUR_POSTGRES_PASSWORD
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
Replace the placeholder username and password with your PostgreSQL
credentials. With spring.jpa.hibernate.ddl-auto=update, Hibernate
creates or updates mapped tables automatically. If your project uses
another schema-management method, use that configuration instead.

Security: Never commit real database passwords or secrets to a
public repository. Prefer environment variables or a secrets manager for
shared and production environments.
Run the application
Run commands from the project root (the folder containing pom.xml).

Windows, with Maven Wrapper:
.\mvnw.cmd spring-boot:run
Windows, with Maven installed:
mvn spring-boot:run
macOS / Linux, with Maven Wrapper:
./mvnw spring-boot:run
The configured base URL is:
http://localhost:1008
Keep the application terminal open while testing. Stop the server with
Ctrl+C.
API endpoints
1. Create a booking
POST http://localhost:1008/api/v1/bookings

Header: Content-Type: application/json

Example request body:
{
  "instructorName": "Ravi Kumar",
  "bookingDate": "2026-10-30",
  "quantity": 2,
  "purpose": "Java programming training"
}
The date must be today or later, and quantity must be from 1 to 5
inclusive.

Expected success: 201 Created

The response should contain the booking ID, instructor name, booking
date, quantity, purpose, status, and creation timestamp.
2. Retrieve a booking
GET http://localhost:1008/api/v1/bookings/1

Replace 1 with the ID returned by the create-booking request.

Expected success: 200 OK. If the ID does not exist, the expected
status is 404 Not Found.
3. Check availability
GET http://localhost:1008/api/v1/equipment/availability?date=2026-10-30

Use the ISO date format YYYY-MM-DD.

Example response when no equipment is reserved on that date:
{
  "date": "2026-10-30",
  "totalQuantity": 5,
  "reservedQuantity": 0,
  "availableQuantity": 5
}
After a booking for two laptops on that date, availability should show
reservedQuantity as 2 and availableQuantity as 3. A different
date has its own independent capacity.
Validation and expected HTTP statuses
  Scenario                            Expected status
  Booking created                     201 Created

  Booking or availability retrieved   200 OK

  Missing/invalid fields, quantity    400 Bad Request
  outside 1--5, or past date          

  Booking ID does not exist           404 Not Found
  Requested quantity exceeds          409 Conflict
  remaining availability              
Example invalid request (quantity is too high):
{
  "instructorName": "Ravi Kumar",
  "bookingDate": "2026-10-30",
  "quantity": 6,
  "purpose": "Java programming training"
}
This request should be rejected with 400 Bad Request.
Manual testing with Postman
1. Start PostgreSQL and the Spring Boot application.
2. Send the sample POST /api/v1/bookings request.
3. Use the returned ID with GET /api/v1/bookings/{id}.
4. Check availability for the same date.
5. Attempt a booking that exceeds the remaining capacity and verify
   409 Conflict.
6. Check another date to verify date-specific capacity.
7. Test a past date and quantities 0 and 6 to verify validation.
No authentication is required for this Phase 1 API unless you have added
authentication separately.
Run automated tests
From the project root:
mvn test
Or on Windows with Maven Wrapper:
.\mvnw.cmd test
The test suite should cover:
- Request validation (required fields, quantity boundaries, and past
  dates).
- Booking creation and retrieval.
- Availability for different dates.
- Insufficient availability and the 409 Conflict response.
- Concurrent booking requests.
Concurrency requirement: A sequential test alone does not prove
concurrency safety. Tests should issue competing requests at the same
time and verify that total reservations never exceed five. This requires
transaction handling and appropriate database locking or equivalent
atomic reservation logic.
Suggested project structure
Exact package and file names may differ in your implementation.
src/
└── main/
    ├── java/
    │   └── .../
    │       ├── controller/   # REST endpoints
    │       ├── service/      # Booking and availability logic
    │       ├── repository/   # Spring Data JPA repositories
    │       ├── entity/       # JPA entities
    │       ├── dto/          # Request and response DTOs
    │       ├── mapper/       # DTO/entity mapping
    │       └── exception/    # Exception handling, if implemented
    └── resources/
        └── application.properties
Business rules
- Daily capacity: five laptops.
- Capacity is independent for each date.
- Booking date must be today or in the future.
- A successful booking reserves the requested quantity immediately.
- New bookings have PENDING status.
- Reservations must never reduce available quantity below zero.
License
Add the license required by your project or assignment, if applicable.
