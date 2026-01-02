
# What is High-Level Architecture

**Definition (plain):**
High-level architecture (HLA) describes the major components of a system and how they interact — without implementation minutiae. It shows services, data stores, external systems, and communication patterns so stakeholders can understand system shape, responsibilities, and major constraints.

**Why it matters:**

* Aligns teams (product, infra, devs).
* Helps estimate capacity, reliability, and development effort.
* Guides non-functional design (scaling, reliability, latency, security).

**Typical HLA elements:**

* Clients (web, mobile)
* Edge (CDN, load balancer)
* API Layer / Gateway
* Application layer (web servers, services)
* Data layer (databases, caches, queues)
* Observability (metrics, logs, tracing)
* External integrations (payment gateways, third-party APIs)

**Real-world example — e-commerce HLA:**

* Clients: browser, mobile app
* CDN: serve product images, assets
* Load balancer + API Gateway: route requests, enforce auth, throttling
* Microservices: Catalog, Cart, Orders, Payment, Recommendation
* Datastores: Catalog DB (read-optimized), Orders DB (ACID), Redis for cart cache, Kafka for events
* Observability: Prometheus + Grafana, distributed tracing (Jaeger)

# Horizontal vs Vertical Scaling

**Vertical scaling (scale-up):** increase resources of a single machine (CPU, RAM, faster disk).

* Pros: simple, no distributed complexity.
* Cons: single point of failure, finite limit, often expensive at high end.

**Horizontal scaling (scale-out):** add more machines/instances running the same service behind a load balancer.

* Pros: better fault tolerance, practically unlimited right-sizing, cheaper on commodity hardware, easier to scale specific components.
* Cons: complexity of distributed systems (consistency, coordination, session handling).

**When to use which:**

* Start with vertical for simple MVPs (easier).
* Move to horizontal before you hit single-machine limits or need high availability.

**Real-world use case — traffic surge (Black Friday):**

* Vertical: add CPU/RAM to the app DB server for a short sales spike.
* Horizontal: spin up more stateless web/app servers, put them behind LB; use autoscaling groups to scale back after.

**Key implication:**
To scale horizontally, design components stateless (or externalize state to Redis/DB), use sticky-less sessions, and ensure idempotent requests when possible.

# Monolithic vs Microservices Architecture

**Monolithic architecture:** single deployable artifact that contains UI, business logic, data access in one process.

* Pros: simple to develop and test initially; local calls are fast; easier to debug for small teams.
* Cons: hard to scale parts separately, larger deploys, long-term coupling, risky deploys.

**Microservices architecture:** system split into multiple services, each owning a bounded context and deployable independently. Services communicate via network (HTTP/gRPC, message queues).

* Pros: independent deploys, scale components independently, tech heterogeneity per service, smaller codebases per team.
* Cons: operational complexity (service discovery, monitoring), network overhead, data consistency challenges.

**When to choose:**

* Monolith: small team, simple product, need fast delivery.
* Microservices: larger product, multiple teams, need independent scaling and faster independent deployments.

**Real-world example — ride-hailing app:**

* Monolith early: authentication, ride matching, payments all in one process. Faster to iterate.
* Microservices later: split into User Service, Trip Matching Service, Pricing Service, Payments, Notification Service; this lets pricing scale separately from notification bursts.

**Transition pattern:** start with a modular monolith (clear module boundaries) then extract services when needed.

# Request Flow Fundamentals

**A canonical request flow (modern web app):**

1. Client (browser/mobile) issues request.
2. DNS resolves -> client to CDN (if static assets).
3. Load Balancer / API Gateway receives request.

   * Can do TLS termination, routing, rate limiting, auth.
4. Gateway routes to appropriate backend (monolith or specific microservice).
5. Backend may: read/write DB, use cache (Redis), enqueue message (Kafka), call other services (sync/async).
6. Backend returns response -> gateway -> LB -> client.
7. Observability: logs/metrics/traces collected at each hop.

**Example sequence for “place order”:**
Client -> API Gateway (`/orders`) -> Orders Service validates -> calls Inventory Service (sync or via event) -> Inventory reserves items -> Orders Service writes order into Orders DB -> Publish `order.created` event to Kafka -> downstream Payment Service consumes event or API call -> Payment confirms -> Orders Service updates status -> response to client.

**Synchronous vs Asynchronous calls:**

* Sync (HTTP/gRPC): simple but increases end-to-end latency and tight coupling.
* Async (message queue): improves resilience and throughput, enables eventual consistency.

**Java snippet (simple request flow) — Spring Boot controller example (stateless):**

```java
// OrdersController.java
@RestController
@RequestMapping("/orders")
public class OrdersController {
    private final OrderService orderService;
    public OrdersController(OrderService orderService) { this.orderService = orderService; }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest req) {
        OrderDto created = orderService.createOrder(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
```

`OrderService` may call other services via RestTemplate/WebClient or publish events.

# Understanding Latency & Throughput

**Latency:** time taken to service a single request (often measured in ms). Includes network time + processing time.

* Examples: DNS lookup, TCP handshake, TLS negotiation, server processing, DB query time, disk I/O.

**Throughput:** amount of work done per unit time (requests/sec, transactions/sec). High throughput means more requests processed per second.

**Their relationship:**

* Under light load, latency may be low and throughput small. As load increases, latency often increases (queues build up), and throughput may hit saturation (max capacity). Goal: increase throughput while keeping latency acceptable.

**Measures & metrics:**

* p50, p95, p99 latency percentiles (tail latency matters).
* Requests per second (RPS).
* Error rates, saturation (CPU, memory, IO), queue lengths.

**Real-world analogy:** toll booth: latency = how long one car takes to pass; throughput = cars per minute. Adding more booths (horizontal scaling) increases throughput.

**How to improve latency and throughput:**

