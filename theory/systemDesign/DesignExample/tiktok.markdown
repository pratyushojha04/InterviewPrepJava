Great — let’s design **Instagram / TikTok Feed** end-to-end.

We’ll build it like interviewers expect:

✔ Concept — what problem we are solving
✔ **HLD — scalable architecture + diagram**
✔ Feed ranking concepts (very important)
✔ **LLD — UML models, APIs, DB schema**
✔ Flow diagrams
✔ **Working Spring Boot code (feed service)**
✔ Trade-offs & scaling

---

# 🌍 Problem — Instagram / TikTok Feed

A user opens the app and sees:

* posts from people they follow
* recommended content
* ranked by relevance
* infinite scroll + pagination
* fast — under ~100 ms

TikTok differs slightly:

> Feed is mostly **recommendations**, not follow-based.

We'll design to support both.

---

# 1️⃣ Requirements

## Functional

* View personalized feed
* Infinite scroll
* Likes, comments, shares
* Upload posts (image/video)
* Recommended content
* Cache for fast loading

## Non-functional

* Low latency
* Massive scale
* Fault tolerant
* High write throughput (uploads)
* Extreme read throughput (feeds)

---

# 2️⃣ High-Level Architecture (HLD)

Think:

> **Content creation pipeline + Feed generation + Ranking**

---

## 🔷 Architecture Diagram

```
        ┌──────────┐
        │  Clients │
        └────┬─────┘
             |
         API Gateway
             |
   ┌─────────▼─────────┐
   │    Feed Service    │  <-- generates personalized feed
   └─────────┬─────────┘
             |
   ┌─────────▼─────────┐
   │  Feed Cache (Redis)│ <-- low-latency feed
   └─────────┬─────────┘
             |
   ┌─────────▼─────────┐
   │  Feed Generator    │ <-- precomputes feeds / hybrid
   └─────────┬─────────┘
             |
   ┌─────────▼──────────┐
   │  Content Service    │ <-- posts, captions, metadata
   └─────────┬──────────┘
             |
   ┌─────────▼──────────┐
   │  Media Storage (S3) │ <-- images & videos
   └─────────┬──────────┘
             |
   ┌─────────▼──────────┐
   │ Engagement Service  │ <-- likes, comments, views
   └─────────┬──────────┘
             |
       Message Broker
             |
   ┌─────────▼──────────┐
   │ Recommendation/ML  │ <-- ranking model
   └────────────────────┘

User Graph DB → followers / following relationships
```

---

# 3️⃣ Feed Strategies (Critical interview topic)

## Strategy A — **Fan-out on write** (Instagram style)

When a user posts:

```
Push post into followers' feed
```

✔ Fast read
❌ Expensive for users with millions of followers

---

## Strategy B — **Fan-out on read** (TikTok style)

When user opens feed:

```
Generate feed dynamically
```

✔ Powerful recommendations
❌ More backend compute

---

### Real systems use **Hybrid**

```
Friends → precomputed feed
Recommendations → pull on demand
```

---

# 4️⃣ Data Model (LLD)

## 📌 UML (simplified)

```
+-----------+        +-----------+
|   User    |        |  Follow   |
+-----------+        +-----------+
| id        |<------>| followerId|
| name      |        | followeeId|
+-----------+        +-----------+

+-----------+
|   Post    |
+-----------+
| id        |
| userId    |
| mediaUrl  |
| caption   |
| createdAt |
+-----------+

+-----------+
| Engagement|
+-----------+
| postId    |
| likes     |
| comments  |
| views     |
+-----------+

+-----------+
|   Feed    |
+-----------+
| userId    |
| postIds[] |
+-----------+
```

---

# 5️⃣ Database Choices

| Component         | DB                             |
| ----------------- | ------------------------------ |
| Posts             | NoSQL (Cassandra/Mongo)        |
| Feed cache        | Redis                          |
| Followers graph   | Neo4j / RocksDB / Redis        |
| Engagement events | Kafka + warehouse (ClickHouse) |
| Media             | Object storage (S3 / MinIO)    |

---

# 6️⃣ Feed Ranking (Interview GOLD)

Ranking formula example:

