# financial-pipeline-workflow

A **Product Approval Pipeline API** built with Spring Boot and PostgreSQL — inspired by the structured investment product approval workflows used by firms like Arrow IA for enterprise clients such as LPL Financial and Raymond James.

The platform manages the full lifecycle of a structured investment product from submission through compliance review to final approval or rejection — providing financial analysts with a clean, auditable trail of every decision made.

---

## What it does

Financial firms submit structured investment products (Buffered Notes, Barrier Notes, Autocall products) for compliance review. The API manages the entire approval workflow:

```
Firm submits product → PENDING → UNDER_REVIEW → APPROVED / REJECTED
```

Analysts can attach review notes with decisions at each stage. Every state transition is validated — a product cannot skip from `PENDING` directly to `APPROVED` without going through `UNDER_REVIEW`.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 25 |
| Framework | Spring Boot 4.0.3 |
| Database | PostgreSQL 14 |
| ORM | Spring Data JPA / Hibernate |
| API Docs | Swagger UI (springdoc-openapi) |
| Build Tool | Maven |
| Validation | Jakarta Bean Validation |

---

## Architecture

The project follows strict layered architecture — each layer only communicates with the one directly below it:

```
Controller   → receives HTTP requests, returns DTOs as JSON
Service      → business logic, workflow validation, entity ↔ DTO conversion
Repository   → data access layer, extends JpaRepository
Model        → JPA entities mapped to PostgreSQL tables
DTO          → request/response shapes — decoupled from DB schema
Exception    → global exception handling via @ControllerAdvice
```

```
com.arrow.server
├── controller
│   ├── ProductController.java
│   └── ReviewNoteController.java
├── service
│   ├── ProductService.java              (interface)
│   ├── ReviewNoteService.java           (interface)
│   └── impl
│       ├── ProductServiceImpl.java
│       └── ReviewNoteServiceImpl.java
├── repository
│   ├── ProductRepository.java
│   └── ReviewNoteRepository.java
├── model
│   ├── Product.java
│   ├── ReviewNote.java
│   ├── ProductType.java                 (enum)
│   ├── ProductStatus.java              (enum)
│   └── ReviewDecision.java             (enum)
├── dto
│   ├── CreateProductRequest.java
│   ├── ProductResponse.java
│   ├── CreateReviewRequest.java
│   ├── ReviewNoteResponse.java
│   └── ErrorResponse.java
├── exception
│   ├── ResourceNotFoundException.java
│   └── GlobalExceptionHandler.java
└── config
    └── CorsConfig.java
```

---

## Data Model

### Product

Represents a structured investment product submitted for compliance review.

| Field | Type | Description |
|---|---|---|
| id | Long | Auto-generated primary key |
| name | String | Product name e.g. "JPM Buffered Note Series 4" |
| issuer | String | Issuing institution e.g. "JPMorgan" |
| type | ProductType | BUFFERED, BARRIER, or AUTOCALL |
| underlyingAsset | String | e.g. "SPY", "SPX", "QQQ" |
| protectionLevel | Double | Downside protection % e.g. 10.0 |
| termMonths | Integer | Product duration in months |
| submittedBy | String | Name of the submitting advisor |
| submittedAt | LocalDateTime | Auto-set on creation |
| status | ProductStatus | PENDING → UNDER_REVIEW → APPROVED/REJECTED |

### ReviewNote

An analyst's review comment attached to a product during evaluation.

| Field | Type | Description |
|---|---|---|
| id | Long | Auto-generated primary key |
| product | Product | @ManyToOne — FK to products table |
| reviewerName | String | Name of the reviewing analyst |
| comment | String | Review commentary |
| decision | ReviewDecision | APPROVE, REJECT, or NEEDS_MORE_INFO |
| createdAt | LocalDateTime | Auto-set on creation |

### Entity Relationship

```
Product (1) ──────────< ReviewNote (many)
                        product_id FK
```

---

## API Endpoints

### Products

```
POST   /api/products                      Submit a product for review
GET    /api/products                      List all products
GET    /api/products/{id}                 Get product by ID
GET    /api/products/status/{status}      Filter by status
GET    /api/products/type/{type}          Filter by type
GET    /api/products/issuer/{issuer}      Filter by issuer (partial, case-insensitive)
PATCH  /api/products/{id}/status          Update product status
```

### Reviews

```
POST   /api/products/{id}/reviews         Add a review note to a product
GET    /api/products/{id}/reviews         Get all reviews for a product
GET    /api/products/{id}/reviews?decision=APPROVE   Filter reviews by decision
```

