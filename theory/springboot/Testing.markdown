Below is a **deep, interview-level explanation** of the three topics you provided.
I’ve covered **concepts → internal working → best practices → common interview questions → Java code examples** (including Spring Boot + Testcontainers + Mockito).

---

# ✅ **1. JUnit 5: Annotations, Assertions, Lifecycle**

![Image](https://miro.medium.com/v2/resize%3Afit%3A1400/0%2A1Y4ffLi2bw9HaH7s.png?utm_source=chatgpt.com)

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20231228120919/JUnit-5-test-Lifecycle-660.png?utm_source=chatgpt.com)

![Image](https://www.arhohuttunen.com/media/post/junit-5-test-lifecycle/junit-5-per-method-lifecycle.svg?utm_source=chatgpt.com)

---

## **What JUnit 5 Actually Is**

JUnit 5 is not a single library. It has 3 modules:

1. **JUnit Platform** → Runs the tests
2. **JUnit Jupiter** → Provides new annotations & APIs
3. **JUnit Vintage** → Runs old JUnit 3/4 tests on JUnit 5

Most developers use **JUnit Jupiter**, which contains `@Test`, lifecycle callbacks, assertions, etc.

---

## ⭐ **JUnit 5 Annotations (Core)**

### **1. @Test**

Marks a method as a test method.

```java
@Test
void testSum() {
    assertEquals(4, 2 + 2);
}
```

---

### **2. @BeforeEach / @AfterEach**

Executed **before/after each test method**.

Used to reset shared state.

```java
@BeforeEach
void setup() {
    calculator = new Calculator();
}

@AfterEach
void cleanup() {
    calculator = null;
}
```

---

### **3. @BeforeAll / @AfterAll**

Executed **once per test class** (static methods unless using @TestInstance).

Use when setting up expensive resources like:

* Database connection
* Testcontainers start
* Loading files

```java
@BeforeAll
static void initAll() { System.out.println("Runs once before all tests"); }
```

---

### **4. @DisplayName**

Gives readable test names.

```java
@DisplayName("Should return correct sum")
@Test
void sumTest() {}
```

---

### **5. @Disabled**

Skip a test.

```java
@Disabled("Feature not ready")
@Test
void testFeature() {}
```

---

### **6. Parameterized Tests**

Useful for running same test with many values.

```java
@ParameterizedTest
@ValueSource(strings = {"madam", "racecar"})
void testPalindrome(String word) {
    assertTrue(isPalindrome(word));
}
```

---

## ⭐ **JUnit 5 Assertions**

### **Basic Assertions**

```java
assertEquals(10, result);
assertNotNull(user);
assertTrue(age > 18);
assertThrows(IllegalArgumentException.class, () -> service.validate(null));
```

---

### **AssertAll (Group Assertions)**

Runs all assertions even if some fail.

```java
assertAll(
    () -> assertEquals("John", user.getName()),
    () -> assertTrue(user.getAge() > 18)
);
```

---

### **Timeout Assertions**

```java
assertTimeout(Duration.ofMillis(500), () -> service.process());
```

---

## ⭐ **JUnit 5 Lifecycle Flow**

```
@BeforeAll → @BeforeEach → @Test → @AfterEach → @AfterAll
```

A class-level lifecycle annotation exists:

### **@TestInstance(Lifecycle.PER_CLASS)**

Makes @BeforeAll/@AfterAll non-static and allows sharing state between tests.

---

# ✅ **2. Mocking With Mockito**

![Image](https://www.softwaretestinghelp.com/wp-content/qa/uploads/2018/12/UnitTestingDependencies-1.jpg?utm_source=chatgpt.com)

![Image](https://www.oreilly.com/api/v2/epubs/urn%3Aorm%3Abook%3A9781783983605/files/graphics/3605OS_02_05.jpg?utm_source=chatgpt.com)

![Image](https://i.sstatic.net/frwt1.png?utm_source=chatgpt.com)

---

## 📌 What Is Mocking?

Mocking means **you replace real dependencies with fake objects** that behave the way you want.

Used for **unit tests** to isolate business logic from:

* Database
* API calls
* File system
* Message queues

---

## ⭐ Mockito Core Concepts

### **1. @Mock**

Creates a mock object.

```java
@Mock
UserRepository userRepository;
```

---

### **2. @InjectMocks**

Injects mocks into the class under test.

```java
@InjectMocks
UserService userService;
```

---

### **3. Stubbing**

Define mock behaviour.

```java
when(userRepository.findById(1L))
    .thenReturn(Optional.of(new User("John")));
```

---

### **4. Verify behaviour**

Check if a method was called.

```java
verify(userRepository, times(1)).save(any(User.class));
```

---

### **5. ArgumentCaptor**

Capture method input.

```java
ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
verify(userRepository).save(captor.capture());
assertEquals("John", captor.getValue().getName());
```

---

## ⭐ Full Mockito Example (Spring Boot)

### **Service**

```java
public class UserService {

    @Autowired
    private UserRepository repository;

    public User getUser(Long id) {
        return repository.findById(id).orElseThrow();
    }
}
```

### **Tests**

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    UserService service;

    @Test
    void testGetUser() {
        when(repository.findById(1L))
            .thenReturn(Optional.of(new User("John")));

        User user = service.getUser(1L);

        assertEquals("John", user.getName());
        verify(repository).findById(1L);
    }
}
```

---

# ✅ **3. Integration Tests with Testcontainers & H2**

![Image](https://testcontainers.com/getting-started/images/cn-arch.png?utm_source=chatgpt.com)

![Image](https://openliberty.io/img/guide/testcontainers/inventory.png?utm_source=chatgpt.com)

![Image](https://miro.medium.com/v2/resize%3Afit%3A1400/1%2AkTrTI3Dt5WO1tg7qGLkJjw.png?utm_source=chatgpt.com)

---

# ⭐ What Are Integration Tests?

They test application behaviour with **real environment components**, such as:

* Real database (PostgreSQL/MySQL)
* Redis
* Kafka
* External APIs

Not mocked → more realistic.

---

# ⭐ Testcontainers (Most Important for Interviews)

Testcontainers uses **lightweight Docker containers** for databases in tests.

Benefits:

✔ Real DB → same behaviour as production
✔ Automatically starts and stops containers
✔ No need to install DB locally
✔ Clean test environment every time

---

## ⭐ Example: Using PostgreSQL Testcontainer in Spring Boot

### **Add Dependencies**

```xml
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>postgresql</artifactId>
    <version>1.19.3</version>
    <scope>test</scope>
</dependency>
```

---

### **Integration Test**

```java
@Testcontainers
@SpringBootTest
class UserRepositoryIT {

    @Container
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("user")
            .withPassword("password");

    @Autowired
    UserRepository userRepository;

    @DynamicPropertySource
    static void config(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void testSaveUser() {
        User user = new User("Alice");
        userRepository.save(user);

        assertEquals(1, userRepository.findAll().size());
    }
}
```

---

# ⭐ In-Memory DB (H2) for Faster Integration Tests

H2 is a lightweight SQL database running in memory.

Used when:

* Ultra fast tests required
* DB-specific behaviour not important

---

## Example Spring Boot + H2 Test

### **application-test.properties**

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=create-drop
```

---

### **Integration Test**

```java
@SpringBootTest
@ActiveProfiles("test")
class H2IntegrationTest {

    @Autowired
    UserRepository repository;

    @Test
    void testInsertUser() {
        repository.save(new User("Bob"));
        assertEquals(1, repository.count());
    }
}
```

---

# ⭐ Testcontainers vs H2 (Interview Question)

| Feature                               | Testcontainers | H2        |
| ------------------------------------- | -------------- | --------- |
| Real DB                               | Yes            | No        |
| Matches production behavior           | ✔              | ❌         |
| Speed                                 | Slower         | Very fast |
| Docker required                       | Yes            | No        |
| Recommended for production-like tests | ✔              | ❌         |

Companies increasingly expect **Testcontainers knowledge**.

---

# ⭐ Common Interview Questions

### **JUnit 5**

* Difference between JUnit 4 and JUnit 5?
* What is @TestInstance(Lifecycle.PER_CLASS)?
* How does @BeforeEach differ from @BeforeAll?

### **Mockito**

* What is mocking vs stubbing?
* What is @InjectMocks doing under the hood?
* When should you use ArgumentCaptor?
* Why should we not mock static methods (until Mockito 3.4)?

### **Testcontainers**

* Why use Testcontainers instead of H2?
* How are containers started/stopped?
* How to override Spring Boot properties with DynamicPropertySource?

---

# 🔥 Want me to create **full runnable Spring Boot project (JUnit + Mockito + Testcontainers)** for these topics?

I can generate:

✔ `pom.xml`
✔ Entity, Repository, Service
✔ All tests (unit + integration)
✔ Docker-backed Testcontainers setup

Just say **“generate project”**.
Below is a **deep, interview-focused, practical explanation** of the next three testing topics you asked for:

* **Parameterized Tests**
* **Assertion Libraries (AssertJ)**
* **Code Coverage Tools (JaCoCo) & Static Analysis (SpotBugs, Checkstyle)**
* **Basics of Test-Driven Development (TDD)**

Each section includes **concepts → best practices → interview questions → Java/Spring Boot examples**.

---

# ✅ 1. **Parameterized Tests (JUnit 5)**

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20231228120919/JUnit-5-test-Lifecycle-660.png?utm_source=chatgpt.com)

![Image](https://mkyong.com/wp-content/uploads/2019/11/junit-5-param-test.png?utm_source=chatgpt.com)

### ✔ What They Are

Parameterized tests allow **running the same test multiple times with different inputs**, reducing duplication and improving test clarity.

Used for:

* Testing algorithms
* Edge-case coverage
* Validations
* Boundary testing

---

## ⭐ Types of Parameter Sources

### **1. @ValueSource**

Most common → tests with simple literals.

```java
@ParameterizedTest
@ValueSource(strings = {"madam", "racecar", "level"})
void testPalindrome(String word) {
    assertTrue(isPalindrome(word));
}
```

---

### **2. @CsvSource (multiple inputs per test)**

```java
@ParameterizedTest
@CsvSource({
    "2, 3, 5",
    "10, 5, 15"
})
void testAddition(int a, int b, int expected) {
    assertEquals(expected, a + b);
}
```

---

### **3. @CsvFileSource (external CSV file)**

```
# numbers.csv
2,3,5
10,20,30
```

```java
@ParameterizedTest
@CsvFileSource(resources = "/numbers.csv")
void testAdditionFromFile(int a, int b, int expected) {
    assertEquals(expected, a + b);
}
```

---

### **4. @EnumSource**

Testing enum functionality.

```java
@ParameterizedTest
@EnumSource(Day.class)
void testEnum(Day day) {
    assertNotNull(day);
}
```

---

### **5. @MethodSource**

When inputs need complex objects.

```java
static Stream<Arguments> userProvider() {
    return Stream.of(
        Arguments.of(new User("A", 20)),
        Arguments.of(new User("B", 30))
    );
}

@ParameterizedTest
@MethodSource("userProvider")
void testUser(User user) {
    assertTrue(user.getAge() > 0);
}
```

---

### ⭐ Benefits (Interview)

* Eliminates repetitive tests
* Better coverage with fewer lines
* Encourages boundary testing
* Realistic data testing using CSV files

---

# ✅ 2. **Assertion Libraries – AssertJ**

![Image](https://img-c.udemycdn.com/course/750x422/3197776_e85e_2.jpg?utm_source=chatgpt.com)

![Image](https://miro.medium.com/v2/resize%3Afit%3A1200/1%2AOlqb2vrEnk2-V20iv8WPmw.png?utm_source=chatgpt.com)

![Image](https://sangsoonam.github.io/images/2016/02-03/assertj.png?utm_source=chatgpt.com)

---

## ✔ What AssertJ Is

AssertJ is a **fluent, human-readable assertion library** for Java.

JUnit’s assertions are:

```
assertEquals(expected, actual)
```

AssertJ is:

```
assertThat(actual).isEqualTo(expected);
```

More readable and chainable.

---

## ⭐ AssertJ Key Features

### **1. Fluent, chainable assertions**

```java
assertThat(user.getName())
    .isNotBlank()
    .startsWith("A")
    .endsWith("h");
```

---

### **2. Collection assertions**

```java
assertThat(users)
    .hasSize(3)
    .extracting("name")
    .contains("Alice", "Bob");
```

---

### **3. Exception assertions**

```java
assertThatThrownBy(() -> service.getUser(null))
    .isInstanceOf(IllegalArgumentException.class)
    .hasMessageContaining("id cannot be null");
```

---

### **4. Date/Time assertions**

```java
assertThat(date).isBefore(LocalDate.now());
```

---

### **5. Optional assertions**

```java
assertThat(optionalUser)
    .isPresent()
    .get()
    .extracting(User::getName)
    .isEqualTo("John");
```

---

### ⭐ Why AssertJ over JUnit?

| Feature               | JUnit   | AssertJ   |
| --------------------- | ------- | --------- |
| Readability           | ⭐       | ⭐⭐⭐⭐⭐     |
| Fluent API            | ❌       | ✔         |
| Collection assertions | Limited | Excellent |
| Exception testing     | OK      | Best      |
| Custom conditions     | ❌       | ✔         |

Interviewers often expect AssertJ in modern Spring Boot projects.

---

# ✅ 3. **Code Coverage (JaCoCo) & Static Analysis (SpotBugs, Checkstyle)**

![Image](https://www.eclemma.org/images/jacocoreport.png?utm_source=chatgpt.com)

![Image](https://miro.medium.com/1%2ACG_3O4e5-qecl6pMvcuIQg.jpeg?utm_source=chatgpt.com)

![Image](https://raw.githubusercontent.com/jdneo/vscode-checkstyle/master/docs/gifs/setVersion%28lower%29.gif?utm_source=chatgpt.com)

---

# ⭐ A. **JaCoCo – Code Coverage**

### ✔ What It Does

JaCoCo tells you what percentage of code is executed by tests.

Types of coverage:

* **Line coverage**
* **Branch coverage**
* **Method/Class coverage**

---

## 📌 Add JaCoCo in Maven

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

Run report:

```
mvn test
```

Output:
`/target/site/jacoco/index.html`

---

## ⭐ Interview Insights for JaCoCo

### ❓ Does JaCoCo guarantee high test quality?

No.
You can reach 95% coverage with meaningless tests.

Coverage ≠ correctness.

---

---

# ⭐ B. **SpotBugs – Static Analysis**

SpotBugs detects **runtime bugs before running the code**, e.g.:

* Null dereference
* Infinite loops
* Multithreading problems
* Use of deprecated APIs

---

## Add SpotBugs (Maven)

```xml
<plugin>
    <groupId>com.github.spotbugs</groupId>
    <artifactId>spotbugs-maven-plugin</artifactId>
    <version>4.7.3.0</version>
</plugin>
```

Run:

```
mvn spotbugs:spotbugs
```

---

### Typical issues caught:

* Dead stores
* Unclosed resources
* Bad equals/hashcode
* Wrong string comparison
* Inefficient loops

---

# ⭐ C. **Checkstyle – Code Style Enforcer**

Checkstyle enforces coding standards:

* Naming conventions
* Line length
* Braces & indentation
* Javadoc rules

---

## Add Checkstyle (Maven)

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-checkstyle-plugin</artifactId>
    <version>3.3.1</version>
</plugin>
```

Run:

```
mvn checkstyle:check
```

---

### Interview highlight

Checkstyle improves **code consistency**, SpotBugs improves **code correctness**.

---

# ✅ 4. **Basics of Test-Driven Development (TDD)**

![Image](https://miro.medium.com/1%2AtZSwCigaTaJdovyWlp5uBQ.jpeg?utm_source=chatgpt.com)

![Image](https://developer.ibm.com/developer/default/articles/5-steps-of-test-driven-development/images/tdd-red-green-refactoring-v3.png?utm_source=chatgpt.com)

![Image](https://media.geeksforgeeks.org/wp-content/uploads/20240614153321/Test-Driven-Dev.webp?utm_source=chatgpt.com)

---

## ⭐ The 3-Step TDD Cycle

### **1. RED → Write a failing test**

* No implementation yet
* Test describes expected behavior

### **2. GREEN → Write minimum code to pass test**

* Do not write perfect code
* Just satisfy the test

### **3. REFACTOR → Improve the code**

* Clean code
* Keep tests green

Repeat.

---

## ⭐ Example: Write a function to check if number is prime

### **RED**

```java
@Test
void shouldReturnTrueForPrimeNumbers() {
    assertTrue(MathUtil.isPrime(5));
}
```

Fails → correct.

---

### **GREEN**

```java
public static boolean isPrime(int n) {
    if (n == 5) return true; // minimal solution
    return false;
}
```

Passes test → but ugly.

---

### **REFACTOR**

```java
public static boolean isPrime(int n) {
    if (n <= 1) return false;
    for (int i = 2; i <= Math.sqrt(n); i++) {
        if (n % i == 0) return false;
    }
    return true;
}
```

Now add more tests → repeat cycle.

---

## ⭐ TDD Interview Questions

### **1. Does TDD mean 100% coverage?**

No, but typically high coverage.

---

### **2. Is TDD only for unit tests?**

Mostly yes, but can influence integration tests too.

---

### **3. Biggest challenges?**

* Takes more time initially
* Developers resist writing tests first
* Hard to apply when requirements unclear

---

### **4. Benefits?**

* Cleaner design
* Fewer bugs
* Safer refactoring
* Documentation through tests

---

# 🎯 Want me to generate a **full Spring Boot sample project** containing:

* Parameterized tests
* AssertJ
* Mockito
* Testcontainers
* JaCoCo
* SpotBugs, Checkstyle
* TDD examples

Just say: **“Generate full project with all testing tools.”**


Here are **40 interview questions with detailed answers** covering all topics you requested:

---

# ✅ **JUnit 5 – Parameterized Tests – AssertJ – Mockito – Testcontainers – H2 – JaCoCo – SpotBugs – Checkstyle – TDD**

---

# 🔥 **SECTION 1: JUNIT 5 (10 Questions)**

---

## **1. What are the main components of JUnit 5?**

JUnit 5 contains three sub-projects:

1. **JUnit Platform** – base foundation, test engine, IDE integration.
2. **JUnit Jupiter** – new programming model (`@Test`, `@BeforeEach`, assertions).
3. **JUnit Vintage** – backward compatibility for JUnit 4/3 tests.

This modular structure allows running multiple test engines simultaneously (JUnit + Mockito extensions + custom engines).

---

## **2. Difference between @BeforeEach and @BeforeAll?**

* `@BeforeEach` runs **before every test method**, used for resetting state.
* `@BeforeAll` runs **once per class**, used for expensive setup (DB connections, Testcontainers startup).
* `@BeforeAll` must be **static** unless using `@TestInstance(PER_CLASS)`.

---

## **3. What is @TestInstance and why is it used?**

`@TestInstance(TestInstance.Lifecycle.PER_CLASS)` tells JUnit to create **one instance** of the test class instead of a new one per test.

Benefits:

* @BeforeAll methods do not need to be static.
* State can be shared across tests (caution required).

---

## **4. What are dynamic tests in JUnit 5?**

Dynamic tests are created at runtime using `@TestFactory`. Useful when number of tests is unknown beforehand.

```java
@TestFactory
Stream<DynamicTest> palindromeTests() { ... }
```

---

## **5. What is the purpose of Assertions.assertAll()?**

`assertAll` groups multiple assertions and executes **all** of them even if some fail.

---

## **6. When should @Disabled be used?**

Used to skip tests temporarily:

* Feature not ready
* External dependency unavailable

Not recommended for long-term disabling.

---

## **7. What is the difference between @DisplayName and test method name?**

`@DisplayName` allows **human-readable names** in reports and IDEs:

```java
@DisplayName("Should calculate total price correctly")
```

This improves test readability.

---

## **8. What is the default test lifecycle in JUnit 5?**

JUnit creates a **new test class instance per test method** (`PER_METHOD`).
This ensures no shared mutable state.

---

## **9. Why JUnit 5 does not need public test methods?**

JUnit 5 uses reflection and does not require `public` access.

```java
@Test
void testSomething() {} // valid
```

JUnit 4 required public methods.

---

## **10. How does assertThrows() work internally?**

It executes the lambda and records the thrown exception:

```java
assertThrows(IllegalArgumentException.class, () -> service.save(null));
```

If no exception or wrong type → test fails.

---

---

# 🔥 **SECTION 2: PARAMETERIZED TESTS (6 Questions)**

---

## **11. Why use parameterized tests?**

Advantages:

* Reduces duplicate test code
* Ensures boundary testing
* Increases coverage with fewer lines
* Easier testing for algorithms

---

## **12. What is @ValueSource used for?**

Runs the same test with multiple simple literal values (String, int, long, etc.).

```java
@ValueSource(ints = {1, 2, 3})
```

---

## **13. Difference between @CsvSource and @CsvFileSource?**

* `@CsvSource`: Inline CSV values
* `@CsvFileSource`: Loads CSV data from file, useful for **large datasets**

---

## **14. When do we use @MethodSource?**

Use it when parameters are **complex objects**, not simple values.

```java
static Stream<Arguments> provideUsers() { ... }
```

---

## **15. Can parameterized tests use dependency injection?**

Yes. JUnit 5 allows injecting:

* `TestInfo`
* `RepetitionInfo`
* Registered extensions

Even inside parameterized tests.

---

## **16. What happens if @MethodSource method is non-static?**

It will fail **unless** test class uses:

```java
@TestInstance(PER_CLASS)
```

---

---

# 🔥 **SECTION 3: ASSERTJ (6 Questions)**

---

## **17. What makes AssertJ better than JUnit assertions?**

AssertJ provides:

* Fluent API
* Better error messages
* Collection, Optional, Stream assertions
* Chaining (`isNotNull().startsWith("A")`)
* Custom matchers

It significantly improves readability.

---

## **18. How does AssertJ handle exception assertions?**

Using `assertThatThrownBy`:

```java
assertThatThrownBy(() -> service.getUser(null))
    .isInstanceOf(IllegalArgumentException.class)
    .hasMessageContaining("id");
```

---

## **19. What is soft assertions in AssertJ?**

Soft assertions allow collecting **all failures** instead of failing after the first one:

```java
SoftAssertions softly = new SoftAssertions();
softly.assertThat(a).isEqualTo(b);
softly.assertThat(c).isTrue();
softly.assertAll();
```

---

## **20. How do you assert Optional values using AssertJ?**

```java
assertThat(optional)
    .isPresent()
    .get()
    .extracting(User::getName)
    .isEqualTo("John");
```

---

## **21. How to assert collections using AssertJ?**

```java
assertThat(users)
    .hasSize(3)
    .extracting(User::getName)
    .containsExactly("A", "B", "C");
```

---

## **22. What is recursive comparison in AssertJ?**

Compares all fields deeply:

```java
assertThat(actual)
    .usingRecursiveComparison()
    .isEqualTo(expected);
```

Great for DTO validation.

---

---

# 🔥 **SECTION 4: MOCKITO (8 Questions)**

---

## **23. What is mocking and why do we use it?**

Mocking replaces real dependencies with **fake objects** during unit testing.

Reasons:

* Avoid heavy DB/API calls
* Isolate service logic
* Force specific scenarios

---

## **24. What do @Mock and @InjectMocks do internally?**

* `@Mock` → creates a proxy mock instance
* `@InjectMocks` → injects mocks into fields via

  * constructor
  * setter
  * field injection

---

## **25. Difference between mocking and stubbing?**

* **Mocking**: Creating fake object
* **Stubbing**: Defining mock behavior

```java
when(repo.findById(1L)).thenReturn(user);
```

---

## **26. What is verification in Mockito?**

Verifies interactions:

```java
verify(repo, times(1)).save(any());
```

Ensures correct flow.

---

## **27. What is ArgumentCaptor?**

Captures argument passed to a mocked method.

---

## **28. Can Mockito mock final classes or static methods?**

Yes, from **Mockito 3.4+** with:

```java
mockStatic(...)
```

But use sparingly → static methods hide dependencies.

---

## **29. Common mistakes using Mockito?**

* Mocking everything (including your class under test)
* Overuse of verify()
* Using mocks instead of real objects for simple cases
* Mocking value objects or DTOs

---

## **30. Difference between @Mock and @Spy?**

* `@Mock`: no behavior unless stubbed
* `@Spy`: real object but selectively stubbed

---

---

# 🔥 **SECTION 5: TESTCONTAINERS + H2 (6 Questions)**

---

## **31. Why use Testcontainers instead of H2?**

H2 behaves differently from real databases (SQL dialect, functions, transactions).

Testcontainers provides:

* Real PostgreSQL/MySQL
* Reliable E2E tests
* Identical behavior to production

---

## **32. How does Testcontainers start containers?**

It uses **Docker** to start lightweight containers on demand.
Containers start before tests and automatically shut down afterward.

---

## **33. What is @DynamicPropertySource used for?**

It injects container properties into Spring Boot dynamically:

```java
registry.add("spring.datasource.url", container::getJdbcUrl);
```

---

## **34. Can Testcontainers work without Docker installed?**

No. It requires Docker (or Podman) to run.

---

## **35. When should we use H2 instead of Testcontainers?**

Use H2 when:

* Speed matters more than accuracy
* Testing simple repository logic
* No DB-specific behavior required

---

## **36. Can Testcontainers be reused across test classes?**

Yes. Using:

```java
@Container
static PostgreSQLContainer<?> container = ...
```

Helps improve test performance.

---

---

# 🔥 **SECTION 6: JACOCO + STATIC ANALYSIS (4 Questions)**

---

## **37. What does JaCoCo measure?**

* **Line coverage**
* **Instruction coverage**
* **Branch coverage**
* **Cyclomatic complexity**

But **coverage does NOT guarantee correctness**.

---

## **38. What is the difference between JaCoCo and SpotBugs?**

| Tool           | Purpose                          |
| -------------- | -------------------------------- |
| **JaCoCo**     | Measures test execution coverage |
| **SpotBugs**   | Detects static code issues       |
| **Checkstyle** | Enforces coding standards        |

---

## **39. What issues does SpotBugs detect?**

* Null pointer risks
* Thread-safety issues
* Performance problems
* Wrong API usage
* Serialization mistakes

It prevents real production bugs.

---

## **40. Why do companies use Checkstyle?**

To enforce:

* Consistent coding standards
* Naming conventions
* Curly brace style
* Line length limits
* Javadoc requirements

Reduces code review burden.

---

---

# 🎯 **Bonus: TDD (Test Driven Development)**

If you'd like, I can add **20 more TDD-specific Q&A** (Red-Green-Refactor, mocks in TDD, how to write testable code, anti patterns, etc.).

---

# ✅ Want me to generate this entire content as a **PDF**, **blog article**, or **Notion-ready document**?


Below is a **clear, complete, interview-level difference** between **JUnit 4 and JUnit 5**.

---

# ⭐ **Major Differences Between JUnit 4 and JUnit 5**

![Image](https://pbs.twimg.com/media/C_7DGAJUIAAHEpg.jpg?utm_source=chatgpt.com)

![Image](https://miro.medium.com/1%2AsHTLQ58DERSLBdZPpq-elg.png?utm_source=chatgpt.com)

![Image](https://www.softwaretestinghelp.com/wp-content/qa/uploads/2019/12/sequential-workflow-of-the-lifecycle-annotations-for-JUnit-4.1.png?utm_source=chatgpt.com)

---

# 🔥 **1. Architecture (Most Important Difference)**

### ✅ **JUnit 4**

* Monolithic (everything in one jar → `junit-4.x.jar`)
* No modularity
* Tight coupling of runner, assertions, rules

### ✅ **JUnit 5**

* Modular and extensible
* **Three major components**:

  1. *JUnit Platform*
  2. *JUnit Jupiter* (new APIs)
  3. *JUnit Vintage* (runs JUnit 4 tests)

➡ **JUnit 5 = platform + multiple engines**, while JUnit 4 = single engine.

---

# 🔥 **2. Annotations Differences**

| Feature                   | JUnit 4                                        | JUnit 5                               |
| ------------------------- | ---------------------------------------------- | ------------------------------------- |
| Test method               | `@Test`                                        | `@Test`                               |
| Setup before each test    | `@Before`                                      | `@BeforeEach`                         |
| Setup once before class   | `@BeforeClass`                                 | `@BeforeAll`                          |
| Teardown after each test  | `@After`                                       | `@AfterEach`                          |
| Teardown once after class | `@AfterClass`                                  | `@AfterAll`                           |
| Ignoring a test           | `@Ignore`                                      | `@Disabled`                           |
| Parameterized tests       | In `org.junit.runners.Parameterized` (complex) | Native support (`@ParameterizedTest`) |
| Display name              | No                                             | `@DisplayName`                        |
| Test factory              | No                                             | `@TestFactory`                        |

JUnit 5 annotation names are **more meaningful and consistent**.

---

# 🔥 **3. Test Lifecycle Improvements**

### JUnit 4:

* Test class instantiated **once per class**.
* Methods annotated with @Before, @After reuse same object → dangerous for stateful code.

### JUnit 5:

* Default lifecycle is **PER_METHOD** → a new instance per test.
* Can change to PER_CLASS using:

```java
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
```

---

# 🔥 **4. Assertions and Assumptions**

### JUnit 4:

* Assertions from `org.junit.Assert` (static methods)
* Limited expressiveness

### JUnit 5:

* More expressive assertions in `org.junit.jupiter.api.Assertions`
* `assertAll`, `assertThrows`, `assertTimeout`
* Better assumptions for conditional testing

---

# 🔥 **5. Extension Model (Huge Upgrade)**

### JUnit 4:

* Used **Rules** and **Runners**
* Only one Runner allowed per test class → limitation

### JUnit 5:

* Powerful **Extension API**
* Multiple extensions allowed
* Supports:

  * Lifecycle callbacks
  * Parameter resolvers
  * Exception handlers
  * Conditional test execution

Used widely for:

* Spring Test
* Mockito Extension
* Testcontainers

---

# 🔥 **6. Java Compatibility**

| JUnit Version | Java Requirement |
| ------------- | ---------------- |
| JUnit 4       | Java 5+          |
| JUnit 5       | Java 8+          |

JUnit 5 uses modern Java features (lambdas, streams).

---

# 🔥 **7. Parameterized Tests**

### JUnit 4:

* Complicated
* Required custom runners
* Bad readability

### JUnit 5:

* Built-in support with annotations:

  * `@ValueSource`
  * `@CsvSource`
  * `@MethodSource`

Much cleaner and readable.

---

# 🔥 **8. Test Discovery**

### JUnit 4:

* Tests discovered only by naming conventions and annotations.

### JUnit 5:

* Test discovery via platform launcher
* More flexible
* IDEs integrate better (IntelliJ, Eclipse, VS Code)

---

# 🔥 **9. Backward Compatibility**

### JUnit 4:

* Cannot run JUnit 5 tests.

### JUnit 5:

* Has **JUnit Vintage Engine** → can run JUnit 4 tests.
  This allows gradual migration.

---

# 🔥 **10. Tagging & Filtering**

### JUnit 4:

* Categories via `@Category`, required external Runner.

### JUnit 5:

* Powerful tagging with `@Tag`
* Filter tests easily via Maven/Gradle.

---

# 🔥 Quick Summary Table

| Feature                | JUnit 4         | JUnit 5                              |
| ---------------------- | --------------- | ------------------------------------ |
| Architecture           | Single module   | Modular (Platform, Jupiter, Vintage) |
| Parameterized Tests    | Hard to write   | Built-in with annotations            |
| Extensions             | Runners/Rules   | Powerful Extension API               |
| Annotations            | @Before, @After | @BeforeEach, @AfterEach              |
| Lifecycle              | Single instance | PER_METHOD by default                |
| Assertions             | Limited         | Rich & expanded                      |
| Backward Compatibility | No              | Yes (Vintage)                        |
| Java Support           | Java 5          | Java 8+                              |

---

# 🎯 One-Line Interview Answer

**“JUnit 4 is monolithic and limited, while JUnit 5 is modular, extensible, supports modern Java features, and provides a much richer test model with better annotations, parameterized tests, and extension mechanisms.”**

---

If you want, I can also prepare:

✔ **JUnit 4 → JUnit 5 migration cheat sheet**
✔ **20 interview questions only on JUnit 5**
✔ **Side-by-side code comparison for each feature**

Just tell me!
