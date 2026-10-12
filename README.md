# OrderFlow — Java · Spring Boot · PostgreSQL

A portfolio REST API demonstrating **Java 21, Spring Boot, Spring Data JPA, PostgreSQL, Flyway migrations, transactions, order placement and inventory control**.

Architecture: REST API → Spring Boot service → transactional repository → PostgreSQL.

Features: unique product SKU, validated price and quantities, product catalog, persisted orders, conditional stock reservation for concurrency protection, and SQL migrations.

Run locally after source files are added with `docker compose up --build`. This is independent educational code with fictitious products; no payment processing or public security layer.

Author: Samra Faisal Khan · [GitHub](https://github.com/sfk219)