```
score = 
  w1 * recency +
  w2 * likeRate +
  w3 * dwellTime +
  w4 * similarityToUser +
  w5 * creatorAffinity
```

TikTok heavily weighs **watch time** & **replay rate**.

---

# 🔁 Sequence Flows

## 1️⃣ Create Post

```
User -> Content Service -> DB
                      -> feed generator
                      -> push to followers' feed (Redis lists)
```

## 2️⃣ Get Feed

```
Client -> Feed Service
         -> read from Redis feed list
         -> fallback DB + recompute
```

## 3️⃣ Engagement

```
User -> Engagement Service -> Kafka
                           -> ML ranking updates
```

---

# 7️⃣ APIs (LLD)

### POST /posts

Upload metadata (media already uploaded):

```json
{
  "userId": "u1",
  "mediaUrl": "...",
  "caption": "hello"
}
```

### GET /feed?userId=u1&page=1

Returns ranked feed.

---

# 🧑‍💻 8️⃣ SPRING BOOT — FEED SERVICE (Working Example)

## 📌 Entities

```java
@Document("posts")
public class Post {
    @Id String id;
    String userId;
    String mediaUrl;
    String caption;
    Instant createdAt;
}
```

Feed entry cache structure:

```java
public record FeedItem(String postId, double score) {}
```

---

## 📌 Repository

```java
public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByIdIn(List<String> ids);
}
```

---

## 📌 Redis Feed Cache

```java
@Service
public class FeedCache {

    private final StringRedisTemplate redis;

    public FeedCache(StringRedisTemplate redis) {
        this.redis = redis;
    }

    public List<String> getFeed(String userId, int start, int end) {
        return redis.opsForList()
                .range("feed:" + userId, start, end);
    }

    public void pushToFeeds(List<String> followerIds, String postId) {
        followerIds.forEach(f -> 
            redis.opsForList().leftPush("feed:" + f, postId)
        );
    }
}
```

---

## 📌 Feed Service (ranking + fetch)

```java
@Service
public class FeedService {

    private final FeedCache cache;
    private final PostRepository repo;

    public FeedService(FeedCache cache, PostRepository repo) {
        this.cache = cache;
        this.repo = repo;
    }

    public List<Post> getFeed(String userId, int page) {
        int start = page * 20;
        int end = start + 19;

        List<String> ids = cache.getFeed(userId, start, end);

        if (ids == null || ids.isEmpty())
            return List.of();

        return repo.findByIdIn(ids);
    }
}
```

---

## 📌 Controller

```java
@RestController
@RequestMapping("/feed")
public class FeedController {

    private final FeedService service;

    public FeedController(FeedService service) {
        this.service = service;
    }

    @GetMapping
    public List<Post> feed(@RequestParam String userId,
                           @RequestParam(defaultValue = "0") int page) {
        return service.getFeed(userId, page);
    }
}
```

---

# 🖼 Media Upload Pipeline (Same as WhatsApp earlier)

Upload through presigned URL → stored in S3 → CDN served.

---

# ⚙️ Scaling Techniques (Very Important)

| Problem                           | Solution                    |
| --------------------------------- | --------------------------- |
| Hot users (millions of followers) | async fan-out + batching    |
| Cold start feed                   | recommendations on demand   |
| Slow DB reads                     | Redis + feed precomputation |
| Ranking expensive                 | ML service + feature store  |
| Large media                       | Object storage + CDN        |
| Feed consistency                  | append-only + timestamps    |

---

# 🧠 Interview Talking Points

Use these lines:

> “Instagram uses fan-out-on-write for friends and TikTok uses fan-out-on-read for recommendations — real systems combine both.”

> “Feed is cached in Redis and rebuilt asynchronously to minimize latency.”

> “Engagement events stream into Kafka where ML continuously improves ranking.”

> “We shard posts and partition Kafka by authorId/conversationId for ordering.”

---

# 🎯 What I can add next if you want:

✔ full microservice repo layout
✔ Kafka-based async feed builder
✔ Like/comment service with code
✔ Recommendation model simulation
✔ Pagination + infinite scroll optimization
✔ Redis cluster + sharding explanation

Just say **“continue”** and what area to expand — and I’ll build the next layer step-by-step.
