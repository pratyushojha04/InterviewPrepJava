Great choice — **URL Shortener (like TinyURL)** is one of the most common — and powerful — system-design interview questions.

I’ll give you:

✔ Concept
✔ Functional + non-functional requirements
✔ **HLD (High-Level Design)** — architecture, components, scaling
✔ **LLD (Low-Level Design)** — DB schema, APIs, algorithms
✔ Flowcharts & diagrams (easy to redraw)
✔ Java + Spring Boot sample implementation
✔ Trade-offs & interview discussions

---

# 🌍 Problem: Design TinyURL

Input:

```
https://www.amazon.com/a-very-long-link/.......
```

Output:

```
https://tiny.url/abc123
```

When user visits `tiny.url/abc123`, they should be redirected to original long URL.

---

# 1️⃣ Requirements

## ✅ Functional Requirements

1️⃣ Shorten long URLs
2️⃣ Redirect when short URL is accessed
3️⃣ Custom alias (optional)
4️⃣ Expiry support (optional)
5️⃣ Track basic analytics (optional)

---

## ⚙ Non-Functional Requirements

* **Highly available**
* **Low latency**
* **Short URL must be unique**
* Handle **high read traffic**
* **Scalable**

---

# 2️⃣ High-Level Design (HLD)

### Main Components:

```
Client
  |
API Gateway
  |
URL Shortening Service
  |          |
 DB        Cache (Redis)
  |
Analytics (optional)
```

---

## 🔁 High-Level Flow (Shortening)

```
Client -> POST /shorten
        |
        v
Generate short key ---> store (short -> long)
        |
return short URL
```

---

## 🔁 Flow (Redirection)

```
User hits /abc123
      |
Check cache?
      |
Found -> redirect
      |
Else query DB -> save to cache -> redirect
```

---

## 🏗️ Tech choices

| Component     | Choice                              |
| ------------- | ----------------------------------- |
| Backend       | Spring Boot                         |
| DB            | MySQL / PostgreSQL (or NoSQL later) |
| Cache         | Redis                               |
| Load Balancer | Nginx                               |
| Reverse Proxy | CDN optional                        |

---

# 3️⃣ LOW-LEVEL DESIGN (LLD)

## 3.1 Database Schema

### Option 1 — Relational (simple)

Table: **url_mapping**

| Field      | Type      | Meaning        |
| ---------- | --------- | -------------- |
| id         | BIGINT PK | Auto increment |
| short_key  | VARCHAR   | Unique         |
| long_url   | TEXT      | Original URL   |
| created_at | DATETIME  | created        |
| expiry_at  | DATETIME  | nullable       |

Indexes:

```
UNIQUE(short_key)
INDEX(long_url)
```

---

## 3.2 Key Generation Strategies

### 🔹 Option 1: Base62 Encoding (Most common)

Auto-increment ID:

```
1 → "a"
2 → "b"
62 -> "10"
```

Short key = Base62(ID)

Pros:
✔ simple
✔ collision free

---

### 🔹 Option 2: Hashing (MD5/SHA)

```
hash(long_url) -> take first 7 chars
```

Must handle collisions via retries.

---

### 🔹 Option 3: Random Key (High traffic systems)

Random 7-char key, check uniqueness.

---

# 🔥 API Design

### POST /shorten

Request:

```json
{
  "longUrl": "https://google.com/test"
}
```

Response:

```json
{
  "shortUrl": "https://tiny.url/abc123"
}
```

---

### GET /{shortKey}

Redirect to original link.

---

# 📊 Flowcharts (LLD)

### Shortening Flow

```
[Receive long URL]
       |
Check if already shortened?
       |
 Save if new -> Generate key -> Store
       |
Return short URL
```

---

### Redirect Flow

```
[User click short URL]
      |
Check Redis cache
      |
Found? -> redirect
      |
Else DB lookup -> update cache -> redirect
```

