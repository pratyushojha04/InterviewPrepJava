Below is **the most detailed, systematic, interview-oriented, code-supported explanation** of the **Collections Framework** you’ll find — covering **List, Set, Map, Queue, Deque**, their implementations, iteration rules, fail-fast behavior, and working demos **with comments + input/output** exactly as you requested.

---

# ✅ **JAVA COLLECTIONS FRAMEWORK — FULL DETAILED NOTES + DEMOS**

The **Java Collections Framework (JCF)** is a unified architecture to store, manipulate, and retrieve groups of objects efficiently.
It provides **interfaces, implementations, and algorithms**.

---

# ⭐ **TOP-LEVEL COLLECTION INTERFACES**

```
Iterable
  └── Collection
        ├── List
        ├── Set
        └── Queue
              └── Deque
Map   (separate hierarchy)
```

---

# --------------------------------------------------------------

# 🔵 **1) LIST INTERFACE**

Ordered, indexed, allows duplicates.

Common Implementations:

* **ArrayList**
* **LinkedList**
* **Vector / Stack** (legacy)

### Common List operations:

```
add(E)
add(int index, E)
get(int index)
set(int index, E)
remove(int index)
remove(Object)
contains(E)
indexOf(E)
size()
isEmpty()
```

---

# 🔥 **1.1 ARRAYLIST — Full Theory**

### Internal Working

* Backed by **dynamic array**.
* When capacity is exceeded → grows by **1.5x**.
* **Fast: get(), set()**
* **Slow: add(index), remove(index)** (shifting occurs)

### Maintains Insertion Order

### Allows duplicates

### Allows null

---

## ✅ **ArrayList — Full Code Demo**

```java
import java.util.*;

public class ArrayListDemo {
    public static void main(String[] args) {

        // Creating ArrayList
        ArrayList<Integer> list = new ArrayList<>();   // default initial capacity = 10

        // Adding elements
        list.add(10);                // add(E) → append
        list.add(20);
        list.add(30);
        list.add(1, 15);             // add(index, element)

        // Fetching elements
        int val = list.get(2);       // get(index)

        // Updating element
        list.set(2, 25);             // set(index, element)

        // Removing elements
        list.remove(1);              // remove(index)
        list.remove(Integer.valueOf(25));  // remove(Object)

        // Iteration using for-each
        System.out.println("For-each:");
        for (int x : list) System.out.println(x);

        // Using Iterator
        System.out.println("Iterator:");
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            int x = it.next();
            if (x == 30) it.remove();  // Safe removal
        }

        System.out.println("Final List: " + list);
    }
}
```

### **OUTPUT**

```
For-each:
10
25
30

Iterator:
Final List: [10, 25]
```

---

# 🔥 **1.2 LINKEDLIST — Full Theory**

### Internal Working

Doubly Linked List:

```
prev ← node → next
```

### Performance:

| Operation           | LinkedList    | ArrayList          |
| ------------------- | ------------- | ------------------ |
| add/remove at index | ❌ slow (O(n)) | ❌ slow (O(n))      |
| add/remove at ends  | ✔ fast (O(1)) | ✔ fast at end only |
| get(index)          | ❌ O(n)        | ✔ O(1)             |

### Best Use:

* Queue, Deque
* Frequent insert/delete operations

---

## ✅ **LinkedList — Full Demo**

```java
import java.util.*;

public class LinkedListDemo {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();

        list.add("A");                // add(E)
        list.addFirst("Start");       // specific to LinkedList
        list.addLast("End");

        list.removeFirst();           // remove first node
        list.remove("End");           // remove object

        // Iteration
        for (String s : list) {
            System.out.println(s);
        }
    }
}
```

### OUTPUT

```
A
```

---

# --------------------------------------------------------------

# 🔵 **2) SET INTERFACE**

Unordered, **no duplicates**.

Implemented by:

* **HashSet**
* **LinkedHashSet**
* **TreeSet**

### Common Set methods:

```
add(E)
remove(E)
contains(E)
size()
isEmpty()
iterator()
```

---

# 🔥 **2.1 HASHSET — Full Theory**

### Characteristics:

* Uses **HashMap internally**.
* No order guarantee.
* **Fast lookup (O(1))** average.
* Allows **one null**.

### Internal working:

```
hashCode() → bucket index → equals()
```

---

## ✅ **HashSet — Demo**

```java
import java.util.*;

public class HashSetDemo {
    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>();

        set.add("Java");
        set.add("Python");
        set.add("Java");   // duplicate ignored
        set.add(null);

        for (String s : set) {
            System.out.println(s);
        }
    }
}
```

### OUTPUT (order varies)

```
null
Java
Python
```

---

# 🔥 **2.2 TREESET — Full Theory**

* Sorted Set (Red-Black Tree)
* **No null allowed**
* Operations: O(log n)

Sorts either by:

* Natural ordering (Comparable)
* Custom ordering (Comparator)

---

## ✅ **TreeSet — Demo**

```java
import java.util.*;

public class TreeSetDemo {
    public static void main(String[] args) {
        TreeSet<Integer> ts = new TreeSet<>();

        ts.add(50);
        ts.add(10);
        ts.add(40);

        System.out.println(ts); // sorted
    }
}
```

### OUTPUT

```
[10, 40, 50]
```

---

# --------------------------------------------------------------

# 🔵 **3) MAP INTERFACE**

Key-value pairs, **keys cannot duplicate**.

Implementations:

* **HashMap**
* **LinkedHashMap**
* **TreeMap**

### Common Map operations:

```
put(K,V)
get(K)
containsKey(K)
remove(K)
keySet()
values()
entrySet()
```

---

# 🔥 **3.1 HASHMAP — Full Theory**

### Characteristics:

* Stores entries in **buckets (array + linked list/tree)**.
* Allows **one null key**, many null values.
* No order.
* **O(1)** average performance.

### Internal Working:

```
hashCode() → bucket index → equals() for collision
```

---

## ✅ **HashMap — Demo**

```java
import java.util.*;

public class HashMapDemo {
    public static void main(String[] args) {

        HashMap<Integer, String> map = new HashMap<>();

        map.put(101, "A");
        map.put(102, "B");
        map.put(101, "C");  // overwrites value for 101

        // Iterating using entrySet
        for (Map.Entry<Integer, String> e : map.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }

        map.remove(102);
        System.out.println("After removing 102: " + map);
    }
}
```

### OUTPUT

```
101 -> C
102 -> B
After removing 102: {101=C}
```

---

# 🔥 **3.2 LINKEDHASHMAP — Full Theory**

* Preserves **insertion order**.
* Slightly slower than HashMap.
* Still O(1).

---

## Demo

```java
LinkedHashMap<Integer, String> lhm = new LinkedHashMap<>();
lhm.put(1,"A");
lhm.put(2,"B");
lhm.put(3,"C");

System.out.println(lhm);
```

**OUTPUT**

```
{1=A, 2=B, 3=C}
```

---

# --------------------------------------------------------------

# 🔵 **4) QUEUE INTERFACE**

FIFO (First In, First Out)

Common implementations:

* **LinkedList**
* **PriorityQueue**

### Core methods

```
add(e)
offer(e)
remove()
poll()
peek()
element()
```

Difference:

| Method   | Behavior                    |
| -------- | --------------------------- |
| add()    | throws exception on failure |
| offer()  | returns false on failure    |
| remove() | throws exception if empty   |
| poll()   | returns null if empty       |

---

## PriorityQueue Demo

```java
PriorityQueue<Integer> pq = new PriorityQueue<>(); // min-heap

pq.offer(30);
pq.offer(10);
pq.offer(20);

System.out.println(pq.poll()); // 10
System.out.println(pq.poll()); // 20
```

