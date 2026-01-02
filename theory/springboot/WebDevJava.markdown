Here is the **complete, well-structured table of all important HTTP status codes** categorized from **1xx to 5xx** — perfect for interviews, notes, and revision.

---

# ✅ **HTTP STATUS CODES — FULL TABLE**

## **1xx — Informational Responses**

| Code    | Name                | Meaning                                                |
| ------- | ------------------- | ------------------------------------------------------ |
| **100** | Continue            | Client should continue sending the request body        |
| **101** | Switching Protocols | Server switching to another protocol (e.g., WebSocket) |
| **102** | Processing          | Server has accepted but is still processing (WebDAV)   |
| **103** | Early Hints         | Used with Link headers for preload resources           |

---

## **2xx — Success Responses**

| Code    | Name                          | Meaning                                                 |
| ------- | ----------------------------- | ------------------------------------------------------- |
| **200** | OK                            | Standard success for GET/PUT/PATCH/DELETE               |
| **201** | Created                       | Resource created successfully (POST)                    |
| **202** | Accepted                      | Request accepted but processing is pending (async jobs) |
| **203** | Non-Authoritative Information | Response modified by a proxy                            |
| **204** | No Content                    | Success but no response body (DELETE/PUT)               |
| **205** | Reset Content                 | Client should reset the document view                   |
| **206** | Partial Content               | Used in range-based downloads                           |
| **207** | Multi-Status                  | Multiple status codes (WebDAV)                          |
| **208** | Already Reported              | WebDAV; avoids repeated responses                       |
| **226** | IM Used                       | Delta encoding responses                                |

---

## **3xx — Redirection Responses**

| Code    | Name               | Meaning                                          |
| ------- | ------------------ | ------------------------------------------------ |
| **300** | Multiple Choices   | Resource has several representations             |
| **301** | Moved Permanently  | Permanent redirect (SEO-friendly)                |
| **302** | Found              | Temporary redirect                               |
| **303** | See Other          | Redirect to another URI using GET                |
| **304** | Not Modified       | Cached version is valid                          |
| **305** | Use Proxy          | Deprecated                                       |
| **306** | Switch Proxy       | No longer used                                   |
| **307** | Temporary Redirect | Same method redirect; modern replacement for 302 |
| **308** | Permanent Redirect | Permanent version of 307                         |

---

## **4xx — Client Error Responses**

| Code    | Name                            | Meaning                                                 |
| ------- | ------------------------------- | ------------------------------------------------------- |
| **400** | Bad Request                     | Invalid syntax/body                                     |
| **401** | Unauthorized                    | Authentication required                                 |
| **402** | Payment Required                | Reserved (used by some APIs)                            |
| **403** | Forbidden                       | Authenticated but not allowed                           |
| **404** | Not Found                       | Resource doesn't exist                                  |
| **405** | Method Not Allowed              | HTTP method not supported                               |
| **406** | Not Acceptable                  | Server cannot return acceptable content type            |
| **407** | Proxy Authentication Required   | Authenticate with proxy                                 |
| **408** | Request Timeout                 | Client took too long                                    |
| **409** | Conflict                        | Resource conflict (duplicate entries, version conflict) |
| **410** | Gone                            | Resource permanently removed                            |
| **411** | Length Required                 | Content-Length header missing                           |
| **412** | Precondition Failed             | Conditional headers failed (ETag, If-Match)             |
| **413** | Payload Too Large               | Request body too large                                  |
| **414** | URI Too Long                    | URL too large                                           |
| **415** | Unsupported Media Type          | Server cannot accept this data format                   |
| **416** | Range Not Satisfiable           | Invalid range in partial downloads                      |
| **417** | Expectation Failed              | Expect header cannot be satisfied                       |
| **418** | I'm a Teapot                    | Joke status (RFC 2324)                                  |
| **421** | Misdirected Request             | Wrong server                                            |
| **422** | Unprocessable Entity            | Valid JSON, invalid data (common in APIs)               |
| **423** | Locked                          | Resource locked                                         |
| **424** | Failed Dependency               | Failure in earlier request                              |
| **425** | Too Early                       | Avoid premature replays                                 |
| **426** | Upgrade Required                | Client must switch protocols (HTTPS, HTTP/3)            |
| **428** | Precondition Required           | Missing conditional headers                             |
| **429** | Too Many Requests               | Rate limiting                                           |
| **431** | Request Header Fields Too Large | Headers too large                                       |
| **451** | Unavailable For Legal Reasons   | Blocked due to legal issue (court order)                |

---

## **5xx — Server Error Responses**

| Code    | Name                            | Meaning                                    |
| ------- | ------------------------------- | ------------------------------------------ |
| **500** | Internal Server Error           | Generic server failure                     |
| **501** | Not Implemented                 | Method not supported by server             |
| **502** | Bad Gateway                     | Invalid response from upstream server      |
| **503** | Service Unavailable             | Server overloaded or down for maintenance  |
| **504** | Gateway Timeout                 | Upstream server didn’t respond             |
| **505** | HTTP Version Not Supported      | Refuses HTTP version                       |
| **506** | Variant Also Negotiates         | Configuration loop                         |
| **507** | Insufficient Storage            | Server cannot store the representation     |
| **508** | Loop Detected                   | Infinite loop on server                    |
| **510** | Not Extended                    | Further extensions required                |
| **511** | Network Authentication Required | Client must authenticate to access network |