* Caching (Redis, CDN) to reduce load on DB.
* Database indexing and query optimization.
* Use connection pooling.
* Partition/shard data.
* Batch operations (reduce per-request overhead) — improves throughput.
* Use async processing for non-blocking tasks (reduces synchronous latency).
* Keep services stateless; scale horizontally.
* Use circuit breakers (resilience), bulkheads (isolate failures), and rate limiting.

# Real-world use case end-to-end — E-commerce during peak sale

**Problem:** orders spike 10x. Need low latency checkout and high throughput order intake.
**HLA approach:**

* CDN for assets.
* API Gateway with WAF and rate limiting.
* Stateless app servers autoscaled horizontally.
* Redis for session/cart caching.
* Read replicas for product catalog; master for orders DB (or use separate DB for orders).
* Use message queue (Kafka) for order processing (payment, shipping). Orders API acknowledges request quickly and returns `accepted` while downstream workers finalize.
* Use circuit breaker for payment gateway.
* Monitor p50/p95/p99 latencies; scale worker pools accordingly.

**Why this helps:** shortens perceived latency (client gets quick ack), increases throughput (queues decouple spike from slow downstream services), and isolates failures.

# Java Code Examples (concise, practical)

## 1) Minimal Spring Boot REST endpoint (stateless — easy to scale horizontally)

```java
// build on Spring Boot (spring-boot-starter-web)
// ProductController.java
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService){this.productService = productService;}

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String id) {
        ProductDto dto = productService.getProductById(id);
        return ResponseEntity.ok(dto);
    }
}
```

Make sure `ProductService` is lightweight and reads from a cache (Redis) or read-replica DB to reduce latency.

## 2) Microservice-to-microservice call using WebClient (non-blocking)

```java
// InventoryClient.java
@Component
public class InventoryClient {
    private final WebClient webClient;
    public InventoryClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://inventory-service").build();
    }

    public Mono<Boolean> reserve(String sku, int qty) {
        return webClient.post()
            .uri("/reserve")
            .bodyValue(Map.of("sku", sku, "qty", qty))
            .retrieve()
            .bodyToMono(ReserveResponse.class)
            .map(ReserveResponse::isSuccess)
            .timeout(Duration.ofSeconds(2)) // avoid waiting forever
            .onErrorReturn(false);
    }
}
```

Use non-blocking IO to improve throughput and resource utilization.

## 3) Publish/consume event with Kafka (asynchronous order processing)

```java
// publishing
kafkaTemplate.send("orders", orderId, orderEvent);

// consuming
@KafkaListener(topics = "orders")
public void processOrder(String key, OrderEvent event) {
    // payment processing, shipping, update DB
}
```

This allows the Orders API to accept quickly and let workers process.

# Patterns to know (brief)

* **API Gateway / Edge Proxy** — central routing, auth, rate-limiting.
* **Service Discovery** — find microservice instances (Eureka, Consul, DNS).
* **Circuit Breaker** — prevent cascading failures (Hystrix/Resilience4j).
* **Bulkhead** — isolate resources per service.
* **CQRS** — separate read & write models for scaling.
* **Event-driven architecture** — decoupling with message buses (Kafka, RabbitMQ).
* **Backpressure** — slow down producers if consumers can't keep up.

# Troubleshooting tips & metrics to instrument

**Essential metrics:** RPS, latency (p50/p95/p99), error rate, CPU/memory IO, DB latency, queue lengths.
**Tracing:** instrument end-to-end traces (OpenTelemetry, Jaeger) to find hotspots.
**Load testing:** use JMeter / k6 to simulate load. Measure where latency grows and which resource saturates.
**If tail latency spikes:** check GC pauses, thread pool saturation, retry storms, or a slow downstream service.

# Quick Decision Map

* Need fast iteration, small team → **Monolith / Modular Monolith**.
* Need independent scaling, multiple teams, large system → **Microservices** (but accept operational cost).
* Need simple short-term scaling → **Vertical scaling**.
* Need long-term capacity & HA → **Horizontal scaling** + stateless services and externalized state.

# Short checklist to make services ready for horizontal scaling

1. Make web/app servers **stateless** (store sessions in Redis or token-based).
2. Use **load balancer** with health checks.
3. Externalize file storage to S3 or object store.
4. Use **connection pools** and tune DB connections for many app instances.
5. Use **cache** for hot reads (Redis, CDN).
6. Implement **health endpoints** and metrics (Prometheus).
7. Make endpoints **idempotent** if possible (helps retries).

---
Here you go — **CAP Theorem + ACID vs BASE** explained in great depth *and* with **practical real-world examples + Java/Spring code snippets**, focusing on how these concepts impact actual backend development and system design.

---

# ⭐ 1) CAP Theorem (Consistency, Availability, Partition Tolerance)

## 🔥 Plain Definition

In a distributed system, **you can only guarantee two of the three at any moment**:

| Property                    | Meaning                                            |
| --------------------------- | -------------------------------------------------- |
| **Consistency (C)**         | Every read returns the latest write                |
| **Availability (A)**        | Every request gets a response (success or failure) |
| **Partition Tolerance (P)** | System continues to work even if network splits    |

👉 **Partition Tolerance is non-negotiable** in distributed systems
Because networks *will* fail, partitions *will* happen.

So in reality, you choose **CA**, **CP**, or **AP**, depending on your business needs.

---

# ✔ Choosing CAP Properties (Real-world systems)

| System                               | Tradeoff | Reason                                                |
| ------------------------------------ | -------- | ----------------------------------------------------- |
| **SQL DB (Postgres, MySQL Cluster)** | CP       | Avoid inconsistent reads during failures              |
| **MongoDB, Cassandra, DynamoDB**     | AP       | Never stops serving reads/writes even under partition |
| **Banking systems**                  | CP       | Consistency > Availability                            |
| **Social feeds, shopping carts**     | AP       | Availability > Strict consistency                     |

---

# 🎯 Real-World Scenario

## Example: **E-commerce Inventory System**

### ❌ During network partition:

* Warehouse DB A shows **5 items left**
* Warehouse DB B shows **5 items left**

If 7 people place orders at the same time…

