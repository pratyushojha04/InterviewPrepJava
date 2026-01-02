
---

# **📘 High-Level System Design – Complete Syllabus**

---

# **1. Fundamentals of System Design**

### **1.1 Introduction to System Design**

* What is High-Level Architecture
* Horizontal vs Vertical Scaling
* Monolithic vs Microservices Architecture
* Request flow fundamentals
* Understanding Latency & Throughput
* CAP Theorem
* ACID vs BASE Properties

### **1.2 Requirements Engineering**

* Functional Requirements
* Non-Functional Requirements:

  * Scalability
  * Availability
  * Reliability
  * Consistency models
  * Durability
  * Latency & Response Time
  * Fault Tolerance
  * Observability

---

# **2. Networking & Communication Basics**

### **2.1 Networking Concepts**

* DNS, DHCP
* IP, TCP, UDP
* Load Balancers (L4, L7)
* CDNs
* Reverse Proxy vs Forward Proxy

### **2.2 Communication Patterns**

* Synchronous vs Asynchronous
* REST API basics
* WebSockets
* gRPC
* Long Polling vs Server Sent Events
* RPC fundamentals

---

# **3. Scalability Concepts**

### **3.1 Vertical & Horizontal Scaling**

* Stateless vs Stateful Services
* Scaling strategies for compute, storage, cache

### **3.2 Load Balancing**

* Types: Round Robin, Least Connections, IP Hash
* Global Load Balancing (Geo-aware)
* Health checks
* Failover strategies

---

# **4. Caching**

### **4.1 Caching Basics**

* What to cache?
* Cache Placement:

  * Client-side cache
  * CDN
  * Reverse proxy caches
  * Distributed cache (Redis/Memcached)

### **4.2 Cache Problems**

* Cache Eviction Policies (LRU, LFU, FIFO)
* Cache Miss & Cache Stampede
* Write strategies:

  * Write-through
  * Write-back
  * Write-around
* Consistency and invalidation

---

# **5. Databases in System Design**

### **5.1 SQL vs NoSQL**

* When to use what
* ACID vs BASE
* Transaction isolation levels

### **5.2 NoSQL Database Types**

* Key-value stores (Redis, DynamoDB)
* Column stores (Cassandra, Bigtable)
* Document stores (MongoDB)
* Graph databases (Neo4j)

### **5.3 Database Scaling**

* Replication (Sync/Async)
* Sharding:

  * Hash-based
  * Range-based
  * Directory-based
* Partition tolerance
* Read/Write Splitting
* Hot partitions

### **5.4 Database Indexing**

* B-Trees vs Hash Indexing
* Composite Indexes
* Query optimization basics

---

# **6. Message Queues & Streaming**

### **6.1 Message Brokers**

* Kafka
* RabbitMQ
* SQS
* Pub/Sub Model
* Push vs Pull Consumers

### **6.2 Use Cases**

* Decoupling services
* Event-driven architecture
* Background processing

### **6.3 Stream Processing**

* Kafka Streams
* Flink
* Data pipelines

---

# **7. System Architecture Patterns**

### **7.1 Architectural Styles**

* Microservices
* Serverless architecture
* Event-driven systems
* CQRS (Command Query Responsibility Segregation)
* Event Sourcing
* SOA

### **7.2 Reliability Patterns**

* Retry patterns
* Circuit breaker
* Bulkhead pattern
* Graceful degradation
* Timeouts & backoff

---

# **8. Distributed Systems Concepts**

### **8.1 Distributed Fundamentals**

* Distributed Consensus (Paxos, Raft)
* Two-Phase Commit (2PC)
* Leader Election
* Vector Clocks
* Gossip Protocol

### **8.2 Consistency Models**

* Strong consistency
* Eventual consistency
* Causal consistency
* Read-your-own-writes

---

# **9. Observability**

### **9.1 Logging**

* Centralized logging
* Log rotation
* Log indexing

### **9.2 Metrics**

* Prometheus
* Grafana
* Golden signals: LATENCY, TRAFFIC, ERRORS, SATURATION

### **9.3 Tracing**

* Distributed tracing (Jaeger, Zipkin)
* Trace IDs, spans

---

# **10. Storage Systems & File Handling**

### **10.1 Distributed File Systems**

* HDFS
* Object Storage (S3, GCS, MinIO)
* Block Storage vs Object Storage

### **10.2 Data Replication, Durability**

* RAID levels
* Erasure coding

---

# **11. High Availability and Fault Tolerance**

### **11.1 Fault Tolerance Strategies**

* Replication
* Redundancy
* Failover
* Multi-region deployments

### **11.2 Disaster Recovery**

* RPO & RTO
* Active-active systems
* Active-passive systems

---

# **12. Security in System Design**

### **12.1 Application Security**

* Authentication & Authorization
* OAuth 2.0
* JWT
* API Gateway security

### **12.2 Network Security**

* Firewalls
* DDoS protection
* Rate limiting
* WAF (Web Application Firewall)

---

# **13. API Design**

### **13.1 REST API Best Practices**

* Pagination
* Rate limiting
* Idempotency
* Versioning

### **13.2 GraphQL**

* Basic structure
* Use cases

---

# **14. Cloud Computing Concepts**

### **14.1 Cloud Models**

* IaaS / PaaS / SaaS
* On-demand compute vs reserved
* Auto-scaling groups

### **14.2 Cloud-Native Design**

* Kubernetes basics
* Containers & Docker
* Service mesh (Istio)

---

# **15. Design Workflow (For Interview or Real System)**

### **15.1 Step-by-Step Approach**

1. Clarify requirements
2. Define APIs
3. Identify major components
4. Choose storage
5. Caching strategy
6. Scaling strategy
7. Detailed architecture
8. Identify bottlenecks
9. Consider failure points
10. Compute cost estimates (optional)

---

# **16. Popular System Design Case Studies**

You should design each of these:

* URL Shortener (TinyURL)
* WhatsApp / Messenger Chat System
* Instagram / TikTok Feed
* YouTube / Video Streaming
* Uber / Ola Architecture
* E-Commerce System (Amazon)
* Payment Gateway (UPI-like)
* Notification System
* Logging system
* Real-time Analytics system
* Food Delivery App (Zomato, Swiggy)

---

# **17. Mock Interview Practice**

* How to explain your architecture
* How to draw diagrams
* How to estimate scale (QPS, storage, bandwidth)
* Trade-offs explanation

---

# **18. Final Revision Topics (Frequently Asked Interview Questions)**

* Difference between SQL & NoSQL
* CAP Theorem (real meaning)
* Sharding strategies
* How load balancer works internally
* Strong vs eventual consistency
* Kafka internals
* Caching issues & solutions
* Rate limiting algorithms (Token Bucket, Leaky Bucket)
* API Gateway vs Load Balancer
* Designing idempotent APIs
* Backpressure handling

---

