
---

# **1. CACHING BASICS — COMPLETE SYSTEM DESIGN EXPLANATION**

Caching is one of the most important performance optimization techniques in large-scale systems. A cache stores precomputed or frequently accessed data in a fast-access storage layer (memory, SSD, distributed store) to reduce latency, database load, and CPU consumption.

---

# **2. WHAT TO CACHE? (Complete explanation)**

A cache is useful only when:

* The data is **expensive to compute** OR **expensive to fetch**
* The data is **read frequently** but **changes infrequently**
* The system needs **low latency**, high throughput
* You want to **offload backend databases/services**

Below are the primary categories:

---

## **2.1. Cacheable Data Categories**

### **1. Static Content**

* Images, HTML fragments, CSS, JS, media files
* Rarely change → perfect for CDN
* Examples:

  * Static product listing images
  * Landing page HTML chunks
  * Documentation pages

---

### **2. Semi-Static Data**

Data that changes occasionally (minutes/hours/days), but not constantly.

Examples:

* Product details
* User profile pages
* Category filters
* Popular posts
* Frequently viewed items

These are often stored in **Redis**, **Memcached**, or application memory.

---

### **3. Query Results**

For heavy DB queries:

* JOIN-heavy SQL
* Aggregate queries
* Analytics queries
* Search results for common queries (“top 10 products”, “latest posts”)

Caching the result reduces DB load drastically.

---

### **4. Computation-Expensive Results**

Anything requiring heavy CPU or backend resources:

* ML inference result caching
* Expensive sorting
* PDF generation results
* Rendered HTML templates
* Pre-computed user dashboards

---

### **5. Session Data**

* User session
* Authentication tokens
* Rate limiting counters

Usually stored in Redis due to speed + persistence support.

---

### **6. API Responses**

Common external API calls:

* Exchange rates
* Weather data
* GeoIP responses
* Stock prices

Cache TTL based on freshness requirement.

---

### **7. Metadata / Config / Small Lookups**

* Country list
* Static configuration
* Feature flags (after fetched once)

---

# **3. CACHE PLACEMENT (FULL BREAKDOWN)**

Placement determines latency, cost, caching effectiveness, and infrastructure complexity.

There are four major caching tiers:

---

# **3.1 CLIENT-SIDE CACHE**

### **Where?**

Inside the user’s browser or mobile app.

### **Examples**

* Browser Cache-Control
* LocalStorage
* IndexedDB
* ServiceWorker cache

### **Used For**

* Static assets (JS, CSS, images)
* Rendered pages (HTML fragment caching)
* Avoiding repeated API calls (offline-first apps)

### **Advantages**

* Zero network latency (fastest possible)
* Reduces bandwidth
* No server load

### **Disadvantages**

* Limited size
* Not suitable for dynamic/personalized content
* Inconsistent across devices
* Security issues for sensitive data

---

# **3.2 CDN (CONTENT DELIVERY NETWORK)**

A **CDN is an edge cache** placed geographically close to users.

### **Caches**

* Static assets
* Images
* Videos
* API GET responses
* HTML fragments

### **How it works**

1. User sends request → CDN checks if cached
2. If HIT → cached response returned
3. If MISS → fetches from origin server → stores → then returns to user

### **Advantages**

* Very low latency for global users
* Reduces origin server load
* Great for burst traffic
* Built-in DDOS protection

### **Disadvantages**

* Hard to use for dynamic content
* Cache invalidation must be managed carefully

---

# **3.3 REVERSE PROXY CACHE (Nginx, Varnish, HAProxy)**

Sits between clients and your backend servers.

### **Works well for**

* SSR web pages
* API GET responses
* Personalized-but-cacheable responses (via Vary headers)

### **Flow**

Client → Reverse Proxy → App Server → DB

### **Advantages**

* Reduces load on application layer
* Allows API caching without modifying application code
* Supports request collapsing (one fetch for multiple users)

### **Disadvantages**

* Only works for HTTP-level caching
* Difficult for personalized data unless header-based rules are applied

---

# **3.4 DISTRIBUTED CACHE (REDIS / MEMCACHED)**

### **Key Characteristics**

* Stored in RAM → extremely fast
* Shared across multiple servers
* Supports high throughput
* Ideal for application-level, DB-level caching

### **When to use**