---

# --------------------------------------------------------------

# 🔵 **5) DEQUE INTERFACE**

Double-ended queue.

Implementations:

* **ArrayDeque**
* **LinkedList**

Methods:

```
addFirst(), addLast()
offerFirst(), offerLast()
removeFirst(), removeLast()
peekFirst(), peekLast()
```

---

## ArrayDeque Demo

```java
ArrayDeque<Integer> dq = new ArrayDeque<>();

dq.addFirst(10);
dq.addLast(20);
dq.addFirst(5);

System.out.println(dq.removeLast()); // 20
```

---

# --------------------------------------------------------------

# 🔥 FAIL-FAST BEHAVIOR (VERY IMPORTANT FOR INTERVIEWS)

If collection is modified structurally while iterating using `Iterator`, it throws:

```
java.util.ConcurrentModificationException
```

### Example that FAILS:

```java
ArrayList<Integer> list = new ArrayList<>();
list.add(1); list.add(2); list.add(3);

for (int x : list) {
    list.remove(Integer.valueOf(2));  // ❌ causes fail-fast
}
```

### Safe way → `Iterator.remove()`

```java
Iterator<Integer> it = list.iterator();
while (it.hasNext()) {
    int x = it.next();
    if (x == 2) it.remove();  // ✔ SAFE
}
```

---

# --------------------------------------------------------------
Perfect — here is **the most detailed and complete explanation** of:

# ✅ **Generics (Parameterized Types, Wildcards)**

# ✅ **Java 8: Lambdas, Functional Interfaces, Method References**

# ✅ **Streams API (Intermediate / Terminal operations)**

# ✅ **Optional (java.util.Optional)**

Everything includes **theory + diagrams + full code demos + comments + input/output** exactly in your preferred format.

---

# ---------------------------------------------------------

# 🔵 **1) GENERICS IN JAVA**

Generics allow classes, interfaces, and methods to operate on **parameterized types**.

### ⭐ Why Generics?

✔ Compile-time type safety
✔ Avoids `ClassCastException`
✔ Eliminates boilerplate casting
✔ Generic algorithms & data structures

---

# 🔥 **1.1 PARAMETERIZED TYPES**

### Example: A Generic Class

```java
// T is a type parameter
class Box<T> {
    private T value;

    public void set(T value) { this.value = value; }  // setter
    public T get() { return value; }                  // getter
}

public class GenericDemo {
    public static void main(String[] args) {

        Box<Integer> intBox = new Box<>();     // T = Integer
        intBox.set(10);
        System.out.println(intBox.get());      // Output: 10

        Box<String> strBox = new Box<>();      // T = String
        strBox.set("Hello");
        System.out.println(strBox.get());      // Output: Hello
    }
}
```

### OUTPUT

```
10
Hello
```

---

# 🔥 **1.2 GENERIC METHOD**

```java
public class GenericMethods {

    // T is type param for method
    public static <T> void print(T value) {
        System.out.println("Value: " + value);
    }

    public static void main(String[] args) {
        print(100);          // T = Integer
        print("Java");       // T = String
    }
}
```

### OUTPUT

```
Value: 100
Value: Java
```

---

# ---------------------------------------------------------

# 🔵 **1.3 WILDCARDS — ?**

Wildcards allow **flexible** generic usage when the exact type is not known.

---

# ⭐ **1.3.1 ? extends T (Upper Bounded Wildcard)**

Used when you want to **read** from a structure, but not add.

```
List<? extends Number>
→ List of any subtype of Number (Integer, Double, etc.)
```

### Example:

```java
public static void printList(List<? extends Number> list) {
    for (Number n : list) {            // safe: reading allowed
        System.out.println(n);
    }
    // list.add(10); ❌ NOT allowed
}
```

Reason: Type could be List<Double> → inserting Integer would be unsafe.