### **AP System** (e.g., DynamoDB, Cassandra)

* Accepts writes in both partitions
* Might oversell
* Fixes count later via reconciliation
* Good for: carts, likes, views, search queries

### **CP System** (e.g., Postgres)

* Will reject writes on one partition
* Prevents overselling
* Bad UX but prevents inconsistency
* Good for: payments, inventory management, wallet transactions

---

# ⭐ 2) ACID Properties (Traditional SQL)

Ensures strong correctness for transactions.

| Property            | Meaning                                       |
| ------------------- | --------------------------------------------- |
| **Atomicity (A)**   | All steps succeed or NONE                     |
| **Consistency (C)** | Must move DB from valid → valid state         |
| **Isolation (I)**   | Concurrent transactions don’t interfere       |
| **Durability (D)**  | Once committed, data is saved even on failure |

### ACID Example — **Bank Transfer**

* Debit account A
* Credit account B
  If credit fails, debit is rolled back → no partial results.

---

# ⭐ 3) BASE Properties (NoSQL)

Used in distributed systems prioritizing availability.

| Property                      | Meaning                                        |
| ----------------------------- | ---------------------------------------------- |
| **Basically Available (B)**   | System almost always responds                  |
| **Soft-state (S)**            | State may change over time (async replication) |
| **Eventually consistent (E)** | All replicas converge *eventually*             |

### BASE Example — **Amazon Shopping Cart**

* Adds appear immediately (high availability)
* Replicas sync later
* Temporary inconsistencies allowed

---

# ⭐ ACID vs BASE Comparison

| Feature      | ACID                   | BASE                          |
| ------------ | ---------------------- | ----------------------------- |
| Consistency  | Strong                 | Eventual                      |
| Availability | Medium                 | High                          |
| Scaling      | Hard (vertical mostly) | Easy (horizontal)             |
| Use cases    | Banking, inventory     | Feeds, carts, logs, analytics |
| Examples     | MySQL, Postgres        | DynamoDB, Cassandra, MongoDB  |

---

# ⭐ Practical Java Examples

---

# ☕ 1) ACID — Java + Spring Boot + PostgreSQL

### Example: **Bank Transfer Transaction (Atomic & Consistent)**

```java
@Service
public class BankService {

    @Autowired
    private AccountRepository repo;

    @Transactional  // ACID
    public void transferMoney(Long fromId, Long toId, double amount) {

        Account from = repo.findById(fromId).orElseThrow();
        Account to = repo.findById(toId).orElseThrow();

        if (from.getBalance() < amount)
            throw new RuntimeException("Insufficient Funds!");

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        repo.save(from);
        repo.save(to);
    }
}
```

### ✔ Effects:

* If **any step fails**, entire action rolls back → atomic
* No inconsistent states → consistent
* Transaction lock → isolation
* Commit written to WAL → durable

This is **classic ACID**.

---

# ☕ 2) BASE — Using MongoDB (Eventual Consistency Example)

### Example: **User Likes a Post (High Availability)**

Single write request. Read may not reflect latest write immediately.

```java
@Service
public class PostService {

    @Autowired
    private MongoTemplate mongo;

    public void likePost(String postId) {
        Query q = new Query(Criteria.where("_id").is(postId));
        Update update = new Update().inc("likes", 1);

        mongo.updateFirst(q, update, "posts"); 
        // No transaction! No strict consistency.
    }
}
```

### ✔ Behavior:

* Fast, highly available
* Eventually all replicas sync
* If two clients like simultaneously, likes may momentarily differ
* BASE behavior

---

# ☕ 3) CAP Example — Choosing CP vs AP in Java

## **CP Example (Consistent System using DB Transaction)**

Inventory must NEVER oversell.

```java
@Transactional
public void reserveInventory(String sku, int qty) {
    Inventory inv = repo.findBySkuForUpdate(sku); // row lock

    if (inv.getAvailable() < qty)
        throw new RuntimeException("Out of stock");

    inv.setAvailable(inv.getAvailable() - qty);
    repo.save(inv);
}
```

✔ Uses row locking → consistent
❌ If DB partitioned, request fails → no availability

---

## **AP Example (Accept writes even when partitioned)**

Using **Redis + async fix later** →

```java
public void addToCart(String userId, String sku) {
    String key = "cart:" + userId;
    redisTemplate.opsForList().leftPush(key, sku);
    // Always available
    // Cart sync to DB happens async
}
```

✔ High availability
✔ Partition resistant
❌ Cart inconsistency possible briefly

---

# ⭐ Real-World Architectural Mapping

| Component            | CAP Choice | Property System               |
| -------------------- | ---------- | ----------------------------- |
| Payments             | CP         | ACID                          |
| Orders               | CP         | ACID (for order confirmation) |
| Cart                 | AP         | BASE                          |
| Search               | AP         | BASE                          |
| Notification         | AP         | BASE                          |
| User login           | CP         | ACID                          |
| Analytics dashboards | AP         | BASE                          |

This hybrid is what FAANG uses.

---

# ⭐ Practical System Use Case — E-commerce Platform (CAP + ACID + BASE)

## 📌 Order Placement Flow

| Step                       | System | Property |
| -------------------------- | ------ | -------- |
| Cart → AP                  | BASE   |          |
| Checkout → CP              | ACID   |          |
| Inventory reservation → CP | ACID   |          |
| Payment → CP               | ACID   |          |
| Notification → AP          | BASE   |          |

Why?

* 🚀 Cart and notifications must always be available
* 💵 Payment and inventory must be strongly consistent

# ---------------------------------------
Below is a **deep, practical, engineering-focused** explanation of all system design requirements — **Functional, NFRs (Scalability, Availability, Reliability, etc.)**, **with real-world examples + Java/Spring code where applicable**.

I will treat this as if you are preparing for **FAANG-level system design interviews** and **real production backend engineering**.

---

# ⭐ 1) Functional Requirements (FR)

Functional requirements describe **what the system should do**.

### ✔ Characteristics

