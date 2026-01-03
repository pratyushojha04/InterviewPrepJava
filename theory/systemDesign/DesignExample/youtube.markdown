Great — let’s design **YouTube / Large-Scale Video Streaming Platform** end-to-end.

We’ll cover:

✔ Problem + requirements
✔ **HLD — high-level architecture + diagram**
✔ Upload → transcode → store → distribute (CDN)
✔ Streaming (Range requests, adaptive bitrate — HLS/DASH)
✔ **LLD — UML models + DB schema + APIs**
✔ Sequence flows
✔ **Spring Boot code** (video upload + streaming with HTTP Range)
✔ Scaling + trade-offs

---

# 🎥 Problem — What does YouTube need to do?

When a user uploads a video:

> It should be processed, stored, and playable worldwide with low latency.

When a viewer plays a video:

> Streaming must be smooth — adaptive to network conditions.

---

# 1️⃣ Requirements

## Functional

* Upload videos
* Process/convert videos into multiple qualities
* Store and stream videos
* Resume playback, seek, buffer
* Recommendations & search
* Likes, comments, views
* Thumbnails, metadata

## Non-functional

* Massive scale (billions of views)
* Low latency streaming
* High availability
* Global distribution
* Fault-tolerant pipeline

---

# 2️⃣ HIGH-LEVEL DESIGN (HLD)

Think in **pipelines**:

1️⃣ upload
2️⃣ transcode
3️⃣ store
4️⃣ distribute
5️⃣ stream

---

## 🏗 Architecture Diagram

```
          ┌─────────────┐
          │    Client   │
          └──────┬──────┘
                 |
            API Gateway
                 |
        ┌────────▼────────┐
        │  Upload Service │
        └--------┬--------┘
                 |
          Object Storage (raw)
                 |
            Message Queue
                 |
        ┌────────▼────────┐
        │ Transcode Workers│  (FFmpeg)
        └--------┬--------┘
                 |
     Store multiple renditions (144p..4K)
                 |
            Metadata DB
                 |
        ┌────────▼────────┐
        │   CDN + Edge    │  (global distribution)
        └--------┬--------┘
                 |
        ┌────────▼────────┐
        │  Streaming API  │ (serves playlists/chunks)
        └─────────────────┘
```

---

# 3️⃣ Video Processing Pipeline

## Step 1 — Upload

Raw video saved to **object storage** (S3 / GCS / MinIO).

## Step 2 — Transcoding

Convert to formats & resolutions (H.264/H.265):

```
144p, 240p, 360p, 480p, 720p, 1080p, 4K
```

Create **chunks** + manifest:

* HLS (.m3u8 playlist + .ts chunks)
* or MPEG-DASH

Workers run FFmpeg.

---

## Step 3 — Store & Distribute

Processed files are stored and pushed to:

✔ Object storage
✔ CDN edge nodes

---

## Step 4 — Streaming

Player fetches:

```
playlist.m3u8
 -> segment.ts
 -> next segment.ts
 -> ...
```

Player auto-switches quality based on bandwidth (**adaptive bitrate**).

---

# 4️⃣ DATA MODEL (LLD)

## UML (simplified)

```
+-----------+
|   User    |
+-----------+
| id        |
| name      |
| email     |
+-----------+

+-----------+
|   Video   |
+-----------+
| id        |
| ownerId   |
| title     |
| description|
| duration  |
| status    |(PROCESSING/READY)
| url       |(playlist)
| createdAt |
+-----------+

+-------------+
| Rendition   |
+-------------+
| id          |
| videoId     |
| quality     | (360p/720p/1080p)
| url         |
+-------------+

+------------+
| Engagement |
+------------+
| videoId    |
| likes      |
| views      |
+------------+
```

---

# DB choices

| Use               | DB                   |
| ----------------- | -------------------- |
| Video metadata    | SQL / NoSQL          |
| Video files       | Object storage       |
| Views, engagement | Kafka → analytics DB |
| Search            | Elasticsearch        |