---

---

# ✅ **1. HTTP BASICS**

HTTP (HyperText Transfer Protocol) is a **stateless, request–response** protocol used for communication between clients (like browsers, Postman, mobile apps) and servers (APIs).

---

# **A. HTTP METHODS (CRUD Mapping + Deep Explanation)**

| HTTP Method | CRUD           | Purpose                   | Idempotent? | Safe? |
| ----------- | -------------- | ------------------------- | ----------- | ----- |
| **GET**     | Read           | Fetch resource(s)         | ✔ Yes       | ✔ Yes |
| **POST**    | Create         | Create new resource       | ✖ No        | ✖ No  |
| **PUT**     | Update/Replace | Replace entire resource   | ✔ Yes       | ✖ No  |
| **PATCH**   | Update/Modify  | Partially update resource | ✖ No        | ✖ No  |
| **DELETE**  | Delete         | Remove resource           | ✔ Yes       | ✖ No  |

### **1️⃣ GET**

* Used to **retrieve** data.
* Should **not change server state** ("safe").
* Should be **cacheable**.

### **2️⃣ POST**

* Used to **create** new resources.
* Not idempotent:
  Sending the same POST twice may create 2 entries.

### **3️⃣ PUT**

* Used to **replace** an entire resource.
* **Idempotent**: sending the same PUT request repeatedly yields the same final state.

### **4️⃣ PATCH**

* Optimized for **partial updates**.
* Not idempotent by definition, but can be implemented idempotently.

### **5️⃣ DELETE**

* Removes a resource.
* Idempotent: deleting something twice still results in “not present”.

---

# **B. HTTP STATUS CODES – COMPLETE BREAKDOWN**

## **1xx – Informational**

| Code                        | Meaning                                                     |
| --------------------------- | ----------------------------------------------------------- |
| **100 Continue**            | Server acknowledges request header; client should send body |
| **101 Switching Protocols** | WebSocket upgrades                                          |

---

## **2xx – Success**

| Code               | Meaning                                               |
| ------------------ | ----------------------------------------------------- |
| **200 OK**         | Successful GET/PUT/PATCH/DELETE                       |
| **201 Created**    | New resource created (POST)                           |
| **202 Accepted**   | Task accepted but not processed (async jobs)          |
| **204 No Content** | Successful request but no response body (DELETE, PUT) |

---

## **3xx – Redirection**

| Code                      | Meaning                    |
| ------------------------- | -------------------------- |
| **301 Moved Permanently** | Resource moved             |
| **302 Found**             | Temporary redirect         |
| **304 Not Modified**      | Client can use cached data |

---

## **4xx – Client Errors**

| Code                         | Meaning                                      |
| ---------------------------- | -------------------------------------------- |
| **400 Bad Request**          | Invalid syntax or parameters                 |
| **401 Unauthorized**         | Authentication required                      |
| **403 Forbidden**            | Authenticated but not allowed                |
| **404 Not Found**            | Resource doesn’t exist                       |
| **409 Conflict**             | Resource state conflict (duplicate entries)  |
| **422 Unprocessable Entity** | Semantic error (valid JSON but invalid data) |

---

## **5xx – Server Errors**

| Code                          | Meaning                           |
| ----------------------------- | --------------------------------- |
| **500 Internal Server Error** | Unexpected server crash           |
| **502 Bad Gateway**           | Reverse proxy communication error |
| **503 Service Unavailable**   | Server down/overloaded            |
| **504 Gateway Timeout**       | Timeout between services          |

---

# **C. HTTP HEADERS – EXPLAINED DEEPLY**

Headers are **metadata** about the request/response.

---

## **1️⃣ Request Headers**

### **Authorization**

```
Authorization: Bearer <token>
```

### **Content-Type**

Tells server what client is sending.

Example:

```
Content-Type: application/json
```

### **Accept**

Tells server what the client expects:

```
Accept: application/json
```

### **User-Agent**

Type of client (browser/app):

```
User-Agent: Mozilla/5.0
```

---

## **2️⃣ Response Headers**

### **Location**

Used with **201 Created** to specify new resource URI.

### **Cache-Control**

Controls caching:

```
Cache-Control: no-store
```

### **Set-Cookie**

Used for sessions:

```
Set-Cookie: sessionid=abc123;
```

---

# **2. REST PRINCIPLES (VERY IMPORTANT FOR INTERVIEWS)**

REST = **Representational State Transfer**
It is an architectural style using HTTP.

## **Six REST Constraints**

| Constraint                       | Explanation                                                                   |
| -------------------------------- | ----------------------------------------------------------------------------- |
| **1. Stateless**                 | Server stores no session state. Every request must contain all required info. |
| **2. Client–Server**             | Clear separation of UI (client) and data logic (server).                      |
| **3. Cacheable**                 | Responses must define caching rules.                                          |
| **4. Uniform Interface**         | Consistent URIs, methods, representations.                                    |
| **5. Layered System**            | Client cannot assume direct connection to server.                             |
| **6. Code on Demand (Optional)** | Server can send executable code (rare).                                       |

---

## **REST Resource Naming Rules**