* User-visible
* Features, use cases, business workflows
* Define application behavior

### ✔ Real Example: *E-commerce System*

Functional requirements:

1. User can register/login
2. View products
3. Add items to cart
4. Place order
5. Make payment
6. Track order status
7. Receive notifications

### ✔ Java Example — Implementing a Functional Requirement

**Functional Requirement → “User can place an order”**

```java
@PostMapping("/orders")
public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest req) {
    OrderResponse response = orderService.placeOrder(req);
    return ResponseEntity.ok(response);
}
```

This is directly tied to business logic.

---

# ⭐ 2) Non-Functional Requirements (NFR)

NFRs describe **how the system should behave** and the **qualities** it must exhibit.

Below are all major NFRs with real-world examples and code integrations.

---

# ⭐ 3) Scalability

Ability of a system to handle **increasing load** by **adding resources**.

### Types

* **Vertical scaling** — add CPU/RAM
* **Horizontal scaling** — add more instances (preferred)

### Example

Amazon increases EC2 instances during Diwali sale.

### Java/Spring Implementation — *Stateless Scaling*

To scale horizontally, your services must be **stateless**.

```java
@RestController
public class UserController {

    @GetMapping("/profile")
    public UserDto getProfile(@RequestHeader("Authorization") String token) {
        // decode JWT (state stored in token, not server)
        return jwtService.decode(token).getUser();
    }
}
```

* No session on server
* JWT allows servers to be stateless → horizontally scalable

---

# ⭐ 4) Availability

Ability of the system to **stay operational**, even during failures.

### Availability formula

`Availability = MTBF / (MTBF + MTTR)`

### Example

* Google Search = 99.999% uptime (5 minutes/yr downtime)
* Banking systems often target 99.9%

### Java Example — Health Check Endpoint

Used by load balancer to keep only healthy instances alive.

```java
@RestController
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
```

Kubernetes uses this endpoint to kill unhealthy pods.

---

# ⭐ 5) Reliability

Probability that the system works **correctly** over time.

### Example

* Netflix video streaming rarely crashes while watching a movie
* Railway booking must reliably prevent duplicate tickets

### Java Example — Retry Mechanism (Resilience4j)

```java
@Retry(name = "inventory")
public Inventory checkInventory(String sku) {
    return webClient.get()
        .uri("/inventory/" + sku)
        .retrieve()
        .bodyToMono(Inventory.class)
        .block();
}
```

If the Inventory service fails, it retries automatically → higher reliability.

---

# ⭐ 6) Consistency Models

Defines how **distributed data** appears to users.

### Types

| Model                    | Meaning                                     | Example             |
| ------------------------ | ------------------------------------------- | ------------------- |
| **Strong Consistency**   | Reads ALWAYS see latest write               | SQL, Banking        |
| **Eventual Consistency** | Value converges eventually                  | DynamoDB, Cassandra |
| **Read-Your-Writes**     | A writer always reads their own latest data | Firebase            |

### Java Example — Strong Consistency (ACID DB)

```java
@Transactional
public void updateBalance(Long id, double amount) {
    Account acc = repo.findById(id).orElseThrow();
    acc.setBalance(acc.getBalance() + amount);
    repo.save(acc);
}
```

DB guarantees strict consistency.

### Eventual Consistency (BASE) Example

Using asynchronous events:

```java
@KafkaListener(topics = "order-placed")
public void updateAnalytics(OrderEvent event) {
    analyticsService.increment(event.productId());
}
```

Analytics may lag behind by a few seconds.

---

# ⭐ 7) Durability

Once data is written and acknowledged, it should **never be lost**.

### Example

* Banking transaction must persist even if server crashes
* Order confirmation can't be lost

### Java Example — Transactional Write to Disk

```java
@Transactional
public void saveTransaction(Transaction t) {
    transactionRepository.save(t);
}
```

PostgreSQL writes to WAL (Write-Ahead Log) → durable.

### Kafka Example — Durable Messaging

```java
kafkaTemplate.send("payments", paymentEvent)
             .get(); // ensures message is persisted
```

Kafka writes to disk → survives failures.

---

# ⭐ 8) Latency & Response Time

* **Latency** = time to process a single request
* **Response time** = latency + queues + network overhead

### Example

* Uber rider app must respond < 200 ms
* WhatsApp message delivery must feel instant (< 50 ms)

### Java Example — Redis Caching to Improve Latency

```java
public Product getProduct(String id) {
    String cached = redis.get(id);

    if (cached != null)
        return objectMapper.readValue(cached, Product.class);

    Product p = repo.findById(id).orElseThrow();
    redis.set(id, objectMapper.writeValueAsString(p));
    return p;
}
```

Cache hit = 1–3 ms
DB hit = 10–100 ms
→ overall system becomes much faster.

---

# ⭐ 9) Fault Tolerance

System continues to operate even when **one part fails**.

### Methods

* Replication
* Auto-restart
* Circuit breakers
* Failover nodes

### Example

Netflix continues streaming even if one region fails.

### Java Example — Circuit Breaker

```java
@CircuitBreaker(name = "paymentCB", fallbackMethod = "fallbackPayment")
public PaymentResponse processPayment(PaymentRequest req) {
    return paymentClient.charge(req);
}

public PaymentResponse fallbackPayment(PaymentRequest req, Throwable t) {
    return new PaymentResponse("FAILED_TEMPORARILY");
}
```

Prevents cascading failures.

---

# ⭐ 10) Observability

Ability to understand **internal state** from **external outputs** like:

* Logs
* Metrics
* Traces

### Example

Uber uses Jaeger tracing to track ride requests across 50+ services.

### Java Implementation

### Logging with SLF4J

```java
log.info("Order placed: {}", orderId);
```

### Metrics with Micrometer + Prometheus

```java
Counter counter = Counter.builder("orders_created").register(meterRegistry);
counter.increment();
```

### Distributed Tracing (OpenTelemetry)