---

# ⭐ **1.3.2 ? super T (Lower bounded wildcard)**

Used when you want to **write** into a structure.

```
List<? super Integer>
→ List of Integer or its super types (Number, Object)
```

### Example:

```java
public static void addNums(List<? super Integer> list) {
    list.add(10);    // ✔ allowed (Integer is acceptable)
    list.add(20);
}
```

But reading gives `Object`, not Integer.

---

# ⭐ **1.3.3 Unbounded Wildcard: ?**

Used when type doesn’t matter.

```java
List<?> list = new ArrayList<String>();
```

Cannot add anything except `null`.

---

# ---------------------------------------------------------

# 🔵 **2) JAVA 8 FEATURES**

Includes:
✔ Lambda Expressions
✔ Functional Interfaces
✔ Method References
✔ Stream API
✔ Optional

---

# ---------------------------------------------------------

# 🔵 **2.1 LAMBDA EXPRESSIONS**

A lambda is a concise way to implement a functional interface.

### Syntax:

```
(parameters) -> expression
(parameters) -> { statements }
```

---

### Example — Comparator without Lambda

```java
Comparator<Integer> c = new Comparator<Integer>() {
    public int compare(Integer a, Integer b) {
        return Integer.compare(a, b);
    }
};
```

### Same using Lambda

```java
Comparator<Integer> c = (a, b) -> Integer.compare(a, b);
```

---

## Full Lambda Demo

```java
import java.util.*;

public class LambdaDemo {
    public static void main(String[] args) {
        
        List<Integer> list = Arrays.asList(5, 1, 4, 2, 3);

        // Lambda for sorting
        list.sort((a, b) -> a - b);   // ascending

        // Lambda for iteration
        list.forEach(x -> System.out.println(x));
    }
}
```

### OUTPUT

```
1
2
3
4
5
```

---

# ---------------------------------------------------------

# 🔵 **2.2 FUNCTIONAL INTERFACE**

An interface with **exactly one abstract method**.

Examples:

* Runnable
* Callable
* Comparator
* java.util.function package

### Custom Example:

```java
@FunctionalInterface
interface Calculator {
    int add(int a, int b);   // Single Abstract Method
}

public class FI {
    public static void main(String[] args) {

        Calculator c = (a, b) -> a + b;   // lambda implementation

        System.out.println(c.add(10, 20));
    }
}
```

### OUTPUT

```
30
```

---

# ---------------------------------------------------------

# 🔵 **2.3 METHOD REFERENCES**

Shortcut for lambdas when existing methods can be reused.

### Types:

1. **object::instanceMethod**
2. **Class::staticMethod**
3. **Class::instanceMethod**

---

### Example — static method reference

```java
public class MR {
    public static void print(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList("A", "B", "C");

        list.forEach(MR::print);  // method reference
    }
}
```

### OUTPUT

```
A
B
C
```

---

# ---------------------------------------------------------

# 🔵 **3) STREAMS API — FULL DETAILS**

Introduced in Java 8 for **functional processing** of collections.

Pipeline:

```
Source → Intermediate Ops → Terminal Op
```

---

# ⭐ **3.1 INTERMEDIATE OPERATIONS** (lazy)

They **return a Stream**.

| Operation  | Meaning                |
| ---------- | ---------------------- |
| map()      | transform elements     |
| filter()   | keep matching elements |
| sorted()   | sort stream            |
| distinct() | remove duplicates      |
| limit(n)   | first n elements       |
| skip(n)    | skip first n           |

---

# ⭐ **3.2 TERMINAL OPERATIONS** (consume the stream)

| Operation              | Meaning                   |
| ---------------------- | ------------------------- |
| forEach()              | iterate                   |
| collect()              | convert to List/Set/Map   |
| reduce()               | combine into single value |
| count()                | count items               |
| anyMatch(), allMatch() | boolean checks            |

---