| Action        | Endpoint Example              |
| ------------- | ----------------------------- |
| Get all users | `/api/users`                  |
| Get one user  | `/api/users/{id}`             |
| Create user   | `/api/users` (POST)           |
| Update user   | `/api/users/{id}` (PUT/PATCH) |
| Delete user   | `/api/users/{id}`             |

Guidelines:

* Use **nouns**, not verbs.
* Use plural collection names.
* Use nested resources for relationships:
  `/users/1/orders/5`

---

## **HTTP Method Mapping in REST**

| REST Operation | HTTP Method |
| -------------- | ----------- |
| Create         | POST        |
| Read           | GET         |
| Update         | PUT / PATCH |
| Delete         | DELETE      |

---

# 🚀 **3. PRACTICAL IMPLEMENTATION – SPRING BOOT REST API**

Let's implement a **User CRUD REST API** demonstrating methods + status codes + headers.

---

# **A. User Model**

```java
public class User {
    private Long id;
    private String name;
    private String email;

    // getters and setters
}
```

---

# **B. Controller with All HTTP Operations**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    private Map<Long, User> db = new ConcurrentHashMap<>();
    private AtomicLong idGen = new AtomicLong(1);

    // ---------------------- GET ALL ----------------------
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Cache-Control", "no-store")
                .body(new ArrayList<>(db.values()));
    }

    // ---------------------- GET ONE ----------------------
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        User user = db.get(id);
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
        return ResponseEntity.ok(user);
    }

    // ---------------------- CREATE (POST) ----------------------
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        long id = idGen.getAndIncrement();
        user.setId(id);
        db.put(id, user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/api/users/" + id)
                .body(user);
    }

    // ---------------------- UPDATE (PUT) ----------------------
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        if (!db.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        updatedUser.setId(id);
        db.put(id, updatedUser);

        return ResponseEntity.ok(updatedUser);
    }

    // ---------------------- DELETE ----------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        db.remove(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
```

---

# 🔥 **What This Example Demonstrates**

### ✔ GET returns **200 OK**

### ✔ POST returns **201 Created** + **Location header**

### ✔ PUT returns **200 OK** with updated resource

### ✔ DELETE returns **204 No Content**

### ✔ Headers like Cache-Control and Location

### ✔ Proper REST resource naming

---

# 📌 Additional Notes for Interviews

* REST is **not a protocol**, it uses HTTP but is an **architectural style**.
* Statelessness improves **scalability** because the server does not track sessions.
* Idempotency is critical for reliability in distributed systems.
* APIs must support proper **content negotiation** using `Accept` and `Content-Type`.

---
Below is a **deep, interview-ready explanation** of *Spring Framework basics*, covering **Dependency Injection**, **Spring configuration**, and **Spring Boot** — with **clear diagrams**, **real-world examples**, and **Java + Spring Boot code**.

---

# ✅ **1. What is the Spring Framework?**

Spring Framework is a **lightweight, modular, dependency-injection–based** Java framework for building enterprise applications.

Key goals:

* Reduce boilerplate code
* Loose coupling
* Easy testing
* Consistent programming model

The core of Spring is the **IoC (Inversion of Control) container**.

---

# 🚀 **2. Dependency Injection (DI)**

Dependency Injection is a design pattern where **objects do not create their own dependencies**. Instead, **dependencies are given to them by the Spring container**.

### Traditional Java (without DI ❌)

```java
public class StudentService {
    private StudentRepository repo = new StudentRepository(); // Tightly coupled
}
```

Problems:

* Hard to test
* Hard to replace implementations
* Violates SOLID principles

---

## ✔ With Spring DI

Spring creates the object and injects the dependency for you.

```java
@Service
public class StudentService {

    private final StudentRepository repo;

    @Autowired
    public StudentService(StudentRepository repo) {
        this.repo = repo;   // Injected by Spring
    }
}
```

### Benefits of DI

* Loose coupling
* Easy mocking/testing
* Flexible configurations
* Supports multiple injection types

---

## Types of Dependency Injection

| Type                      | Description                 | Example                            |
| ------------------------- | --------------------------- | ---------------------------------- |
| **Constructor Injection** | Inject via constructor      | Recommended (immutable, testable)  |
| **Setter Injection**      | Inject via setter methods   | Optional dependencies              |
| **Field Injection**       | Inject directly into fields | Not recommended (poor testability) |

### Constructor Injection Example

```java
@Service
public class OrderService {