```java
Span span = tracer.spanBuilder("place-order").startSpan();
try {
    orderService.placeOrder();
} finally {
    span.end();
}
```

This allows Grafana/Jaeger dashboards.

---

# ⭐ Full Summary Table (With Use Cases & Code Concepts)

| NFR                    | Meaning                | Real Example                   | Java/Spring Technique         |
| ---------------------- | ---------------------- | ------------------------------ | ----------------------------- |
| **Scalability**        | Handle more load       | Amazon sale traffic            | Stateless REST, Redis, LB     |
| **Availability**       | System stays up        | Google Search 24/7             | Health checks, autoscaling    |
| **Reliability**        | Works correctly always | Netflix streaming              | Retry, redundancy             |
| **Consistency Models** | Data correctness       | Bank (strong), Cart (eventual) | ACID txn, Kafka events        |
| **Durability**         | Data never lost        | Payments                       | DB WAL, Kafka persistent log  |
| **Latency**            | Speed per request      | WhatsApp                       | Caching, async                |
| **Fault Tolerance**    | Survive failures       | AWS multi-AZ                   | Circuit breakers, replication |
| **Observability**      | System visibility      | Uber traces                    | Logs, Prometheus, Jaeger      |

---

# ⭐ 2.1 NETWORKING CONCEPTS (Deep + Practical)

---

# ⭐ 1) DNS (Domain Name System)

### ✔ What it does

DNS converts **human-readable names** → **IP addresses**.

```
google.com  --->  142.250.182.78
```

### ✔ Why needed?

Humans don’t remember IPs.
Services also rely on DNS for **service discovery**.

---

### ⭐ DNS Resolution Flow

1. Browser checks local cache
2. OS resolver checks /etc/hosts
3. OS queries recursive resolver (ISP or Google 8.8.8.8)
4. Recursive resolver queries root → TLD → authoritative servers
5. IP returned to client
6. Client caches DNS for TTL

---

### ⭐ Real-World Example

When a user opens `amazon.in`, DNS resolves to a **CloudFront CDN edge server**, not the origin server → better latency.

---

### ⭐ Java Example – DNS Lookup

```java
import java.net.*;

public class DNSLookup {
    public static void main(String[] args) throws Exception {
        InetAddress ip = InetAddress.getByName("google.com");
        System.out.println("IP: " + ip.getHostAddress());
    }
}
```

---

# ⭐ 2) DHCP (Dynamic Host Configuration Protocol)

### ✔ What it does

Automatically assigns:

* IP address
* Subnet mask
* Gateway
* DNS server

to client devices.

---

### ⭐ Why important?

Without DHCP, you would manually configure network details for every laptop/phone/VM.

---

### ⭐ Real-World Example

When you join WiFi:

* Your device broadcasts a DHCP DISCOVER
* Router responds with OFFER → REQUEST → ACK
* Device gets an IP automatically

---

# ⭐ 3) IP, TCP, UDP (Transport & Network Layer)

---

# ✔ IP (Internet Protocol)

Responsible for **routing packets between machines**.

* Best-effort delivery
* No guarantee of order
* No guarantee of delivery
* Stateless

---

# ✔ TCP (Transmission Control Protocol)

Reliable, ordered, connection-oriented.

### Features:

* Guarantees delivery
* Retransmissions
* Flow control
* Congestion control
* Ordered packets

### Used for:

* HTTP / HTTPS
* APIs
* Database connections
* File transfers

---

# ✔ UDP (User Datagram Protocol)

Fast, connectionless, unreliable.

### Features:

* No handshake
* No retransmission
* Packets may drop
* Very low latency

### Used for:

* Live streaming
* Video conferencing
* Gaming
* IoT data
* DNS queries

---

### ⭐ Java Example — TCP Socket Server

```java
ServerSocket server = new ServerSocket(9000);
Socket client = server.accept();

BufferedReader in = new BufferedReader(
     new InputStreamReader(client.getInputStream()));

System.out.println("Client: " + in.readLine());
```

---

### ⭐ Java Example — UDP Communication

```java
DatagramSocket socket = new DatagramSocket();
byte[] data = "Hello".getBytes();

DatagramPacket packet = new DatagramPacket(
    data, data.length, InetAddress.getByName("127.0.0.1"), 9000);

socket.send(packet);
```

---

# ⭐ 4) Load Balancers (L4 vs L7)

---

## ⭐ L4 Load Balancer (Transport Layer: TCP/UDP)

Balances traffic based on:

* IP
* Port
* Protocol

### Examples:

* AWS NLB
* HAProxy L4
* Nginx stream module
* Linux IPVS

### Pros:

* Fast
* Low latency
* Can handle millions of connections

### Cons:

* Cannot inspect HTTP headers
* No routing based on URL / cookies

---

## ⭐ L7 Load Balancer (Application Layer: HTTP/HTTPS)

Understands:

* URLs
* Headers
* Cookies
* Authentication
* Routing rules

### Examples:

* AWS ALB
* Nginx
* Envoy
* Traefik

### Pros:

* Smart routing
* Can do A/B testing
* Can do rate limiting, WAF
* Can offload SSL termination

### Cons:

* More overhead
* Slightly slower than L4

---

### ⭐ Real Example

**Amazon** uses L4 for raw speed on internal traffic, but L7 for public HTTPS routing.

---

### ⭐ Java Example — Running Behind L7 LB (X-Forwarded-For)

```java
@GetMapping("/ip")
public String getClientIp(HttpServletRequest req) {
    String ip = req.getHeader("X-Forwarded-For");
    return ip != null ? ip : req.getRemoteAddr();
}
```

Behind a load balancer, the original client IP is in the header.

---

# ⭐ 5) CDNs (Content Delivery Networks)

### ✔ What they do

Serve **static content** from servers close to users to reduce latency.

### CDN serves:

* Images
* CSS/JS
* Videos
* Static HTML
* Product images
* Thumbnails
* ML artifacts (models)

---

### ⭐ Real-world example

When a user in India requests `amazon.in` product images:

* CloudFront edge server in Mumbai serves it
* No need to fetch from US region
* Reduces latency from ~240ms → ~20ms

---

### ⭐ CDN Workflow

```
User → Edge CDN (cache hit?) → (no) Origin Server
                                    ↓
                              CDN caches object
```

---

### ⭐ Java Example — Setting Cache Headers

```java
@GetMapping(value="/image")
public ResponseEntity<byte[]> getImage() {
    return ResponseEntity.ok()
        .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS))
        .body(imageBytes);
}
```

This instructs CDN + browser to cache data.

---

# ⭐ 6) Reverse Proxy vs Forward Proxy

---

# ⭐ Reverse Proxy (Server-side)

### ✔ Definition:

A proxy that sits **in front of servers**.

```
Client → Reverse Proxy → Backend servers
```

### Used for:

* Load balancing
* SSL termination
* Rate limiting
* Caching
* Hiding internal service structure

### Examples:

* Nginx
* Envoy
* HAProxy
* Apache
* Traefik

### Real Example

Nginx routes:

```
/api/users → users-service
/api/orders → orders-service
```

---

### Java Example — Forwarded Header Handling

```java
@GetMapping("/whoami")
public String whoami(@RequestHeader("X-Forwarded-Host") String host) {
    return "Request passed through reverse proxy: " + host;
}
```

---

# ⭐ Forward Proxy (Client-side)

### ✔ Definition:

A proxy that stands **between client and the internet**.

```
Client → Forward Proxy → Internet
```

### Used for:

* Access control
* Restricting websites
* Anonymity
* Corporate firewalls
* Caching outbound requests

### Examples:

* Squid
* Corporate proxies
* VPNs

---

### ✔ Difference Summary

| Feature    | Reverse Proxy                   | Forward Proxy                    |
| ---------- | ------------------------------- | -------------------------------- |
| Appears to | Servers                         | Clients                          |
| Purpose    | Protect servers, load balancing | Client anonymity, access control |
| Location   | Server-side                     | Client-side                      |
| Examples   | Nginx, Envoy                    | VPN, corporate proxy             |

---

# ⭐ Real-World Architecture Diagram

```
Client
  |
  | DNS Lookup
  ↓
CDN Edge
  |
  ↓
L7 Load Balancer
  |
  ↓
Reverse Proxy (Nginx)
  |
  ↓
Microservices
  |
   → DB / Cache / Queue
```


---
---

Below is a **complete, production-ready, deeply technical explanation** of all communication patterns—**SYNC/ASYNC, REST, WebSockets, gRPC, Long Polling, SSE, RPC**—with:

✅ Real-world use cases
✅ Architecture diagrams
✅ Spring Boot / Java code for each pattern
✅ When to use what (FAANG-level clarity)

This is exactly the level expected in **SDE/System Design interviews + real backend engineering**.

---

# ⭐ 2.2 COMMUNICATION PATTERNS (Deep + Practical)

---

# ⭐ 1) Synchronous vs Asynchronous Communication

## ✅ 1. Synchronous (Blocking)

Client waits for a response before proceeding.

```
Client → Request → Server
Client ← Response ← Server
```

### Use cases:

* Payments
* Login
* Fetching data for UI
* Microservice-to-microservice request that must return immediately

### 👍 Pros:

* Simple
* Predictable
* Great for CRUD operations

### 👎 Cons:

* Tight coupling
* Higher latency
* Cascading failures possible

---

### ⭐ Java/Spring Example (Synchronous REST Call)

```java
WebClient client = WebClient.create("http://inventory-service");

public Mono<Inventory> checkStock(String sku) {
    return client.get()
        .uri("/stock/" + sku)
        .retrieve()
        .bodyToMono(Inventory.class);
}
```

---

## ✅ 2. Asynchronous (Non-blocking)

Client does NOT wait; work is done in background.

```
Client → Request → Server
Client continues...

Server → Callback/Webhook → Client
```

### Use cases:

* Sending emails
* Generating reports
* Order events → Kafka
* Notifications
* ML tasks

### 👍 Pros:

* Highly scalable
* Avoids blocking calls
* Better performance under load

### 👎 Cons:

* Harder to debug
* Eventual consistency

---

### ⭐ Java Example — Async Processing with Kafka

```java
kafkaTemplate.send("order-events", orderEvent);
```

Consumer:

```java
@KafkaListener(topics = "order-events")
public void processEvent(OrderEvent e) {
    // async processing
}
```

---

# ⭐ 2) REST API Basics

REST = Representational State Transfer
Uses HTTP verbs:

| Verb   | Use            |
| ------ | -------------- |
| GET    | Retrieve       |
| POST   | Create         |
| PUT    | Replace        |
| PATCH  | Update partial |
| DELETE | Remove         |

---

### ⭐ Key REST Concepts

* **Stateless** (each request independent)
* **Resource-based** endpoints
* **JSON** most common
* **Uniform Interface**

---

### ⭐ Example REST Endpoint in Spring Boot

```java
@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable String id) {
        return productService.get(id);
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return productService.save(product);
    }
}
```

---

# ⭐ 3) WebSockets (Full-Duplex Communication)

### ✔ What it is:

Persistent **two-way** connection between client and server.

```
Client ⇆ Server
```

### Use cases:

* Chat apps (WhatsApp Web)
* Live dashboards (stocks, IoT)
* Multiplayer gaming
* Real-time notifications

---

### ⭐ Diagram (WebSocket)

```
Client ---- WebSocket Upgrade ----> Server
Client ⇆ Real-time Bidirectional Messages ⇆ Server
```

---

### ⭐ Spring Boot WebSocket Example

**Add config:**

```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat").withSockJS();
    }
}
```

**Message Controller:**

```java
@MessageMapping("/send")
@SendTo("/topic/messages")
public String send(String msg) {
    return msg;
}
```

---

# ⭐ 4) gRPC (Lightning Fast RPC Framework)

Built by Google.
Uses HTTP/2 + Protocol Buffers.

