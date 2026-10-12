# OrderFlow · Java Spring Boot + PostgreSQL

A database-driven inventory and order REST API featuring atomic stock reservation and SQL schema migrations.

## Features
- Java 21, Spring Boot, Spring Data JPA and PostgreSQL
- Product creation, unique SKU validation, catalog retrieval
- Transactional order placement and conditional SQL stock decrement to prevent overselling
- Flyway schema migrations, REST error responses, service-layer unit tests
- Docker Compose and GitHub Actions

## Architecture
`HTTP client → Spring Boot controllers → transactional service → JPA → PostgreSQL`

## Run locally (requires Docker Engine/Desktop and Compose)
```bash
docker compose up --build
```
API: http://localhost:8080/api

Example:
```bash
curl -X POST http://localhost:8080/api/products -H 'Content-Type: application/json' \
  -d '{"sku":"LAP-001","name":"Laptop","price":899.00,"stock":5}'
curl -X POST http://localhost:8080/api/orders -H 'Content-Type: application/json' \
  -d '{"productId":1,"quantity":2}'
```

## Tests
```bash
cd backend && mvn test
```

## Scope
Independent portfolio demo with fake product data. Not an employer system; no payment processing or auth. The Spring Boot build and database deployment have not yet been executed and verified in this environment.