    private final PaymentService paymentService;

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

### Setter Injection Example

```java
@Service
public class EmailService {

    private NotificationService notificationService;

    @Autowired
    public void setNotificationService(NotificationService service) {
        this.notificationService = service;
    }
}
```

### Field Injection (Avoid)

```java
@Autowired
private UserRepository repo;
```

---

# 🔥 **3. Spring Configuration Approaches**

Spring supports **three configuration styles**:

## **1️⃣ XML Configuration (old but still used sometimes)**

```xml
<bean id="studentService" class="com.example.StudentService" />
```

---

## **2️⃣ Java-Based Configuration (Modern)**

Using `@Configuration` + `@Bean`

```java
@Configuration
public class AppConfig {

    @Bean
    public UserService userService() {
        return new UserService();
    }
}
```

---

## **3️⃣ Annotation-Based Configuration (Most common)**

Spring automatically detects components using:

| Annotation                        | Meaning         |
| --------------------------------- | --------------- |
| `@Component`                      | Generic bean    |
| `@Service`                        | Business logic  |
| `@Repository`                     | DAO classes     |
| `@Controller` / `@RestController` | Web controllers |

Example:

```java
@Service
public class UserService { }
```

Spring detects them using:

```java
@ComponentScan("com.example")
```

---

# 🌱 **4. Spring Bean Lifecycle (Very Important)**

### Spring creates, manages, and destroys objects (beans):

1. Instantiate
2. Populate dependencies
3. Call lifecycle callbacks (`@PostConstruct`)
4. Bean is ready
5. On shutdown → `@PreDestroy`

Example:

```java
@Component
public class AppLogger {

    @PostConstruct
    public void init() {
        System.out.println("Logger initialized");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("Logger shutting down");
    }
}
```

---

# 🚀 **5. Spring Boot Basics (Super Important)**

Spring Boot is built on top of Spring Framework and aims to:

* Reduce configuration
* Start applications quickly
* Provide auto-configuration
* Provide embedded servers (Tomcat/Jetty)
* Production-ready features (Actuator)

---

## ⭐ What Spring Boot Provides

### 1️⃣ Auto Configuration

Spring Boot **detects your classes** and auto-configures beans.

Example: If you have Spring Data JPA dependency, Boot configures:

* EntityManager
* DataSource
* TransactionManager

---

### 2️⃣ Starter Dependencies

Pre-defined libraries bundled together.

Example:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

Includes:

* Tomcat server
* Spring MVC
* JSON serializer (Jackson)

---

### 3️⃣ Embedded Servers (No need to install Tomcat)

Just run:

```bash
mvn spring-boot:run
```

---

### 4️⃣ Application Configuration (`application.properties`)

```properties
server.port=8081
spring.datasource.url=jdbc:mysql://localhost:3306/testdb
```

---

# 🏗 **6. Spring Boot REST Example (Must Know)**

## **Model**

```java
public class User {
    private Long id;
    private String name;

    // getters & setters
}
```

---

## **Service**

```java
@Service
public class UserService {

    private Map<Long, User> users = new HashMap<>();
    private AtomicLong idGen = new AtomicLong(1);

    public User createUser(User user) {
        user.setId(idGen.getAndIncrement());
        users.put(user.getId(), user);
        return user;
    }

    public User getUser(Long id) {
        return users.get(id);
    }
}
```

---

## **Controller**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return service.createUser(user);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return service.getUser(id);
    }
}
```

---

## **Main Class**

```java
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

Run → Your REST API is live.

---

# 🔥 Interview-Level Comparisons

## **Spring vs Spring Boot**

| Feature       | Spring            | Spring Boot     |
| ------------- | ----------------- | --------------- |
| Configuration | Manual            | Auto-configured |
| Web server    | External Tomcat   | Embedded server |
| Dependencies  | Must specify many | Starters        |
| Setup         | Time-consuming    | Fast & easy     |
| Focus         | Flexibility       | Productivity    |

---

# 🧠 Important Concepts in Short

### ✔ IoC

Spring creates and manages objects.

### ✔ DI

Dependencies are injected, not created manually.

### ✔ Beans

Objects managed by Spring.

### ✔ ApplicationContext

Spring container that holds beans.

### ✔ Auto-Configuration

Boot configures common components automatically.

---
Below is a **deep, interview-focused explanation** of the requested topics:
**Spring Boot starters, properties, application.yml/properties, and building REST APIs with Spring MVC** using `@RestController` and `@RequestMapping`.
Includes **real-world examples + practical Java code**.

---

# ✅ **1. SPRING BOOT STARTERS**

Spring Boot starters are **pre-packaged dependency bundles** that eliminate the need to define multiple dependencies manually.

### ⭐ Why are starters used?

* Reduce dependency management
* Avoid version conflicts
* Auto-configure functionalities
* Quick project setup

---

## 🔥 **Common Spring Boot Starters**

| Starter                            | Purpose                         |
| ---------------------------------- | ------------------------------- |
| **spring-boot-starter-web**        | Spring MVC, REST, Tomcat server |
| **spring-boot-starter-data-jpa**   | JPA, Hibernate, Data access     |
| **spring-boot-starter-security**   | Authentication + authorization  |
| **spring-boot-starter-test**       | JUnit 5, Mockito, Spring Test   |
| **spring-boot-starter-validation** | Bean Validation (JSR 380)       |
| **spring-boot-starter-thymeleaf**  | Templates for web pages         |
| **spring-boot-starter-actuator**   | Monitoring, health checks       |

---

## ✔ Example (pom.xml)

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

Spring Boot automatically configures:

* `DispatcherServlet`
* JSON serialization with Jackson
* Tomcat embedded server
* Exception handlers

You don't have to configure anything manually.

---

# ✅ **2. APPLICATION PROPERTIES – application.properties / application.yml**

Spring Boot reads configuration from:

* `application.properties`
* `application.yml`

Located under:

```
src/main/resources/
```

Used to configure:

* Server port
* Database config
* Logging
* Security
* Profiles

---

# 🔥 **application.properties Example**