### ⭐ Why gRPC?

* Faster than REST
* Strong typing
* Low latency
* Bi-directional streaming
* Perfect for microservices

---

### ⭐ gRPC Communication Types:

| Type                     | Meaning            |
| ------------------------ | ------------------ |
| Unary                    | Request → Response |
| Server streaming         | Response stream    |
| Client streaming         | Request stream     |
| Bi-directional streaming | Both stream        |

---

### ⭐ Real-world use

* Google internal microservices
* Uber: Dispatch, maps
* Netflix: fast inter-service calls
* Any ML inference backend

---

### ⭐ gRPC Proto Example

**proto definition:**

```proto
syntax = "proto3";

service ProductService {
  rpc GetProduct (ProductRequest) returns (ProductReply);
}

message ProductRequest {
  string id = 1;
}

message ProductReply {
  string id = 1;
  string name = 2;
}
```

---

### ⭐ Java Server Implementation

```java
public class ProductServiceImpl extends ProductServiceGrpc.ProductServiceImplBase {
    @Override
    public void getProduct(ProductRequest req, StreamObserver<ProductReply> res) {
        ProductReply reply = ProductReply.newBuilder()
                .setId(req.getId())
                .setName("Laptop")
                .build();

        res.onNext(reply);
        res.onCompleted();
    }
}
```

---

# ⭐ 5) Long Polling vs Server-Sent Events (SSE)

---

# ⭐ Long Polling

Client sends a request → server **holds** it until new data arrives.

```
Client → (long wait) → Server
```

### Use:

* Chats (older apps)
* Notifications (pre-WebSocket era)

### Problems:

* Inefficient
* High server load
* Many open connections

---

### ⭐ Long Polling Java Example

```java
@GetMapping("/updates")
public DeferredResult<String> getUpdates() {
    DeferredResult<String> result = new DeferredResult<>();
    updateService.addListener(result);
    return result;
}
```

---

# ⭐ SSE (Server Sent Events)

### ✔ What it is:

Server → Client **one-way continuous updates**.

```
Client ← Stream of updates ← Server
```

Uses HTTP/1.1, not a WebSocket.

### Use cases:

* Live scoring
* Notifications
* Stock market
* Real-time logs

---

### ⭐ SSE vs WebSockets

| Feature    | SSE             | WebSocket    |
| ---------- | --------------- | ------------ |
| Direction  | Server → Client | Both ways    |
| Protocol   | HTTP            | TCP          |
| Use        | Notifications   | Chat, gaming |
| Complexity | Low             | Medium       |
| Reconnect? | Built-in        | Manual       |

---

### ⭐ SSE Spring Example

```java
@GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<String> streamEvents() {
    return Flux.interval(Duration.ofSeconds(1))
               .map(seq -> "Event: " + seq);
}
```

---

# ⭐ 6) RPC Fundamentals (Remote Procedure Call)

RPC = call a method on remote server **as if it's local**.

### ✔ Key concepts:

* Stub: auto-generated client code
* Serialization
* Network transport (often HTTP or TCP)

---

### ⭐ RPC Example (Conceptual)

```
client.getUser("101");
```

Internally:

1. Client serializes request
2. Sends over network
3. Server method executes
4. Response returned

---

### ⭐ Java Example — Classic RPC (Spring RMI)

**Server:**

```java
public interface HelloService extends Remote {
    String sayHello(String name) throws RemoteException;
}
```

**Implementation:**

```java
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
```

(RPC is largely replaced by modern frameworks like gRPC.)

---

# ⭐ DECISION GUIDE — Which Communication Method to Use?

| Requirement               | Choose           |
| ------------------------- | ---------------- |
| Simple CRUD               | REST             |
| Real-time bidirectional   | WebSocket        |
| Server → Client real-time | SSE              |
| Low latency microservices | gRPC             |
| Legacy browsers           | Long Polling     |
| Batch/event async         | Kafka            |
| ML inference              | gRPC             |
| IoT streaming             | WebSocket / gRPC |
| High throughput           | gRPC             |

---

# ⭐ Architecture Diagram (All Patterns)

```
        Client
          |
          | REST (sync)
          ↓
    API Gateway ----> Backend Services (REST/gRPC)
          |
          | WebSockets/SSE
          ↓
    Real-time Notification Service
          |
          | Kafka (async)
          ↓
    Background Workers (async)
          |
          | gRPC (low-latency)
          ↓
        ML Service
```

---
Below is a **deeply detailed, production-grade explanation** of:

### ✅ 3.1 Vertical & Horizontal Scaling

### ✅ Stateless vs Stateful Services

### ✅ Scaling strategies (compute, storage, cache)

### ✅ 3.2 Load Balancing

### ✅ Load Balancing Algorithms (Round Robin, Least Connections, IP Hash)

### ✅ Global Load Balancing

### ✅ Health Checks

### ✅ Failover Strategies

All with **real-world examples + diagrams + Java/Spring code where relevant**.

---

# ⭐ 3.1 Vertical & Horizontal Scaling

---

# ⭐ Vertical Scaling (Scale UP)

Increase the **power of a single machine**.

```
4 CPU → 16 CPU  
16 GB RAM → 64 GB RAM  
HDD → SSD
```

### ✔ Real-world examples

* Upgrading a **PostgreSQL** instance from `db.m5.large` → `db.m5.2xlarge`
* Increasing RAM for a **Redis** node
* Increasing CPU for an **ML inference server**

### 👍 Pros

* Simple
* No code changes
* Useful for databases

### 👎 Cons

* HARD LIMIT — cannot scale beyond a point
* Expensive
* Single point of failure

---

# ⭐ Horizontal Scaling (Scale OUT)

Add **more machines** behind a load balancer.

```
1 server → 10 servers
```

### ✔ Real-world examples

* Adding multiple EC2 instances behind AWS ALB
* Adding more Kubernetes pods
* Adding more Kafka brokers
* Adding more Redis cluster shards

### 👍 Pros

* Unlimited scaling
* High availability
* Fault tolerance