---

# 🧑‍💻 SPRING BOOT IMPLEMENTATION (Example)

## 📌 Entity

```java
@Entity
@Table(name = "url_mapping")
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String shortKey;

    @Column(nullable = false, length = 2000)
    private String longUrl;

    private LocalDateTime createdAt;
    private LocalDateTime expiryAt;
}
```

---

## 📌 Repository

```java
public interface UrlRepository extends JpaRepository<UrlMapping, Long> {

    Optional<UrlMapping> findByShortKey(String shortKey);

    Optional<UrlMapping> findByLongUrl(String longUrl);
}
```

---

## 📌 Base62 Encoder

```java
public class Base62Util {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String encode(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(ALPHABET.charAt((int)(num % 62)));
            num /= 62;
        }
        return sb.reverse().toString();
    }
}
```

---

## 📌 Service

```java
@Service
public class UrlService {

    private final UrlRepository repo;

    public UrlService(UrlRepository repo) {
        this.repo = repo;
    }

    public String shorten(String longUrl) {
        Optional<UrlMapping> existing = repo.findByLongUrl(longUrl);
        if (existing.isPresent()) return existing.get().getShortKey();

        UrlMapping mapping = new UrlMapping();
        mapping.setLongUrl(longUrl);
        mapping.setCreatedAt(LocalDateTime.now());

        UrlMapping saved = repo.save(mapping);

        String shortKey = Base62Util.encode(saved.getId());
        saved.setShortKey(shortKey);
        repo.save(saved);

        return shortKey;
    }

    public String resolve(String shortKey) {
        return repo.findByShortKey(shortKey)
                .map(UrlMapping::getLongUrl)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }
}
```

---

## 📌 Controller

```java
@RestController
public class UrlController {

    private final UrlService service;

    public UrlController(UrlService service) {
        this.service = service;
    }

    @PostMapping("/shorten")
    public String shorten(@RequestBody Map<String, String> req) {
        String shortKey = service.shorten(req.get("longUrl"));
        return "https://tiny.url/" + shortKey;
    }

    @GetMapping("/{key}")
    public ResponseEntity<Void> redirect(@PathVariable String key) {
        String longUrl = service.resolve(key);
        return ResponseEntity.status(302)
                .header("Location", longUrl)
                .build();
    }
}
```

---

# 🚀 SCALING THE SYSTEM (HLD Enhancements)

### 1️⃣ Cache frequently used URLs

```
Short URL lookup -> Redis -> fallback DB
```

### 2️⃣ Sharding database

Partition based on shortKey prefix.

### 3️⃣ Replication for read load

Primary → replica read DBs.

### 4️⃣ Rate limiting (avoid abuse)

Store IP & limit requests per minute.

### 5️⃣ Analytics queue (async)

Clicks written to Kafka → analytics service later.

---

# ⚠ Important Interview Points

### ❓ How do we avoid collisions?

* Base62 → no collision
* Random key → retry on collision
* Hashing → maintain collision detection

### ❓ What happens if DB grows huge?

Move to NoSQL (Cassandra, DynamoDB) for key-value storage.

### ❓ What about custom URLs?

Store user-provided alias in `short_key`.

---

# 🧠 QUICK SUMMARY

| Layer     | Design                                 |
| --------- | -------------------------------------- |
| Frontend  | calls API                              |
| Backend   | Spring Boot microservice               |
| Storage   | MySQL initially → scalable NoSQL later |
| Cache     | Redis                                  |
| Algorithm | Base62 encoding ID                     |
| Scaling   | Replicas, caching, sharding            |

---

If you'd like, next I can provide:

1️⃣ **Sequence diagrams**
2️⃣ **Docker container + full project setup**
3️⃣ **URL expiry / analytics implementation**
4️⃣ **Advanced scaling (CDN + load balancer + cache eviction)**

Just tell me — and we’ll keep building 👨‍💻