```properties
server.port=8081

spring.datasource.url=jdbc:mysql://localhost:3306/testdb
spring.datasource.username=root
spring.datasource.password=1234

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

# 🔥 **application.yml Example (same config in YAML)**

```yaml
server:
  port: 8081

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/testdb
    username: root
    password: 1234

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### ✔ Why YAML is preferred?

* Clean hierarchical structure
* Easier for complex configs
* Readable indentation

---

## ⭐ Properties Binding (Very Important)

### Example: `application.yml`

```yaml
app:
  email: admin@example.com
  timeout: 5000
```

### Create POJO

```java
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private String email;
    private int timeout;

    // getters & setters
}
```

Spring automatically binds the YAML values → POJO fields.

---

# 🎯 **3. BUILDING REST APIs WITH SPRING MVC**

Spring MVC is the Web framework used by Spring Boot to build REST APIs.

### ✔ Key components

* `@RestController` — returns JSON responses
* `@RequestMapping` — base mapping
* `@GetMapping`, `@PostMapping`, `@PutMapping` etc.
* Jackson — for JSON serialization
* `@RequestBody`, `@PathVariable`, `@RequestParam`

---

# 🧱 **REST API Example: User Management**

Let’s build:

* **GET /api/users** – Get all
* **GET /api/users/{id}** – Get one
* **POST /api/users** – Create
* **PUT /api/users/{id}** – Update
* **DELETE /api/users/{id}** – Delete

---

# 1️⃣ **User Model**

```java
public class User {
    private Long id;
    private String name;
    private String email;

    // getters and setters
}
```

---

# 2️⃣ **Service Layer**

```java
@Service
public class UserService {

    private Map<Long, User> users = new HashMap<>();
    private AtomicLong idGenerator = new AtomicLong(1);

    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    public User getById(Long id) {
        return users.get(id);
    }

    public User create(User user) {
        user.setId(idGenerator.getAndIncrement());
        users.put(user.getId(), user);
        return user;
    }

    public User update(Long id, User updated) {
        updated.setId(id);
        users.put(id, updated);
        return updated;
    }

    public void delete(Long id) {
        users.remove(id);
    }
}
```

---

# 3️⃣ **REST Controller (Spring MVC)**

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // GET all users
    @GetMapping
    public List<User> getAllUsers() {
        return service.getAll();
    }

    // GET one user
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return service.getById(id);
    }

    // CREATE user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return service.create(user);
    }

    // UPDATE user
    @PutMapping("/{id}")
    public User updateUser(
            @PathVariable Long id,
            @RequestBody User user
    ) {
        return service.update(id, user);
    }

    // DELETE user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        service.delete(id);
    }
}
```

---

# 🔥 What this controller shows:

| Annotation                        | Meaning                      |
| --------------------------------- | ---------------------------- |
| **@RestController**               | Class returns JSON, not HTML |
| **@RequestMapping("/api/users")** | Base path                    |
| **@GetMapping**                   | Handle GET requests          |
| **@PostMapping**                  | Handle POST requests         |
| **@PutMapping**                   | Handle PUT requests          |
| **@DeleteMapping**                | Handle DELETE requests       |
| **@PathVariable**                 | Extract `{id}` from URL      |
| **@RequestBody**                  | JSON → Java object           |

---

# 🧠 Additional Spring MVC Concepts

### ✔ Query parameters

```
GET /api/users?name=pratyush
```

```java
@GetMapping
public List<User> findByName(@RequestParam String name) {
    // ...
}
```

---

### ✔ Returning ResponseEntity (for status codes)

```java
return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(newUser);
```

---

### ✔ Exception Handling (global)

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception ex) {
        return ResponseEntity.status(500).body(ex.getMessage());
    }
}
```

---

# 📌 Summary Cheat Sheet

### ⭐ Spring Boot Starters

* Dependency bundles
* Auto-configuration

### ⭐ Properties/YAML

* Application settings
* Bind values to POJOs using `@ConfigurationProperties`

### ⭐ REST API with Spring MVC

* Use `@RestController`
* Use mapping annotations
* Automatically converts JSON ↔ Java

---
Below is the **most complete, interview-ready explanation of all important Spring Boot + Spring Framework annotations**, with:

✔ A **clean master table** at the top
✔ Explanation of every annotation
✔ Real-world scenarios
✔ Practical **Java + Spring Boot code examples**
✔ Grouped by purpose (Core, DI, MVC, JPA, Validation, Testing, etc.)

This is the *ultimate annotation cheat sheet* for SDE/Backend interviews.

---

# ✅ **MASTER TABLE — Spring Boot & Spring Core Annotations**