# 🔥 Full Stream Demo (map, filter, collect)

```java
import java.util.*;
import java.util.stream.*;

public class StreamDemo {
    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        // Pipeline: filter even → square → collect to list
        List<Integer> result = list.stream()
                .filter(x -> x % 2 == 0)     // keep evens
                .map(x -> x * x)             // square
                .collect(Collectors.toList());

        System.out.println(result);
    }
}
```

### OUTPUT

```
[4, 16]
```

---

# ---------------------------------------------------------

# 🔵 **3.3 MORE STREAM EXAMPLES**

### Reduce

```java
int sum = list.stream().reduce(0, (a,b) -> a + b);
```

### Count

```java
long count = list.stream().filter(x -> x > 3).count();
```

### Convert to Map

```java
Map<Integer,String> map = list.stream()
    .collect(Collectors.toMap(x -> x, x -> "Val:" + x));
```

---

# ---------------------------------------------------------

# 🔵 **4) OPTIONAL — TO MODEL ABSENCE**

`Optional<T>` avoids `NullPointerException`.

### Common Methods

| Method            | Purpose               |
| ----------------- | --------------------- |
| empty()           | create empty Optional |
| of(value)         | non-null value        |
| ofNullable(value) | null allowed          |
| isPresent()       | check                 |
| ifPresent()       | run action            |
| orElse()          | fallback              |
| orElseGet()       | supplier fallback     |
| orElseThrow()     | throw exception       |

---

## Full Demo with Optional

```java
import java.util.Optional;

public class OptionalDemo {
    public static void main(String[] args) {

        Optional<String> op1 = Optional.of("Hello");

        Optional<String> op2 = Optional.ofNullable(null);

        // get value or fallback
        String val1 = op1.orElse("Default");
        String val2 = op2.orElse("Default");

        System.out.println(val1);   // Hello
        System.out.println(val2);   // Default

        // ifPresent example
        op1.ifPresent(s -> System.out.println("Value exists: " + s));
    }
}
```

### OUTPUT

```
Hello
Default
Value exists: Hello
```

---

# ---------------------------------------------------------
# Streamapi Interview questions

---

# 🚀 **TOP 30 PRACTICAL STREAM API INTERVIEW QUESTIONS (With Code + I/O)**

---

# ✅ **1. Find even numbers from a list**

```java
import java.util.*;
import java.util.stream.*;

public class Q1 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6);

        List<Integer> ans = list.stream()
                .filter(x -> x % 2 == 0)
                .collect(Collectors.toList());

        System.out.println(ans);
    }
}
```

### Output

```
[2, 4, 6]
```

---

# ✅ **2. Square all numbers**

```java
List<Integer> ans = list.stream()
        .map(x -> x * x)
        .collect(Collectors.toList());
```

---

# ✅ **3. Find numbers starting with digit “1”**

```java
List<String> list = Arrays.asList("10","2","14","30");

List<String> ans = list.stream()
        .filter(s -> s.startsWith("1"))
        .collect(Collectors.toList());
```

---

# ✅ **4. Find duplicates from list**

```java
Set<Integer> set = new HashSet<>();

List<Integer> dup = list.stream()
        .filter(x -> !set.add(x))
        .collect(Collectors.toList());
```

---

# ✅ **5. Find first element of stream**

```java
int first = list.stream()
        .findFirst()
        .orElse(-1);
```

---

# ✅ **6. Find max element**

```java
int max = list.stream()
        .max(Integer::compare)
        .get();
```

---

# ✅ **7. Find min element**

```java
int min = list.stream()
        .min(Integer::compare)
        .get();
```

---

# ✅ **8. Sum of numbers**

```java
int sum = list.stream().reduce(0, (a,b) -> a + b);
```

---

# ✅ **9. Count elements greater than 10**

```java
long count = list.stream().filter(x -> x > 10).count();
```

---

# ✅ **10. Sort a list**