* DB query results
* User session storage
* Rate limiting data
* Leaderboards
* Feed generation
* Heavy read workloads

### **Redis vs Memcached**

| Feature     | Redis                                     | Memcached                     |
| ----------- | ----------------------------------------- | ----------------------------- |
| Data Types  | Many (String, Hash, List, Set, SortedSet) | Simple key-value              |
| Persistence | Yes                                       | No                            |
| Pub/Sub     | Yes                                       | No                            |
| TTL         | Yes                                       | Yes                           |
| Performance | Very fast                                 | Slightly faster for simple KV |

### **Advantages**

* Extremely fast (sub-ms)
* Supports structured caching
* Good for large clusters

### **Disadvantages**

* Requires memory (expensive)
* Needs cluster management
* Potential single point of failure (unless clustered)

---

# **4. CACHING PATTERNS / STRATEGIES (Important for System Design)**

### **Cache-Aside (Lazy Loading)**

Most commonly used pattern.

1. App checks cache → miss
2. Load from DB
3. Store in cache
4. Return to client

### **Write-Through**

Write to cache and DB at same time.

### **Write-Behind**

Write to cache → batch write to DB asynchronously.

### **Read-Through**

Cache layer automatically loads from DB on miss.

### **TTL and Eviction Policies**

* TTL: Time to Live
* Eviction: LRU, LFU, FIFO
* Prevents stale data
* Controls memory usage

---

# **5. SPRING BOOT + REDIS EXAMPLE (Caching Annotation)**

## **5.1 Setup Redis Dependency**

```xml
<!-- pom.xml -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

---

## **5.2 Enable Caching**

```java
@SpringBootApplication
@EnableCaching
public class CacheDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CacheDemoApplication.class, args);
    }
}
```

---

## **5.3 Redis Config**

```java
@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }
}
```

---

## **5.4 Caching a Service Method**

```java
@Service
public class ProductService {

    @Cacheable(value = "productCache", key = "#id")
    public Product getProductById(Long id) {
        System.out.println("Fetching from DB...");
        return fetchFromDatabase(id);
    }

