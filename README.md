# Expense Tracker API (Backend)

A Spring Boot REST API for tracking personal expenses, with per-user accounts,
email verification, categories, monthly reports/summaries, and CSV/JSON
import–export of expense data.

## Tech Stack

- **Java 21** + **Spring Boot 4.1.1**
- Spring Data JPA, Spring Security (JWT auth), Spring Validation, Spring Mail
- Database: H2 (in-memory, dev) — PostgreSQL or MySQL for production
- Build: Maven (wrapper included)

## Features

- User accounts with register/login and per-user data isolation
- Email verification on registration:
  - Backend emails a verification token (expires in 10 minutes)
  - Account is activated only after the token is verified
- CRUD for expenses
- Categories for grouping expenses
- Reports/summaries (totals by month and category)
- File persistence: CSV/JSON import and export of expenses

## Project Structure

```
src/main/java/com/example/expensetracker/
├── ExpenseTrackerApiApplication.java   # Main application class
├── model/        # User, Expense, Category entities
├── repository/   # JPA repositories
├── service/      # Business logic (auth, expense CRUD, summaries, email sending)
├── util/         # Token generation, email templates
├── controller/   # REST endpoints
├── dto/          # Request/response objects
└── security/     # Security config with JWT filter
```

## API Endpoints

| Method | Endpoint | Purpose |
|--------|----------|---------|
| POST | `/api/auth/register` | Register a new user (sends verification email) |
| POST | `/api/auth/verify` | Verify email token (10-min expiry) |
| POST | `/api/auth/resend-verification` | Resend verification email |
| POST | `/api/auth/login` | Login, returns JWT (only after verified) |
| GET/POST | `/api/expenses` | List / create expenses |
| PUT/DELETE | `/api/expenses/{id}` | Update / delete expense |
| GET/POST | `/api/categories` | List / create categories |
| GET | `/api/reports/summary?month=...` | Totals by category/month |
| GET | `/api/expenses/export` | Export expenses as CSV |
| POST | `/api/expenses/import` | Import expenses (CSV/JSON) |

## Authentication & Verification Flow

1. `POST /api/auth/register` creates a user (unverified) and sends a
   verification token to the user's email.
2. `POST /api/auth/verify` with the token activates the account
   (token expires after 10 minutes).
3. `POST /api/auth/resend-verification` issues a new token if the user
   didn't receive one or it expired.
4. `POST /api/auth/login` returns a JWT — only allowed for verified accounts.
5. Send the JWT as `Authorization: Bearer <token>` on all protected endpoints.

## Getting Started

### Prerequisites

- Java 21+
- Maven (or use the included `./mvnw` wrapper)

### Run the app

```bash
./mvnw spring-boot:run
```

The API starts on `http://localhost:8080`.

### H2 Console (dev)

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:expensetrackerdb`
- Username: `sa` (no password)

### Run tests

```bash
./mvnw test
```

## Configuration

Main settings live in `src/main/resources/application.properties`:

```properties
spring.application.name=expense-tracker-api
spring.datasource.url=jdbc:h2:mem:expensetrackerdb
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

For email sending, configure Spring Mail (e.g., Gmail SMTP):

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

For local development without SMTP, a console mailer that logs emails
to the console is recommended.

## Roadmap / Open Decisions

- Database: PostgreSQL vs MySQL (production)
- Auth: JWT (recommended)
- Email service: SMTP via Spring Mail vs mock console mailer for local dev
- Token format: 6-digit OTP vs clickable link token
