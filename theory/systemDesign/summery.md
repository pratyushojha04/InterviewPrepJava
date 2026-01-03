Excellent — this is a **great consolidation list**. These are *core* system-design concepts interviewers expect you to explain clearly — **with intuition, diagrams, and practical notes**.

I’ll cover each topic:

1️⃣ SQL vs NoSQL
2️⃣ CAP Theorem (the *real* meaning)
3️⃣ Sharding strategies
4️⃣ How load balancers work internally
5️⃣ Strong vs Eventual consistency
6️⃣ Kafka internals
7️⃣ Caching issues & fixes
8️⃣ Rate limiting (Token Bucket vs Leaky Bucket)
9️⃣ API Gateway vs Load Balancer
🔟 Designing idempotent APIs
1️⃣1️⃣ Backpressure handling

---

# 1️⃣ SQL vs NoSQL

## Concept (simple)

| SQL                | NoSQL                      |
| ------------------ | -------------------------- |
| Structured tables  | Flexible docs/keys/columns |
| Fixed schema       | Dynamic schema             |
| Strong consistency | Often eventual consistency |
| Vertical scaling   | Horizontal scaling         |
| Complex joins      | Denormalized data          |

### SQL examples:

MySQL, PostgreSQL, Oracle

### NoSQL types:

* **Key-Value** (Redis)
* **Document** (MongoDB)
* **Column-family** (Cassandra)
* **Graph** (Neo4j)

---

## When to choose what?

**Use SQL when:**

* strong consistency needed
* relational data
* ACID transactions

**Use NoSQL when:**

* huge scale
* flexible schema
* high write throughput
* low-latency distributed reads

---

# 2️⃣ CAP Theorem — REAL Meaning

A distributed system **cannot guarantee all three** at once:

```
C — Consistency (all nodes see same data)
A — Availability (system always responds)
P — Partition tolerance (network splits tolerated)
```

👉 Partition tolerance is **mandatory** in real distributed systems.

So systems choose:

* **CP** → prefer consistency (e.g., Zookeeper, MongoDB configured strongly)
* **AP** → prefer availability (e.g., Cassandra, DynamoDB)

> CAP is about behavior **during network partitions**, not daily operation.

---

# 3️⃣ Sharding Strategies

Sharding = splitting data across machines.

### Strategies:

#### 1️⃣ Key-based (Hash)

```
shard = hash(userId) % n
```

✔ balanced
❌ reshuffling when adding nodes

#### 2️⃣ Range-based

```
users A–K on shard1
users L–Z on shard2
```

✔ range queries fast
❌ hotspots possible

#### 3️⃣ Directory / Lookup table

A central map of key → shard
✔ flexible
❌ directory becomes critical component

---

# 4️⃣ How Load Balancers Work Internally

```
Clients
  |
Load Balancer
  |------> Server1
  |------> Server2
  |------> Server3
```

### What LB does:

✔ health checks
✔ routing requests
✔ retries
✔ sticky sessions
✔ SSL termination

### Algorithms:

* Round robin
* Least connections
* Weighted routing
* IP hash
* Consistent hashing

Internal flow:

```
Accept connection -> choose server -> forward -> receive response -> return
```

---

# 5️⃣ Strong vs Eventual Consistency

### Strong Consistency

All reads see latest write.

```
Write X=10
Read -> always 10
```

Used in financial systems.

### Eventual Consistency

Nodes sync **over time**.

```
Write -> nodes update later
```

Used in DynamoDB, Cassandra, S3
✔ faster
❌ stale reads possible

---

# 6️⃣ Kafka Internals (how it really works)

```
Producer -> Topic -> Partitions -> Consumers
```

### Key pieces:

* **Broker** — Kafka server
* **Topic** — stream category
* **Partition** — ordered log
* **Offset** — position pointer
* **Consumer group** — shared consumption

Messages are **append-only logs** stored on disk.
Kafka is fast because:

✔ sequential disk writes
✔ zero-copy I/O
✔ batching
✔ partitioning

Consumers **pull** and control offset (replay possible).

---

# 7️⃣ Caching Issues & Solutions

### ❌ Cache stampede

Many requests miss → all hit DB
✔ fix: **lock or stale-while-revalidate**

### ❌ Cache avalanche

Cache layer goes down
✔ fix: distributed cache, stagger expiries

### ❌ Cache inconsistency

Cache stale vs DB
✔ fix:

* write-through
* write-behind
* cache invalidation on update

### ❌ Hot keys

One key overloaded
✔ fix: key sharding or replication

---

# 8️⃣ Rate Limiting Algorithms

### ⏳ Token Bucket (widely used)

```
Bucket fills at rate R
Each request uses 1 token
If empty → reject
```

✔ allows bursts
Used by: API Gateways, Cloudflare

---

### 🚰 Leaky Bucket

```
Requests leave at fixed rate
Extra requests dropped
```

✔ smoother traffic
❌ bursts not allowed

---

# 9️⃣ API Gateway vs Load Balancer

### Load Balancer

Routes traffic to backend servers.

```
Client -> LB -> Service
```

### API Gateway

Does MUCH more:

✔ auth
✔ rate limiting
✔ routing
✔ logging
✔ versioning
✔ transforms
✔ security

```
Client -> API Gateway -> (auth / rate limit / route) -> Services
```

> API gateway is **application layer**.
> Load balancer is **network routing layer**.

---

# 🔟 Designing Idempotent APIs

Idempotent = **same request repeated → same result**.

### Why needed?

Retries due to failures.

### Examples

```
PUT /users/1   // safe
DELETE /users/1 // safe
POST /payment  // NOT safe unless designed
```

Fix POST by adding **idempotency key**:

```
POST /payment
Idempotency-Key: abc123
```

Server stores processed requests — duplicates ignored.

---

# 1️⃣1️⃣ Backpressure Handling

Backpressure = **slow consumers signal producers to slow down**.

Why?

> Prevent crashing under overload.

### Techniques:

✔ bounded queues
✔ drop oldest / newest tasks
✔ rate limits
✔ async processing
✔ circuit breakers
✔ buffer limits

In reactive systems:

```
Producer -> onBackpressure() -> slow down
```

Kafka handles backpressure naturally because consumers **control pull rate**.

---

# 🧠 Interview “one-liners” (use these)

* “SQL ensures ACID; NoSQL focuses on scale & flexibility.”
* “CAP isn’t about choosing two — it’s about how systems behave during partitions.”
* “Sharding distributes data horizontally for scale.”
* “Load balancers provide availability; gateways provide governance.”
* “Kafka is a distributed commit log, not just a queue.”
* “Idempotent APIs make retries safe.”
* “Backpressure prevents cascading failures.”

---

## 🚀 If you want, next I can provide:

✔ diagrams for each concept
✔ real coding examples (Spring + Redis + Kafka)
✔ practice interview questions
✔ system-design mock interview answers

Just tell me **which concept you want deeper** — and I’ll expand it with full detail and code.
