# Event Driven Microservices Platform

This repository contains a sample event-driven microservices platform built with modern Java technologies.

The goal of this project is to demonstrate practical architecture patterns used in modern backend systems, including:

- Microservice architecture
- Event-driven communication
- Domain-oriented design
- Containerized infrastructure

The project is intentionally built step by step to illustrate architectural decisions and development practices.


---

# Technology Stack

- Java 21
- Spring Boot
- Maven
- JUnit 5
- Keycloak
- Docker
- Apache Kafka
- Kubernetes (planned)


---

# Architecture

Each service in the platform follows a domain-oriented structure:

- controller – REST API layer
- service – business logic
- domain – domain model
- repository – persistence layer
- event – domain events
- config – technical configuration

This separation keeps business logic independent from infrastructure concerns and supports testability and maintainability.


---

# Domain Design

The project follows a pragmatic domain-oriented design inspired by Domain-Driven Design (DDD).

Instead of implementing the complete DDD methodology, the focus is on the most valuable concepts:

- explicit domain models
- separation between domain logic and infrastructure
- domain events as communication mechanism between services

This pragmatic approach keeps the architecture understandable while still benefiting from core DDD principles.


---

# Testing Strategy

The project follows a test-first mindset inspired by Test-Driven Development (TDD).

Core business logic is covered with unit tests using JUnit 5.

Instead of strictly applying the full TDD cycle for every component, a pragmatic approach is used:

- domain logic is covered by unit tests
- REST endpoints will be tested via integration tests
- infrastructure components will be tested where appropriate

This balances development speed with reliable test coverage.


---

## 🔐 Identity & Access Management (Keycloak)

Start Keycloak:

cd infrastructure/docker/keycloak
docker compose up -d

Admin Console:
http://localhost:8082

Credentials:
admin / admin

Realm:
event-driven-microservices-platform


---

# Project Structure

```
event-driven-microservices-platform
│
├─ infrastructure
│
├─ services
│ └─ order-service
│ ├─ controller
│ ├─ service
│ ├─ domain
│ ├─ repository
│ ├─ event
│ └─ config
│
└─ README.md
```

The first service currently implemented is the **Order Service**, which exposes a REST API to create orders.


---

# Roadmap

Planned extensions include:

- publishing domain events via Apache Kafka
- additional services (inventory, payment)
- Docker-based local development environment
- integration tests using Testcontainers
- observability using OpenTelemetry


---

# Purpose of this Project

This repository is intended as a practical exploration of modern backend architecture patterns in Java.

The focus is on building a clean and understandable example of an event-driven microservices platform using commonly adopted technologies.