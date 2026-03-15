# Workflow

```
Client
│
▼
order-service
│
│ OrderCreatedEvent
▼
inventory-service
│
│ InventoryReservedEvent
▼
payment-service
│
│ PaymentCompletedEvent
▼
order-service
```

## Saga Flow

```mermaid
sequenceDiagram

participant Client
participant OrderService
participant InventoryService
participant PaymentService

Client->>OrderService: Create Order
OrderService->>Kafka: OrderCreatedEvent

Kafka->>InventoryService: OrderCreatedEvent
InventoryService->>Kafka: InventoryReservedEvent

Kafka->>PaymentService: InventoryReservedEvent
PaymentService->>Kafka: PaymentCompletedEvent

Kafka->>OrderService: PaymentCompletedEvent
OrderService->>OrderService: OrderStatus = COMPLETED
```

---

# Rollback

```
payment-service
  │
  │ PaymentFailedEvent
  ▼
inventory-service
  │
  │ InventoryReleasedEvent
  ▼
order-service
```

```mermaid
sequenceDiagram

OrderService->>Kafka: OrderCreatedEvent
Kafka->>InventoryService: OrderCreatedEvent

InventoryService->>Kafka: InventoryReservedEvent
Kafka->>PaymentService: InventoryReservedEvent

PaymentService->>Kafka: PaymentFailedEvent
Kafka->>InventoryService: PaymentFailedEvent

InventoryService->>Kafka: InventoryReleasedEvent
Kafka->>OrderService: InventoryReleasedEvent

OrderService->>OrderService: OrderStatus = CANCELLED
```