### Workflow Validation

Status transitions are validated in the service layer:

```
PENDING      → UNDER_REVIEW   ✅
PENDING      → APPROVED       ❌ must go through UNDER_REVIEW first
UNDER_REVIEW → APPROVED       ✅
UNDER_REVIEW → REJECTED       ✅
APPROVED     → anything       ❌ final state
REJECTED     → anything       ❌ final state
```

Adding a review note automatically updates the product status:

```
Decision APPROVE         → product status → APPROVED
Decision REJECT          → product status → REJECTED
Decision NEEDS_MORE_INFO → product status → UNDER_REVIEW
```

---

## Request / Response Examples

### Submit a product

```bash
POST /api/products
Content-Type: application/json

{
  "name": "JPM Buffered Note Series 4",
  "issuer": "JPMorgan",
  "type": "BUFFERED",
  "underlyingAsset": "SPY",
  "protectionLevel": 10.0,
  "termMonths": 24,
  "submittedBy": "John Smith"
}
```

Response `201 Created`:

```json
{
  "id": 1,
  "name": "JPM Buffered Note Series 4",
  "issuer": "JPMorgan",
  "type": "BUFFERED",
  "underlyingAsset": "SPY",
  "protectionLevel": 10.0,
  "termMonths": 24,
  "submittedBy": "John Smith",
  "submittedAt": "2026-03-17T19:30:00",
  "status": "PENDING"
}
```

### Add a review note

```bash
POST /api/products/1/reviews
Content-Type: application/json

{
  "reviewerName": "Nitish Ahuja",
  "comment": "Protection level looks solid. Underlying asset is liquid.",
  "decision": "APPROVE"
}
```

Response `201 Created`:

```json
{
  "id": 1,
  "productId": 1,
  "reviewerName": "Nitish Ahuja",
  "comment": "Protection level looks solid. Underlying asset is liquid.",
  "reviewDecision": "APPROVE",
  "createdAt": "2026-03-17T19:35:00"
}
```

### Error response

```json
{
  "status": 404,
  "message": "Product not found with id: 99",
  "timestamp": "2026-03-17T19:30:00"
}
```

---

## HTTP Status Codes

| Code | When |
|---|---|
| 200 | Successful GET, PATCH |
| 201 | Successful POST (resource created) |
| 400 | Invalid input / validation failure |
| 404 | Product or review not found |
| 409 | Duplicate entry / constraint violation |
| 500 | Unhandled server error |

---

## Getting Started

### Prerequisites

```
Java 25
PostgreSQL 14+
Maven 3.x
```

### Database setup

```sql
CREATE DATABASE arrow;
```

### Configuration

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/arrow
spring.datasource.username=postgres
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false

server.port=8080
```

### Run

```bash
mvn clean install -DskipTests
mvn spring-boot:run
```

### API Documentation

Once running, visit:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Key Design Decisions

**Service interfaces over concrete classes** — `ProductService` and `ReviewNoteService` are interfaces with `Impl` classes. Controllers depend on the interface, enabling polymorphism and making the service layer easily swappable for testing.

**Constructor injection over `@Autowired`** — all dependencies injected via constructor, making fields `final` and dependencies explicit. Easier to test, no reflection-based injection.

**DTOs decoupled from entities** — `CreateProductRequest` controls what clients can send. `ProductResponse` controls what they receive. Clients never set `id`, `submittedAt`, or `status` directly — the system manages these.

**Automatic status transitions** — adding a review note with `APPROVE` automatically moves the product to `APPROVED`. Business logic lives in the service layer, not the controller.

**`@Transactional(readOnly = true)`** on all read methods — tells Hibernate to skip dirty checking on reads, improving performance. Write methods use `@Transactional` to guarantee atomicity across multiple DB operations.

**Global exception handling** — `@ControllerAdvice` catches all exceptions in one place. Clients always receive structured JSON error responses, never raw stack traces.

---

## Product Types

| Type | Description |
|---|---|
| BUFFERED | Protects against first X% of losses. Market drops within buffer — no loss. |
| BARRIER | Full protection unless market drops past a barrier level — then fully exposed. |
| AUTOCALL | Automatically terminates early if market hits a target on observation dates, paying a premium. |

---

## Author

**Nitish Ahuja**
[nitishahuja.com](https://nitishahuja.com) · [LinkedIn](https://linkedin.com/in/nitishahuja) · [GitHub](https://github.com/nitishahuja)