### 👎 Cons

* Requires stateless architecture
* Distributed system complexity

---

# ⭐ Key Difference Table

| Feature             | Vertical | Horizontal       |
| ------------------- | -------- | ---------------- |
| Scaling limit       | High     | Almost unlimited |
| Cost efficiency     | Low      | High             |
| Architecture change | None     | Required         |
| DB use              | Great    | Harder           |
| Microservices       | Poor fit | Perfect fit      |

---

# ⭐ Stateless vs Stateful Services

---

# ⭐ Stateless Service

Does **not store user/session data** locally.
Any instance can handle any request.

```
Frontend → LB → Service-1 or Service-2 (same behavior)
```

### Examples:

* Most REST APIs
* Authentication via **JWT**
* Microservices
* ML inference API

### Java Example (Stateless Authentication)

```java
@GetMapping("/profile")
public User getUser(@RequestHeader("Authorization") String jwt) {
    return jwtService.parse(jwt);
}
```

No session stored in server → easy horizontal scaling.

---

# ⭐ Stateful Service

Keeps data **on the machine**, like:

* Session state
* Cache
* File system
* Local in-memory storage

### Examples:

* Databases
* Stateful caches (Redis, Memcached)
* Web servers storing session in memory
* Kafka brokers

### Problem:

Hard to scale horizontally, because state is tied to a specific instance.

---

# ⭐ Converting a Stateful Service to Stateless

Use **external state storage**:

| Old               | New                 |
| ----------------- | ------------------- |
| In-memory session | Redis session store |
| Local file upload | S3 bucket           |
| In-memory cache   | Redis/Memcache      |
| Local DB          | RDS / Aurora        |

---

# ⭐ Scaling Strategies for Compute, Storage, Cache

---

# ⭐ 1. Scaling Compute

### Horizontal:

* More app servers
* Kubernetes HPA (CPU-based autoscaling)
* Serverless (Lambda functions auto-scale)

### Vertical:

* Give the app more RAM/CPU

### Java Example — Ensuring Horizontal Scalability

Use **Thread pools** + **non-blocking IO**:

```java
@Bean
public WebClient webClient() {
    return WebClient.builder().build(); // non-blocking
}
```

---

# ⭐ 2. Scaling Storage

### Vertical:

* Increase disk size
* Faster SSD
* More RAM in DB node

### Horizontal:

* Sharding (MongoDB, Cassandra)
* Partitioning (Postgres, MySQL)
* S3 for object storage
* Distributed file systems

---

# ⭐ 3. Scaling Cache

### Vertical:

* More RAM for Redis / Memcached

### Horizontal:

* Redis Cluster
* Sharded Memcached
* CDN edge nodes

---

# ⭐ 3.2 Load Balancing

Load balancer distributes traffic across servers, prevents overload, and ensures high availability.

---

# ⭐ Types of Load Balancing Algorithms

---

# ⭐ 1. Round Robin

Rotate sequentially:

```
Req1 → Server1  
Req2 → Server2  
Req3 → Server3  
...
```

### Best for:

* Uniform load
* Stateless applications

### Example in Nginx:

```
upstream app {
    server app1;
    server app2;
    server app3;
}
```

---

# ⭐ 2. Least Connections

Send traffic to server with **fewest active connections**.

```
Server1: 5 conns  
Server2: 1 conn ← send next request
```

### Best for:

* Long-lived connections
* WebSockets
* Video streaming

---

# ⭐ 3. IP Hash

Same client → same server.

### Algorithm:

```
server = hash(client_ip) % server_count
```

### Best for:

* Sticky sessions
* Gaming
* Older applications storing session in memory

---

# ⭐ Global Load Balancing (Geo-Aware Routing)

Distributes requests to **closest geographic region**.

### Types:

* **GeoDNS** (Route53, Cloudflare)
* **Anycast routing**
* **GSLB** (Global Server Load Balancing)

### Example:

User in India → Mumbai region
User in USA → Virginia region

### Benefits:

* Low latency
* Fault isolation
* Regional failover

---

# ⭐ Health Checks (Very Important)

Ensures traffic only goes to healthy servers.

Types:

### ✔ 1. L4 health check

Checks TCP port open.

### ✔ 2. L7 health check

Calls an API endpoint like:

```
GET /health
```

### Spring Boot Example

```java
@RestController
public class HealthController {
    @GetMapping("/health")
    public String health() {
        return "UP";
    }
}
```

---

# ⭐ Failover Strategies

Failover = automatically switch to a healthy instance/region when one fails.

---

# ⭐ 1. Active-Active

All nodes active.

### Good for:

* Global systems
* High availability
* Microservices

---

# ⭐ 2. Active-Passive

Primary handles all traffic → secondary takes over when needed.

### Used in:

* Databases (primary-replica)
* Payment systems

---

# ⭐ 3. Failover Types

### ✔ Server failover

Instance goes down → LB routes traffic to others.

### ✔ Region failover

AWS region fails → Route53 shifts to backup region.

### ✔ Database failover

Replica is promoted to primary.

---

# ⭐ Java Example — Circuit Breaker for Failover

```java
@CircuitBreaker(name="orders", fallbackMethod="fallback")
public Order getOrder(String id) {
    return orderClient.fetch(id);  // maybe failover will happen
}

public Order fallback(String id, Throwable t) {
    return new Order("TEMP_UNAVAILABLE");
}
```

---

# ⭐ Summary Table

| Concept            | Summary                      |
| ------------------ | ---------------------------- |
| Vertical Scaling   | Bigger machine               |
| Horizontal Scaling | More machines                |
| Stateless Service  | Easy to scale                |
| Stateful Service   | Hard to scale                |
| Round Robin        | Sequential distribution      |
| Least Connections  | Best for WebSockets          |
| IP Hash            | Sticky sessions              |
| Global LB          | Region-aware routing         |
| Health Checks      | Detect unhealthy instances   |
| Failover           | Auto switch to healthy nodes |

---