| Category                   | Annotation                     | Purpose                                           | Scenario                             |
| -------------------------- | ------------------------------ | ------------------------------------------------- | ------------------------------------ |
| **Core / Boot**            | `@SpringBootApplication`       | Enables auto-config + component scanning + config | Main application class               |
|                            | `@EnableAutoConfiguration`     | Enable Spring Boot auto-config                    | Rarely used alone                    |
|                            | `@ComponentScan`               | Scans packages for beans                          | Controllers/services auto-detected   |
|                            | `@Configuration`               | Marks configuration class                         | Defining custom beans                |
|                            | `@Bean`                        | Defines a bean manually                           | Create external libs as beans        |
| **Dependency Injection**   | `@Autowired`                   | Inject dependency                                 | Service -> Repository                |
|                            | `@Qualifier`                   | Resolve bean ambiguity                            | Multiple Repo beans                  |
|                            | `@Value`                       | Inject values from properties/YAML                | DB URL, email, API keys              |
|                            | `@Primary`                     | Mark bean as preferred                            | Default implementation               |
| **Stereotype Annotations** | `@Component`                   | Generic bean                                      | Utility class                        |
|                            | `@Service`                     | Service layer bean                                | Business logic                       |
|                            | `@Repository`                  | DAO layer                                         | DB operations, exception translation |
|                            | `@Controller`                  | MVC controller                                    | Returning HTML                       |
|                            | `@RestController`              | REST API controller                               | Returning JSON                       |
| **Spring MVC / REST**      | `@RequestMapping`              | Base URL mapping                                  | `/api/users`                         |
|                            | `@GetMapping`                  | GET endpoint                                      | Fetch data                           |
|                            | `@PostMapping`                 | POST endpoint                                     | Create data                          |
|                            | `@PutMapping`                  | PUT endpoint                                      | Update                               |
|                            | `@PatchMapping`                | Partial update                                    | PATCH                                |
|                            | `@DeleteMapping`               | DELETE endpoint                                   | Remove                               |
|                            | `@RequestBody`                 | Bind JSON → Java                                  | POST JSON                            |
|                            | `@PathVariable`                | Extract `{id}` from URL                           | `/users/3`                           |
|                            | `@RequestParam`                | Extract query param                               | `/search?name=ram`                   |
|                            | `@ResponseStatus`              | Custom status code                                | 201 Created                          |
| **JPA / Database**         | `@Entity`                      | Domain model                                      | DB table                             |
|                            | `@Id`                          | Primary key                                       |                                      |
|                            | `@GeneratedValue`              | Auto-generate ID                                  |                                      |
|                            | `@Column`                      | Column config                                     | nullable, length                     |
|                            | `@Table`                       | Table name                                        |                                      |
|                            | `@OneToMany`                   | Relationships                                     | User → Orders                        |
|                            | `@ManyToOne`                   | Child to parent                                   | Order → User                         |
|                            | `@JoinColumn`                  | Foreign key                                       |                                      |
|                            | `@Transactional`               | Transaction boundary                              | Service methods                      |
| **Validation**             | `@Valid`                       | Validate request body                             | DTO validation                       |
|                            | `@NotNull`, `@Email`, `@Size`  | Field validation                                  | User name/email                      |
| **AOP**                    | `@Aspect`                      | Aspect class                                      | Logging, security                    |
|                            | `@Before`, `@After`, `@Around` | Advice types                                      | Log execution time                   |
| **Testing**                | `@SpringBootTest`              | Bootstraps entire app                             | Integration tests                    |
|                            | `@MockBean`                    | Mock bean in Spring context                       | Mock Service in controller test      |

---

# 🔥 NOW LET’S EXPLAIN EVERYTHING IN DETAIL WITH SCENARIOS & CODE

---

# 🎯 **1. Spring Boot Core Annotations**

---

## **1️⃣ @SpringBootApplication**

Combination of:

* `@Configuration`
* `@EnableAutoConfiguration`
* `@ComponentScan`

### Scenario

This annotation is placed on the **main class** of the Spring Boot application.

### Code

```java
@SpringBootApplication
public class EcommerceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }
}
```

---

## **2️⃣ @Configuration**

Marks a class where Spring will look for bean definitions.

### Scenario

When you want to create **custom beans**.

```java
@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
```

---

## **3️⃣ @Bean**

Used **inside @Configuration** class to manually define a bean.

---

## **4️⃣ @ComponentScan**

Defines the packages to scan for Spring components.

```java
@SpringBootApplication
@ComponentScan(basePackages = "com.example")
public class ApiApp { }
```

---

# 🎯 **2. Dependency Injection Annotations**

---

## **5️⃣ @Autowired**

Injects a dependency.

### Example

```java
@Service
public class OrderService {

    @Autowired
    private PaymentService paymentService;
}
```

---

## **6️⃣ @Qualifier**

Used when multiple beans of same type exist.

```java
@Service
public class NotificationService {

    @Autowired
    @Qualifier("emailNotification")
    private Notifier notifier;
}
```

---

## **7️⃣ @Primary**

Makes a bean the default when multiple exist.

```java
@Primary
@Service
public class EmailNotification implements Notifier { }
```

---

## **8️⃣ @Value**

Inject values from properties/YAML.

```java
@Value("${app.email}")
private String adminEmail;
```

---

# 🎯 **3. Stereotype Annotations**

---

## **9️⃣ @Component**

Generic Spring bean.

```java
@Component
public class TokenGenerator { }
```

---

## **🔟 @Service**

Business logic layer.

```java
@Service
public class UserService { }
```

---

## **1️⃣1️⃣ @Repository**