    private Product fetchFromDatabase(Long id) {
        // Simulate DB call
        return new Product(id, "Laptop", 70000);
    }
}
```

---

## **5.5 Cache Eviction**

```java
@CacheEvict(value = "productCache", key = "#id")
public void updateProduct(Long id, Product newData) {
    // update DB logic
}
```

---

# **6. SYSTEM DESIGN BEST PRACTICES FOR CACHING**

### **1. Avoid Over-Caching**

Caching everything increases memory cost + complexity.

### **2. Cache Invalidation is Hard**

Use:

* Versioning
* TTL
* Event-driven invalidation (Kafka)

### **3. Monitor Cache Hit Ratio**

Target: **80–95% hit ratio**

### **4. Prevent Cache Stampede**

Use:

* Mutex locks
* Randomized TTL
* Request collapsing

### **5. Use Correct TTL**

Short TTL = fresh but less effective
Long TTL = stale but high performance

### **6. Choose right placement**

Example:

* Global traffic → CDN
* API queries → Reverse proxy
* DB-heavy → Redis
* User-specific → Application-level caching

---

# **7. SUMMARY TABLE**

| Cache Type          | Best For                        | Speed     | Complexity  |
| ------------------- | ------------------------------- | --------- | ----------- |
| Client-side         | Static assets                   | Very High | Low         |
| CDN                 | Global static content, GET APIs | High      | Medium      |
| Reverse Proxy       | Web pages, API responses        | High      | Medium      |
| Distributed (Redis) | DB results, sessions, app data  | Very High | Medium–High |

---

Below is a **complete, deep system-design–level explanation** of relational vs non-relational databases, ACID vs BASE, transaction isolation, NoSQL types, scaling patterns, partitioning, replication, hot partitions, indexing, and query optimization.
This is equivalent to what top-tier backend/system design interviews expect.

---

# **5. SQL vs NoSQL — COMPLETE SYSTEM DESIGN DEEP DIVE**

---

# **1. SQL vs NoSQL**

## **1.1 SQL Databases (Relational)**

Examples:

* MySQL
* PostgreSQL
* MariaDB
* Oracle
* Microsoft SQL Server

### **Characteristics**

* Structured schema (tables, rows, columns)
* Strong consistency
* ACID transactions
* Joins supported
* Best for complex queries
* Vertical scaling (scale-up) by default
* Mature tooling, constraints, foreign keys

### **Ideal For**

* Banking
* E-commerce orders
* Inventory
* Payment systems
* ERP/CRM
* Where strong consistency matters

---

## **1.2 NoSQL Databases (Non-Relational)**

Examples:

* MongoDB (Document)
* Cassandra, Bigtable (Column)
* Redis, DynamoDB (Key-value)
* Neo4j (Graph)

### **Characteristics**

* Schemaless or flexible schema
* Horizontal scaling (scale-out)
* Optimized for specific use cases
* Eventual consistency (mostly)
* BASE model

### **Ideal For**

* Massive scale
* High write throughput
* Semi-structured/Unstructured data
* Real-time analytics
* Large distributed systems

---

# **1.3 When to Use What? (Interview-grade Answer)**

| Use SQL When                   | Use NoSQL When                       |
| ------------------------------ | ------------------------------------ |
| Strong consistency is required | Need massive horizontal scaling      |
| Complex joins, relationships   | Data is unstructured/semi-structured |
| Ad-hoc queries, reports        | Low latency key-value lookups        |
| Transactional workloads        | High write throughput                |
| Well-defined schema            | Flexible schema                      |
| Small–medium scale             | Petabyte-scale systems               |
| Financial systems              | Social media feeds, IoT data, logs   |

**General rule**:
If the business model and data relationships matter → **SQL**
If scale and performance matter → **NoSQL**

---

# --------------------------------------------------------------

# **2. ACID vs BASE**

## **2.1 ACID (SQL Databases)**

1. **Atomicity** – entire transaction succeeds or fails
2. **Consistency** – DB remains valid after transactions
3. **Isolation** – parallel transactions don’t interfere
4. **Durability** – once committed, data persists

### **Use Cases**

* Banking
* Orders
* Payments
* Finance
* Critical consistency systems

---

## **2.2 BASE (NoSQL Databases)**

**Basically Available, Soft-state, Eventually consistent**

1. **Basically Available** – system is always available
2. **Soft-State** – state may temporarily change
3. **Eventually Consistent** – becomes consistent after some time

### **Use Cases**

* Social media feeds
* Analytics
* High-volume write-intensive workloads
* Distributed microservices

---

# --------------------------------------------------------------

# **3. Transaction Isolation Levels (SQL)**

Standard ANSI levels:

| Level                | What It Prevents       | Issues Still Possible                    |
| -------------------- | ---------------------- | ---------------------------------------- |
| **Read Uncommitted** | —                      | Dirty read, Non-repeatable read, Phantom |
| **Read Committed**   | Dirty reads            | Non-repeatable read, Phantom             |
| **Repeatable Read**  | Dirty + Non-repeatable | Phantom                                  |
| **Serializable**     | All anomalies          | Slowest, full locking                    |

### **Phenomena Explained**

* **Dirty Read:** reading uncommitted data
* **Non-repeatable Read:** same row gives different values in same transaction
* **Phantom Read:** same query returns extra/missing rows

---

# --------------------------------------------------------------

# **5.2 NoSQL Database Types — With Use Cases**

---

# **1. Key-Value Stores (Redis, DynamoDB)**

### **Structure**

`key → value (string, JSON, blob)`

### **Strengths**

* O(1) lookup
* Very fast
* Perfect for caching, sessions, rate limiting

### **Use Cases**

* Caching (Redis)
* Shopping cart sessions
* Gaming leaderboards
* IoT timeseries

---

# **2. Column Stores (Cassandra, Bigtable)**

### **Structure**

* Data stored by columns instead of rows
* Optimized for analytics & wide-column datasets

### **Strengths**

* Massive horizontal scalability
* Write-optimized
* Distributed by design
* High availability

### **Use Cases**

* Event logging
* Timeseries
* Analytics
* Real-time metrics (Netflix uses Cassandra)

---

# **3. Document Stores (MongoDB, Couchbase)**

### **Structure**

JSON-like documents; flexible schema.

### **Strengths**

* Flexible schema
* Queryable
* Nested structure
* Indexing support

### **Use Cases**

* User profiles
* CMS data
* E-commerce catalog
* Blog posts

---

# **4. Graph Databases (Neo4j, Amazon Neptune)**

### **Structure**

Nodes + Edges
Optimized for relationships.

### **Strengths**

* Fast graph traversal
* Relationship-heavy queries

### **Use Cases**

* Social networks
* Fraud detection
* Recommendation engines
* Knowledge graphs

---

# --------------------------------------------------------------

# **5.3 DATABASE SCALING**

Scaling is crucial for modern distributed systems.

---

# **1. Replication**

Replication ensures data is copied to multiple nodes.

### **Types**

---

### **A. Synchronous Replication**

* Write is confirmed **only when all replicas write the data**
* Strong consistency
* Slower

**Use Case:** Financial systems

---

### **B. Asynchronous Replication**

* Primary writes → acknowledgment
* Replicas update later
* Eventual consistency
* Risk of data loss if primary fails

**Use Case:** High throughput systems

---

# --------------------------------------------------------------

# **2. Sharding (Horizontal Partitioning)**

Split a table/collection into *shards* across multiple machines.

Improves:

* Read scalability
* Write scalability
* Storage capacity

---

## **A. Hash-based Sharding**

Key is hashed → decides shard.

```
shard = hash(user_id) % N
```

### **Pros**

* Uniform distribution
* Easy to implement

### **Cons**

* Re-sharding is expensive
* Range queries are difficult

---

## **B. Range-based Sharding**

Ranges define shards:

```
Shard 1: 1–1M  
Shard 2: 1M–2M  
```

### **Pros**

* Good for range queries
* Simple

### **Cons**

* Hot partitions: eg. new users → last shard only
* Imbalanced distribution

---

## **C. Directory-based Sharding**

Lookup table maps keys → shard.

```
UserID: 123 → Shard-7
```

### **Pros**

* Most flexible
* Easy rebalancing

### **Cons**

* Directory becomes bottleneck
* Extra lookup hop

---

# **Partition Tolerance (CAP Theorem)**

CAP states you can only get **2 out of 3**:

* **Consistency**
* **Availability**
* **Partition Tolerance**

Modern distributed systems always must have **Partition Tolerance**.
Thus tradeoff is between **Consistency vs Availability**.

---

# --------------------------------------------------------------

# **Read/Write Splitting**

* Master node handles **writes**
* Replica nodes handle **reads**

### **Advantages**

* Massive read scalability
* Works well with caching

### **Challenges**

* Replica lag (stale reads)
* Strong consistency difficult

---

# --------------------------------------------------------------

# **Hot Partitions**

A single shard/node gets disproportionate traffic.

### **Causes**

* Range-based sharding (new data goes to one shard)
* Hashed poorly
* Non-uniform access (celebrity user, trending topic)

### **Solutions**

* Better hashing
* Consistent hashing
* Splitting hot shards
* Using compound keys
* Adding caching layers

---

# --------------------------------------------------------------

# **5.4 DATABASE INDEXING (CRITICAL INTERVIEW TOPIC)**

Indexes reduce query time drastically.

---

# **1. B-Tree Index (used in SQL)**

* Balanced tree
* Supports range queries, sorting, ordering
* Default index type in relational DBs

### **Complexity**

* Lookup: O(log n)
* Insert: O(log n)

### **Best For**

* WHERE age > 30
* ORDER BY
* JOIN operations

---

# **2. Hash Index**

* Uses hash table internally
* O(1) lookup
* No ordering
* No range queries

### **Best For**

* Equality lookups:
  `WHERE user_id = 123`

### **Worst For**

* Range queries

---

# **3. Composite Indexes**

Index on multiple columns:

```
INDEX (country, city)
```

### **Works only left-to-right prefix**

If composite index is `(A, B, C)`:

* Query `A` — index used
* Query `A & B` — index used
* Query `B & C` — **index NOT used**

---

# **4. Query Optimization Basics**

### **A. Avoid Full Table Scans**

Use:

* Indexes
* LIMIT clauses
* Narrow WHERE conditions

---

### **B. Avoid SELECT * **

Only fetch needed columns.

---

### **C. Use covering indexes**

Index that contains all columns used in the query.

---

### **D. Avoid functions on indexed columns**

Query:

```
WHERE LOWER(name) = 'john'
```

Index won’t be used.

---

### **E. Denormalize for read-heavy workloads**

(Stored computed columns, materialized views, caches)

---

# --------------------------------------------------------------