```java
List<Integer> sorted = list.stream()
        .sorted()
        .collect(Collectors.toList());
```

---

# ✅ **11. Sort a list in reverse order**

```java
List<Integer> sorted = list.stream()
        .sorted((a,b) -> b - a)
        .collect(Collectors.toList());
```

---

# ✅ **12. Convert List<String> to List<Integer>**

```java
List<Integer> nums = list.stream()
        .map(Integer::parseInt)
        .collect(Collectors.toList());
```

---

# ✅ **13. Join list of strings into a single string**

```java
String str = list.stream()
        .collect(Collectors.joining(","));
```

---

# ✅ **14. Remove null values from list**

```java
List<String> cleaned = list.stream()
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
```

---

# ✅ **15. Convert list to a Set**

```java
Set<Integer> set = list.stream().collect(Collectors.toSet());
```

---

# ✅ **16. Convert list to a Map (index → value)**

```java
Map<Integer,String> map =
        list.stream().collect(Collectors.toMap(
                x -> list.indexOf(x),
                x -> x
        ));
```

---

# ✅ **17. Find average**

```java
double avg = list.stream()
        .mapToInt(x -> x)
        .average()
        .orElse(0);
```

---

# ✅ **18. Find second highest number**

```java
int second = list.stream()
        .sorted(Comparator.reverseOrder())
        .skip(1)
        .findFirst()
        .orElse(-1);
```

---

# ✅ **19. Group strings by their length**

```java
Map<Integer,List<String>> map = list.stream()
        .collect(Collectors.groupingBy(String::length));
```

---

# ✅ **20. Partition numbers into even/odd**

```java
Map<Boolean,List<Integer>> map =
        list.stream()
            .collect(Collectors.partitioningBy(x -> x % 2 == 0));
```

---

# ✅ **21. Find frequency of each element**

```java
Map<Integer, Long> freq = list.stream()
        .collect(Collectors.groupingBy(x -> x, Collectors.counting()));
```

---

# ✅ **22. Remove duplicates**

```java
List<Integer> unique = list.stream()
        .distinct()
        .collect(Collectors.toList());
```

---

# ✅ **23. Find any element**

```java
int val = list.stream().findAny().orElse(-1);
```

---

# ✅ **24. Convert array to stream**

```java
int[] arr = {1, 2, 3};
int sum = Arrays.stream(arr).sum();
```

---

# ✅ **25. Get last element using streams**

```java
int last = list.stream()
        .skip(list.size() - 1)
        .findFirst()
        .orElse(-1);
```

---

# ✅ **26. Filter employees by salary > X**

```java
class Emp {
    int id; double salary;
    Emp(int id,double salary){ this.id=id;this.salary=salary; }
}

List<Emp> ans = emps.stream()
        .filter(e -> e.salary > 50000)
        .collect(Collectors.toList());
```

---

# ✅ **27. Find highest-paid employee**

```java
Emp max = emps.stream()
        .max(Comparator.comparing(e -> e.salary))
        .get();
```

---

# ✅ **28. Sort employees by salary**

```java
List<Emp> sorted = emps.stream()
        .sorted(Comparator.comparing(e -> e.salary))
        .collect(Collectors.toList());
```

---

# ✅ **29. Convert List<Emp> → Map<id, salary>**

```java
Map<Integer, Double> map = emps.stream()
        .collect(Collectors.toMap(
                e -> e.id,
                e -> e.salary
        ));
```

---

# ✅ **30. FlatMap example — Convert List<List<Integer>> → List<Integer>**

```java
List<List<Integer>> big = Arrays.asList(
        Arrays.asList(1,2),
        Arrays.asList(3,4)
);

List<Integer> flat = big.stream()
        .flatMap(x -> x.stream())
        .collect(Collectors.toList());

System.out.println(flat);
```

### Output

```
[1, 2, 3, 4]
```

# --------
# --------