DAO layer; also enables Spring’s **exception translation**.

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> { }
```

---

## **1️⃣2️⃣ @Controller**

Returns HTML (Thymeleaf/MVC).

```java
@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index";
    }
}
```

---

## **1️⃣3️⃣ @RestController**

`@Controller` + `@ResponseBody` → returns JSON.

```java
@RestController
@RequestMapping("/api/users")
public class UserController { }
```

---

# 🎯 **4. Spring MVC / REST Annotations**

---

## **1️⃣4️⃣ @RequestMapping**

Defines a base path or method path.

```java
@RequestMapping("/api/customers")
public class CustomerController { }
```

---

## **1️⃣5️⃣ @GetMapping / @PostMapping / @PutMapping …**

Example:

```java
@GetMapping("/{id}")
public User getUser(@PathVariable Long id) { }
```

---

## **1️⃣6️⃣ @RequestBody**

JSON → Java object.

```java
@PostMapping
public User create(@RequestBody User user) { }
```

---

## **1️⃣7️⃣ @PathVariable**

Extracts `{id}` from URL.

```java
@GetMapping("/{id}")
public User get(@PathVariable Long id) { }
```

---

## **1️⃣8️⃣ @RequestParam**

Extracts query params.

```
/search?name=ram
```

```java
@GetMapping("/search")
public List<User> search(@RequestParam String name) { }
```

---

## **1️⃣9️⃣ @ResponseStatus**

Custom status codes.

```java
@ResponseStatus(HttpStatus.CREATED)
@PostMapping
public void createUser() { }
```

---

# 🎯 **5. JPA / ORM Annotations**

---

## **2️⃣0️⃣ @Entity**

Marks class as table.

```java
@Entity
public class User { }
```

---

## **2️⃣1️⃣ @Id, @GeneratedValue**

Primary key & auto-generation.

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

---

## **2️⃣2️⃣ @Column**

Customize fields.

```java
@Column(nullable = false, length = 50)
private String name;
```

---

## **2️⃣3️⃣ @Table**

Specify table name.

```java
@Table(name = "users")
```

---

## **2️⃣4️⃣ @OneToMany / @ManyToOne / @JoinColumn**

### Example: One user → Many orders

```java
@Entity
public class User {
    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
```

```java
@Entity
public class Order {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
```

---

## **2️⃣5️⃣ @Transactional**

Runs method inside a transaction.

```java
@Service
public class PaymentService {

    @Transactional
    public void processPayment() { }
}
```

---

# 🎯 **6. Validation Annotations**

These validate request body DTOs.

```java
public class UserDTO {

    @NotNull
    private String name;

    @Email
    private String email;

    @Size(min = 6)
    private String password;
}
```

Use with:

```java
@PostMapping
public void create(@Valid @RequestBody UserDTO dto) { }
```

---

# 🎯 **7. AOP (Aspect-Oriented Programming)**

---

## **@Aspect**

Marks a class as an aspect.

```java
@Aspect
@Component
public class LoggingAspect { }
```

---

## **@Before / @After / @Around**

Applied on methods.

```java
@Around("execution(* com.app.service.*.*(..))")
public Object logExecution(ProceedingJoinPoint jp) throws Throwable {
    long start = System.currentTimeMillis();
    Object result = jp.proceed();
    long end = System.currentTimeMillis();

    System.out.println("Execution time: " + (end - start));
    return result;
}
```

---

# 🎯 **8. Testing Annotations**

---

## **@SpringBootTest**

Loads full Spring Boot context.

```java
@SpringBootTest
class UserServiceTest { }
```

---

## **@MockBean**

Used to mock dependencies in Spring context.

```java
@MockBean
private UserRepository userRepo;
```

---

# 🧠 **Full Project Example Using Most Annotations**

```java
@SpringBootApplication
public class DemoApp { }

// Controller
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody UserDTO dto) {
        return service.createUser(dto);
    }
}

// Service
@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Transactional
    public User createUser(UserDTO dto) {
        User user = new User(dto.getName(), dto.getEmail());
        return repo.save(user);
    }
}

// Entity
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;
}
```



Below is a **complete, interview-level, production-grade explanation** of all the topics you requested — **Spring Data JPA, Hibernate, entity mapping, transactions, DTOs, validation, exception handling, API versioning, pagination, HATEOAS, and Swagger/OpenAPI** — with **clear concepts + deep explanations + real Spring Boot code**.

This is a **full backend module** you can directly use in your resume or projects.

---

# ✅ **1. DATA ACCESS WITH SPRING DATA JPA & HIBERNATE**

Spring Data JPA is a high-level abstraction over JPA (Java Persistence API).
Hibernate is the most popular **JPA implementation**.

---

# ⭐ Key Concepts

### ✔ **Entity** → Represents a table

### ✔ **Repository** → Provides CRUD operations

### ✔ **Hibernate** → ORM layer mapping Java objects ↔ SQL

### ✔ **Transactions** → Guarantee consistency

### ✔ **Lazy/Eager Loading** → Fetch strategy

---

# 🧱 **1.1 ENTITY MAPPING — Domain Model → DB Table**

### Example: `User` entity

```java
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
}
```

---

# 🧩 **1.2 RELATIONSHIPS**

### ✔ OneToMany Example

```java
@OneToMany(mappedBy = "user")
private List<Order> orders;
```

### ✔ ManyToOne Example

```java
@ManyToOne
@JoinColumn(name = "user_id")
private User user;
```

### ✔ ManyToMany Example

```java
@ManyToMany
@JoinTable(
    name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id")
)
private Set<Role> roles;
```

---

# 🏗 **1.3 SPRING DATA JPA REPOSITORY**

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
```