---

# 5️⃣ APIs

### Upload request (metadata first)

```
POST /videos
```

Response returns upload URL (presigned).

### Stream video

```
GET /videos/{id}/play
```

Returns playlist.

---

# 6️⃣ Sequence Flows

## 📤 Upload

```
Client -> Upload Service -> Storage
                  |
                  -> Queue -> Transcoder
                                 |
                                 -> Storage (processed)
                                 -> DB update (READY)
```

## ▶ Playback

```
Client -> Streaming API -> playlist.m3u8
Client -> CDN -> chunks.ts
```

---

# 7️⃣ SPRING BOOT — CORE STREAMING CODE

We’ll build:

✔ upload endpoint (store file)
✔ video streaming with **HTTP Range headers** (seek support)

---

## 📌 Entity

```java
@Entity
public class Video {
    @Id @GeneratedValue
    Long id;

    String title;
    String path;      // path to stored video
    String status;
    Instant createdAt;
}
```

---

## 📌 Repository

```java
public interface VideoRepository extends JpaRepository<Video, Long> {}
```

---

## 📌 Upload Controller

```java
@RestController
@RequestMapping("/videos")
public class UploadController {

    private final VideoRepository repo;

    public UploadController(VideoRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/upload")
    public Video upload(@RequestParam MultipartFile file) throws Exception {

        Path path = Paths.get("/videos/" + file.getOriginalFilename());
        Files.copy(file.getInputStream(), path);

        Video v = new Video();
        v.setTitle(file.getOriginalFilename());
        v.setPath(path.toString());
        v.setStatus("READY");
        v.setCreatedAt(Instant.now());

        return repo.save(v);
    }
}
```

---

## ▶ Streaming Controller (supports seeking)

```java
@RestController
@RequestMapping("/stream")
public class StreamController {

    @GetMapping("/{file}")
    public ResponseEntity<Resource> stream(
            @PathVariable String file,
            @RequestHeader(value = "Range", required = false) String range
    ) throws Exception {

        File video = new File("/videos/" + file);
        long fileLength = video.length();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "video/mp4");

        if (range == null) {
            Resource resource = new FileSystemResource(video);
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(fileLength)
                    .body(resource);
        }

        long start = Long.parseLong(range.replace("bytes=", "").split("-")[0]);
        long end = Math.min(start + 1_000_000, fileLength - 1);

        RandomAccessFile raf = new RandomAccessFile(video, "r");
        raf.seek(start);
        byte[] buffer = raf.readNBytes((int)(end - start + 1));

        headers.add("Content-Range", "bytes " + start + "-" + end + "/" + fileLength);

        return ResponseEntity.status(206)
                .headers(headers)
                .contentLength(buffer.length)
                .body(new ByteArrayResource(buffer));
    }
}
```

👉 This supports:

✔ streaming
✔ resume
✔ seek bar
✔ buffering

Real systems replace file path with CDN URLs.

---

# 8️⃣ CDN + Edge Strategy

Clients stream from nearest edge location:

```
Origin storage -> CDN edge servers -> Viewer
```

Benefits:

✔ lower latency
✔ cache popular videos
✔ offload backend

---

# 9️⃣ Fault Tolerance & Scaling

| Problem                 | Solution                          |
| ----------------------- | --------------------------------- |
| Transcoding slow        | distributed workers + autoscaling |
| Popular videos          | CDN + caching tiers               |
| Huge uploads            | presigned URLs + chunk uploads    |
| Metadata high read load | caching + replicas                |
| Global users            | geo-distributed CDNs              |

---

# 🔐 DRM & Access (optional)

Premium content → signed URLs with short TTL.

---

# 🧠 Interview quotes you can use

> “Video systems are built around pipelines — upload, transcode, store, distribute.”

> “Streaming uses Range requests + HLS/DASH chunking.”

> “CDNs are mandatory for global low-latency playback.”

> “We decouple processing using queues and worker pools.”

---