Spring automatically implements:

* `findAll()`
* `save()`
* `deleteById()`
* `findById()`
* Custom methods via naming conventions

---

# 🔥 **1.4 TRANSACTIONS — @Transactional**

Transactions ensure **atomicity**. Either all DB operations succeed or none do.

```java
@Service
public class OrderService {

    @Transactional
    public void placeOrder(Order order) {
        inventoryService.reduceStock(order);
        paymentService.processPayment(order);
        orderRepository.save(order);
    }
}
```

If payment fails → Everything rolls back.

---

# 📌 Transaction propagation (interview important)

| Type             | Explanation                                      |
| ---------------- | ------------------------------------------------ |
| **REQUIRED**     | Default. Join existing or create new transaction |
| **REQUIRES_NEW** | Always create new transaction                    |
| **MANDATORY**    | Must run inside a transaction                    |
| **SUPPORTS**     | Runs with or without a transaction               |

---

# ---------------------------------------------------------------

# ✅ **2. DTOs (Data Transfer Objects)**

DTO = request/response object.
It **decouples API data** from internal entity model.

### Example: `UserDTO`

```java
public class UserDTO {
    @NotBlank
    private String name;

    @Email
    private String email;
}
```

---

### Why DTOs?

| Benefit     | Explanation               |
| ----------- | ------------------------- |
| Security    | Hide DB columns           |
| Performance | Avoid lazy loading issues |
| Validation  | Use @Valid annotations    |
| Versioning  | Different DTOs for v1, v2 |

---

# ---------------------------------------------------------------

# ✅ **3. VALIDATION — javax.validation + @Valid**

Spring Boot uses Hibernate Validator.

Common annotations:

* `@NotNull`
* `@NotBlank`
* `@Email`
* `@Size(min, max)`
* `@Pattern`
* `@Min`, `@Max`

---

### **Controller Example**

```java
@PostMapping
public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
}
```

If validation fails → Spring throws `MethodArgumentNotValidException`.

---

# ---------------------------------------------------------------

# ✅ **4. EXCEPTION HANDLING & ERROR RESPONSE DESIGN**

Use **@RestControllerAdvice** to centralize error handling.

---

# 🔥 **Global Exception Handler**

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(new ErrorResponse(ex.getMessage(), 404));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> 
                errors.put(e.getField(), e.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }
}
```

---

### **Error Response Model**

```java
public class ErrorResponse {
    private String message;
    private int statusCode;
}
```

---

# ---------------------------------------------------------------

# ✅ **5. API VERSIONING**

Common strategies:

| Type                  | Example                |
| --------------------- | ---------------------- |
| **URL versioning**    | `/api/v1/users`        |
| **Header versioning** | `X-API-VERSION: 1`     |
| **Param versioning**  | `/api/users?version=1` |

---

### ✔ URL Versioning Example

```java
@RestController
@RequestMapping("/api/v1/users")
public class UserControllerV1 {

    @GetMapping
    public String getUsers() {
        return "Users from V1";
    }
}
```

```java
@RestController
@RequestMapping("/api/v2/users")
public class UserControllerV2 {

    @GetMapping
    public String getUsers() {
        return "Users from V2";
    }
}
```

---

# ---------------------------------------------------------------

# ✅ **6. PAGINATION with Spring Data**

Spring Data JPA provides `Pageable`, `PageRequest`.

### Example

```java
@GetMapping
public Page<User> getUsers(
        @RequestParam int page,
        @RequestParam int size) {

    return userRepo.findAll(PageRequest.of(page, size));
}
```

### Response includes:

* total elements
* total pages
* current page
* content

---

# ---------------------------------------------------------------

# ✅ **7. HATEOAS (Optional Advanced Topic)**

HATEOAS adds **links inside response** to guide the client.

### Example

```java
public class UserModel extends RepresentationModel<UserModel> {
    private Long id;
    private String name;
}
```

Controller:

```java
@GetMapping("/{id}")
public EntityModel<User> getUser(@PathVariable Long id) {
    User user = repo.findById(id).orElseThrow();

    return EntityModel.of(user,
        linkTo(methodOn(UserController.class).getUser(id)).withSelfRel(),
        linkTo(methodOn(UserController.class).getAll()).withRel("all-users")
    );
}
```

---

# ---------------------------------------------------------------

# ✅ **8. OPENAPI / SWAGGER DOCUMENTATION**

Add dependency:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>
```

---

### Start the app → Open Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

---

### Add API Documentation Annotations

```java
@RestController
@RequestMapping("/api/users")
@Tag(name = "Users API", description = "Operations related to users")
public class UserController {

    @Operation(summary = "Get user by ID", description = "Finds a user using ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return service.getById(id);
    }
}
```

Swagger automatically generates:

* Endpoints
* Request/Response models
* Example payloads
* Error responses

---

# ---------------------------------------------------------------

# 🎉 **COMPLETE FLOW DIAGRAM (Conceptual)**

```
Client → Controller → DTO → Service → @Transactional → Repository → Hibernate → Database
         | Validation Errors
         ↓
  Global Exception Handler → ErrorResponse
```

---
