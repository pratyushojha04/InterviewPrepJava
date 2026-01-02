

# ✅ **1. Class**

A **class** is a blueprint or template.

It defines:

* How an object looks → **fields/attributes**
* How an object behaves → **methods**
* How an object is created → **constructor**

### Example:

```java
class Car {
    String model;
    int speed;

    void drive() {
        System.out.println(model + " is driving");
    }
}
```

---

# ✅ **2. Object**

An **object** is a real instance created from the class.

You create an object using the `new` keyword.

```java
Car c1 = new Car();
Car c2 = new Car();
```

Each object has its **own copy** of the fields.

---

# ✅ **3. Fields (Instance Variables)**

Fields store the data/state of an object.

```java
class Car {
    String model;    // field
    int speed;       // field
}
```

Access fields using object:

```java
c1.model = "BMW";
c1.speed = 120;
```

Each object has independent values.

---

# ✅ **4. Constructors**

A **constructor** is used to **initialize the object**.

### Rules:

✔ Same name as class
✔ No return type (not even void)
✔ Called automatically when `new` object is created

---

## 🔹 **Default Constructor (provided by Java)**

```java
Car c = new Car();   // works even if no constructor defined
```

Java creates a constructor like:

```java
Car() {}
```

---

## 🔹 **User-defined Constructor**

```java
class Car {
    String model;
    int speed;

    Car(String m, int s) {
        model = m;
        speed = s;
    }
}
```

Create object:

```java
Car c1 = new Car("BMW", 120);
Car c2 = new Car("Audi", 140);
```

---

# 🔹 **Constructor Overloading**

You can define multiple constructors with different parameters.

```java
class Car {
    String model;
    int speed;

    Car() {
        model = "Unknown";
        speed = 0;
    }

    Car(String model) {
        this.model = model;
        speed = 0;
    }

    Car(String model, int speed) {
        this.model = model;
        this.speed = speed;
    }
}
```

Usage:

```java
Car c1 = new Car();
Car c2 = new Car("BMW");
Car c3 = new Car("Audi", 120);
```

---

# 🔥 **this keyword (Important)**

`this` refers to the **current object**.

Used when parameter name = field name.

```java
class Car {
    String model;

    Car(String model) {
        this.model = model; // left: field, right: parameter
    }
}
```

---

# 🎯 Summary Table

| Concept     | Meaning            | Example              |
| ----------- | ------------------ | -------------------- |
| Class       | Blueprint          | `class Car {}`       |
| Object      | Real instance      | `Car c = new Car()`  |
| Field       | Data inside object | `String model;`      |
| Constructor | Initializes object | `Car(String m){...}` |

---

---
---

Here is a **clear, structured, interview-ready explanation** of **this, static, visibility modifiers, encapsulation, and immutability** in Java.

---

# ✅ **1. `this` Keyword**

`this` refers to **the current object** — the object on which the method or constructor is running.

### ✔ Uses of `this`:

### **1. To access instance variables**

When parameter name = field name:

```java
class Student {
    String name;

    Student(String name) {
        this.name = name;  // this.name = field, name = parameter
    }
}
```

---

### **2. To call other constructors (constructor chaining)**

```java
class Student {
    Student() {
        this("Unknown");  // calling another constructor
    }

    Student(String name) {
        System.out.println(name);
    }
}
```

---

### **3. To pass the current object as argument**

```java
class Test {
    void show() {
        helper(this);
    }

    void helper(Test obj) {}
}
```

---

# ✅ **2. static Keyword**

`static` means:
➡ belongs to the **class**,
➡ NOT to the object,
➡ shared by all objects.

---

## ✔ static variable (class variable)

```java
class Counter {
    static int count = 0;

    Counter() {
        count++;
    }
}
```

`count` is shared among all Counter objects.

---

## ✔ static method

```java
static void greetings() {
    System.out.println("Hello!");
}
```

Call using class name:

```java
Counter.greetings();  
```

⚠ static methods **cannot use non-static things**
because non-static data belongs to objects.

---

## ✔ static block

Runs **once** when the class loads.

```java
static {
    System.out.println("Class loaded");
}
```

---

## ✔ static class (only inner classes can be static)

```java
class Outer {
    static class Inner {}
}
```

---

# ✅ **3. Visibility Modifiers (Access Modifiers)**

| Modifier                   | Same Class | Same Package | Subclass | Other Packages |
| -------------------------- | ---------- | ------------ | -------- | -------------- |
| **public**                 | ✔          | ✔            | ✔        | ✔              |
| **protected**              | ✔          | ✔            | ✔        | ✖              |
| **default** *(no keyword)* | ✔          | ✔            | ✖        | ✖              |
| **private**                | ✔          | ✖            | ✖        | ✖              |

---

# ✔ Summary in simple words:

* **public** → everywhere
* **protected** → package + subclasses
* **default** → package only
* **private** → same class only

---

# Examples

```java
public class A {
    private int x;        // only A
    int y;                // default: package-only
    protected int z;      // package + subclasses
    public int w;         // everywhere
}
```

---

# ✅ **4. Encapsulation**

Encapsulation =
**Hiding internal data and exposing only what is necessary**, usually through **getters/setters**.

### Why?

✔ Control how data is accessed
✔ Prevent invalid data
✔ Improve security
✔ Make classes easier to maintain
✔ Core principle of OOP

---

### Example:

```java
class BankAccount {
    private double balance;   // hidden

    public double getBalance() {   // controlled access
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0)
            balance += amount;
    }
}
```

Fields are private
Access through methods → encapsulation.

---

# ✅ **5. Immutability**

Immutable object → state cannot change after creation.

Example: **String** in Java.

### Requirements to make an immutable class:

1. Class should be marked **final**
2. All fields should be **private + final**
3. No setters
4. If class contains mutable fields, return **deep copies**

---

### Example: Immutable class

```java
final class Person {
    private final String name;
    private final int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;    // no setter
    }

    public int getAge() {
        return age;
    }
}
```

Once created:

```java
Person p = new Person("Alice", 22);
```

You cannot change its values → **immutable**.

---

Here is a **clean, structured, interview-level explanation** of **Inheritance, super, abstract classes, interfaces, default/static methods** — all essential for Java OOP.

---

# ✅ **1. Inheritance**

Inheritance allows one class to **acquire properties and behaviors** of another class.

`child class extends parent class`

### Example

```java
class Vehicle {
    int speed;

    void run() {
        System.out.println("Vehicle is running");
    }
}

class Car extends Vehicle {
    void honk() {
        System.out.println("Car horn");
    }
}
```

`Car` now has:

* `speed`
* `run()`
* and its own `honk()` method

---

# ⭐ **Types of Inheritance in Java**

| Type                      | Supported? | Example                       |
| ------------------------- | ---------- | ----------------------------- |
| Single                    | ✔          | A → B                         |
| Multilevel                | ✔          | A → B → C                     |
| Hierarchical              | ✔          | A → B, A → C                  |
| Multiple (via class)      | ✖          | Not allowed (Diamond problem) |
| Multiple (via interfaces) | ✔          | Allowed                       |

---

# 🚫 Why no multiple inheritance with classes?

Because if two parent classes have same method → ambiguity.

Java solves this using **interfaces**.

---

# ✅ **2. `super` Keyword**

`super` refers to the **parent class**.

### Uses of super:

---

## ✔ 1. Access parent class fields & methods

```java
class Parent {
    int x = 10;
}

class Child extends Parent {
    int x = 20;

    void show() {
        System.out.println(super.x);  // parent’s x
        System.out.println(this.x);   // child’s x
    }
}
```

---

## ✔ 2. Call parent class constructor

```java
class Parent {
    Parent() {
        System.out.println("Parent constructor");
    }
}

class Child extends Parent {
    Child() {
        super();   // must be first line
        System.out.println("Child constructor");
    }
}
```

---

# ✔ 3. Call parent class method

```java
class Parent {
    void show() {
        System.out.println("Parent");
    }
}

class Child extends Parent {
    void show() {
        super.show();     // call parent method
        System.out.println("Child");
    }
}
```

---

# ✅ **3. Abstract Classes**

An abstract class:

* cannot be instantiated
* can have both abstract & non-abstract methods
* used when you want **partial abstraction**

### Syntax

```java
abstract class Animal {
    abstract void sound();   // no body
    void eat() {
        System.out.println("Eating...");
    }
}
```

Child class MUST implement abstract methods:

```java
class Dog extends Animal {
    void sound() {
        System.out.println("Bark");
    }
}
```

---

# ⭐ When to use abstract class?

✔ When you want reuse + force child to implement some behaviors
✔ When you want constructors, fields, or non-static methods

Interfaces cannot have constructors — abstract classes can.

---

# 🔥 Differences: Abstract Class vs Interface (Quick Table)

| Feature     | Abstract Class      | Interface                                       |
| ----------- | ------------------- | ----------------------------------------------- |
| Methods     | Abstract + normal   | Only abstract (until Java 8) + default + static |
| Variables   | final? No           | always public static final                      |
| Constructor | ✔ allowed           | ✖ not allowed                                   |
| Inheritance | Only one            | Multiple allowed                                |
| Use case    | Partial abstraction | Full abstraction                                |

---

# ✅ **4. Interfaces in Java**

Interface = contract: what a class **must do**, not **how**.

### Syntax:

```java
interface Animal {
    void sound();  // abstract method by default
}
```

### Implementing an interface:

```java
class Dog implements Animal {
    public void sound() {
        System.out.println("Bark");
    }
}
```

---

# 🔹 Java allows **multiple interfaces**

```java
class SmartDog implements Animal, Pet {
    public void sound() {}
    public void play() {}
}
```

---

# ✅ **5. Default Methods in Interfaces (Java 8)**

Allows interfaces to have **method implementations**.

```java
interface Vehicle {
    default void horn() {
        System.out.println("Default horn");
    }
}
```

Classes may override it:

```java
class Car implements Vehicle {
    @Override
    public void horn() {
        System.out.println("Car horn");
    }
}
```

---

# Why default methods were added?

✔ To allow adding new methods to interfaces **without breaking old code**
✔ To support multiple inheritance of behavior

---

# 🔥 Diamond Problem with Default Methods

If two interfaces have same default method:

```java
interface A { default void show() { System.out.println("A"); }}
interface B { default void show() { System.out.println("B"); }}
```

Class must override:

```java
class C implements A, B {
    public void show() {
        A.super.show();   // choose which one to call
    }
}
```

---

# ✅ **6. Static Methods in Interfaces**

Static methods belong **only to the interface**.

```java
interface MathUtil {
    static int add(int a, int b) {
        return a + b;
    }
}
```

Call them using interface name:

```java
int x = MathUtil.add(5, 10);
```

⚠ **You cannot call interface static methods through objects or implementing classes.**

---

# 🎯 Summary (Super Important for Interviews)

### **Inheritance**

* Reuse code, avoid duplication
* Only one parent class allowed
* Multiple interface inheritance allowed

### **super**

* Access parent data/methods
* Call parent constructor

### **Abstract class**

* Partial abstraction
* Can have fields + normal methods + abstract methods

### **Interface**

* Full abstraction
* Supports multiple inheritance
* Can have **abstract**, **default**, **static** methods

---

Here is a **clean, sharp, interview-ready** explanation of **Polymorphism, Method Overriding, and Composition vs Inheritance** — exactly the level recruiters expect.

---

# ✅ **1. Polymorphism**

**Polymorphism = Many forms**

In Java, it means **one reference, multiple behaviors** depending on the object type.

There are two types:

---

# ⭐ **A. Compile-time Polymorphism (Method Overloading)**

* Resolved at compile time
* Same method name → different parameters
* You already learned this before

---

# ⭐ **B. Runtime Polymorphism (Method Overriding)**

This is the real polymorphism Java is famous for.

Example:

```java
class Animal {
    void sound() {
        System.out.println("Animal sound");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Bark");
    }
}

class Cat extends Animal {
    @Override
    void sound() {
        System.out.println("Meow");
    }
}
```

### **Key Part:**

```java
Animal a = new Dog();   // upcasting
a.sound();              // Bark (Dog version)
```

✔ Method call depends on **object**, not reference type
✔ Decided at **runtime** → runtime polymorphism

---

# ⭐ Rules for Method Overriding

1. **Method name same**
2. **Parameters same**
3. **Return type same (or covariant)**
4. Access cannot be reduced:

   * parent `public` → child must be `public`
5. Child method cannot throw **more/wider** checked exceptions
6. Only **non-static**, **non-final** methods can be overridden

---

# ⭐ Covariant Return Type

Child method can return a **subclass** type.

```java
class A { }
class B extends A { }

class Parent {
    A show() { return new A(); }
}

class Child extends Parent {
    @Override
    B show() { return new B(); }
}
```

---

# ⭐ What can’t be overridden?

* **static** methods → hidden (not overridden)
* **final** methods → cannot override
* **private** methods → cannot override (only shadowed)

---

# ⭐ Dynamic Method Dispatch

Used to achieve runtime polymorphism.

---

# ✅ **2. Composition vs Inheritance**

Both are ways to create relationships between classes.

---

# ⭐ **Inheritance (Is-a relationship)**

“class B **is a** class A”

Example:

```java
class Car extends Vehicle {}
```

✔ Use when child **is a** type of parent
✔ Reuse logic from parent
✔ Supports polymorphism

But…

❌ Tight coupling
❌ Increases complexity
❌ Not good when behavior needs frequent change
❌ Breaks if hierarchy gets deep

---

# ⭐ **Composition (Has-a relationship)**

One class contains another class.

```java
class Engine {
    void start() {}
}

class Car {
    private Engine engine = new Engine();  // Car HAS an Engine

    void startCar() {
        engine.start();
    }
}
```

Car **has an** Engine → Composition.

---

# ⭐ Composition Advantages (VERY IMPORTANT)

✔ Stronger than inheritance
✔ Low coupling
✔ More flexible
✔ Behavior can change at runtime
✔ No fragile base class problem
✔ Encouraged by modern OOP design (incl. SOLID principles)

---

### Simple rule:

| Scenario | Use         |
| -------- | ----------- |
| “is a”   | Inheritance |
| “has a”  | Composition |

Examples:

* **Dog is an Animal** → Inheritance
* **Car has an Engine** → Composition
* **House has Rooms** → Composition
* **Manager is an Employee** → Inheritance

---

# ⭐ When not to use inheritance?

If relationship is not strictly “is-a”.

Bad example:

```
class Car extends Garage   ❌ wrong
```

Car does NOT "extend" a garage.

---

# ⭐ Composition over Inheritance (OOP Principle)

Modern design prefers:

```
Use composition unless inheritance is the only logical choice.
```

---

# 🎯 Example: Same behavior using both

### Using Inheritance:

```java
class Dog extends Animal {
    void sound() { System.out.println("Bark"); }
}
```

### Using Composition:

```java
class AnimalSound {
    void bark() { System.out.println("Bark"); }
}

class Dog {
    AnimalSound sound = new AnimalSound();
    void sound() { sound.bark(); }
}
```

Composition gives more flexibility.

---

# 🎯 Quick Summary

### **Polymorphism**

* Same interface, different implementation
* Achieved via **overriding**
* Dynamic (runtime) dispatch

### **Method Overriding**

* Same method signature in parent & child
* Child version executed using runtime polymorphism

### **Inheritance**

* “is-a”
* Reuse + polymorphism
* Sometimes rigid

### **Composition**

* “has-a”
* More flexible
* Preferred in modern design

---

----
---
---
Java **intentionally does NOT support multiple inheritance of classes** because it leads to ambiguity, complexity, and several design problems.

The main reason is the famous:

---

# 🔥 **Diamond Problem (The REAL reason)**

Imagine this:

```
    A
   / \
  B   C
   \ /
    D
```

* Class **B** inherits from **A**
* Class **C** inherits from **A**
* Class **D** inherits from **B** and **C**

Now suppose class `A` has a method:

```java
void show() { ... }
```

If `D` calls:

```java
d.show();
```

### ❓ Which method should Java call?

* `A` (from B side)?
* `A` (from C side)?

This ambiguity is called the **Diamond Problem**.

Languages like C++ allow this and face huge complexity.

Java designers wanted to avoid this **confusion** and **complicated rules**.

---

# 🎯 Why Java does NOT support multiple inheritance (simple points)

### ✔️ 1. To avoid ambiguity (Diamond Problem)

Java avoids:

* confusing method resolution
* ambiguous paths
* complicated memory layout

### ✔️ 2. To keep the language simple and clean

C++ multiple inheritance is powerful but extremely complex.

Java wants:

* readability
* easy maintainability
* simpler OOP rules

### ✔️ 3. To avoid method conflicts

If two parent classes have:

```java
void show()
```

Child class cannot decide which one to inherit.

### ✔️ 4. To ensure predictable behavior

Single inheritance makes:

* method lookup simple
* less bugs
* simpler compiler

---

# ⭐ So how does Java solve the problem? → **Interfaces**

Java does NOT allow:

```java
class A {}
class B {}
class C extends A, B   // ❌ Not allowed
```

But Java allows:

```java
interface A {}
interface B {}
class C implements A, B   // ✔ Allowed
```

Why interfaces are safe?

Because:

### ✔ **Interfaces don’t carry implementation**

(Except default methods in Java 8+, and they have strict rules)

### ✔ **No state (variables) that cause conflicts**

### ✔ **Method conflict resolution is clearly defined**

If two interfaces define the same default method:

```java
default void show() {}
```

Java forces you to override it in the child class:

```java
@Override  
public void show() { ... }
```

So no ambiguity.

---

# 📌 Summary (in one line)

> **Java does not support multiple inheritance of classes because it avoids the Diamond Problem and keeps the language simple and predictable.
> Instead, Java uses interfaces to achieve multiple inheritance safely.**

---

---
---
---
Here is a **detailed, expanded, and implementation-rich explanation** of:

✔ `java.lang`
✔ `java.util`
✔ `enum`
✔ `annotations`

Each section includes **theory, diagrams, code samples, interview points, and real use cases**.

---

# 🚀 **1. java.lang — Core Language Package (Auto-Imported)**

`java.lang` is automatically imported in **every Java program**.
It contains essential classes that Java developers use constantly.

---

# ⭐ **1.1 Important Classes in `java.lang`**

---

## ✅ **1.1.1 String (Immutable Class)**

### What does *immutable* mean?

Once a `String` object is created, **you cannot modify it**.

### Example:

```java
String s = "hello";
s = s + " world"; // creates new string object
```

### Why string is immutable?

* Thread-safety
* String pool optimization
* Security

---

### String Operations:

```java
String s = "Java Programming";

// length
System.out.println(s.length());  // 16

// substring
System.out.println(s.substring(5));   // "Programming"

// charAt
System.out.println(s.charAt(0)); // 'J'

// equals
System.out.println("Java".equals("java")); // false

// compareTo
System.out.println("A".compareTo("B")); // -1
```

---

## ✅ **1.1.2 StringBuilder & StringBuffer**

Used when you want **mutable strings**.

### StringBuilder (fast, not synchronized)

```java
StringBuilder sb = new StringBuilder();
sb.append("Hello ");
sb.append("World");
System.out.println(sb.toString());
```

### StringBuffer (thread-safe)

```java
StringBuffer sb = new StringBuffer("Java");
sb.append(" Rocks");
System.out.println(sb);
```

---

## ✅ **1.1.3 Object Class**

The **parent of all Java classes**.

Important methods:

| Method       | Purpose                 |
| ------------ | ----------------------- |
| `toString()` | Convert object → String |
| `equals()`   | Compare objects         |
| `hashCode()` | Hash-based collections  |
| `clone()`    | Copy object             |

### Example (overriding toString and equals):

```java
class Person {
    String name;
    int age;

    Person(String n, int a) {
        this.name = n;
        this.age = a;
    }

    @Override
    public String toString() {
        return name + " (" + age + ")";
    }

    @Override
    public boolean equals(Object obj) {
        Person p = (Person) obj;
        return this.age == p.age && this.name.equals(p.name);
    }
}

public class Test {
    public static void main(String[] args) {
        Person p1 = new Person("Aman", 21);
        Person p2 = new Person("Aman", 21);

        System.out.println(p1); // Aman (21)
        System.out.println(p1.equals(p2)); // true
    }
}
```

---

## ✅ **1.1.4 Math Class**

Frequently used math utilities:

```java
System.out.println(Math.max(10, 20)); // 20
System.out.println(Math.sqrt(25)); // 5.0
System.out.println(Math.abs(-10)); // 10
System.out.println(Math.random()); // 0.0 < x < 1.0
```

---

## ✅ **1.1.5 System Class**

### System.out.println()

```java
System.out.println("Hello");
```

### System.currentTimeMillis()

```java
long time = System.currentTimeMillis();
```

---

## ⭐ **1.1.6 Wrapper Classes**

Convert primitives ↔ Objects.

### Examples:

```java
int x = Integer.parseInt("10");
Integer y = Integer.valueOf(20);
System.out.println(y.compareTo(x));
```

---

# ⭐ **2. java.util — Collections, Utilities & More**

This is one of the **most important** packages in Java.

```
java.util
│
├─ Collection (Interface)
│   ├─ List (Interface)
│   │    ├─ ArrayList
│   │    ├─ LinkedList
│   │
│   ├─ Set (Interface)
│   │    ├─ HashSet
│   │    ├─ LinkedHashSet
│   │    └─ TreeSet
│
└─ Map (Interface)
    ├─ HashMap
    ├─ LinkedHashMap
    └─ TreeMap
```

---

# ⭐ **2.1 List Interface (Ordered + Duplicates)**

## ✔ ArrayList

* Fast lookup
* Dynamic array

```java
ArrayList<String> list = new ArrayList<>();
list.add("Apple");
list.add("Banana");
list.add("Apple"); // duplicates allowed

System.out.println(list); // [Apple, Banana, Apple]
```

---

## ✔ LinkedList

* Fast insertion and deletion

```java
LinkedList<Integer> nums = new LinkedList<>();
nums.add(10);
nums.addFirst(5);
nums.addLast(20);
System.out.println(nums); // [5, 10, 20]
```

---

# ⭐ **2.2 Set Interface (No duplicates)**

## ✔ HashSet (fastest)

```java
Set<String> set = new HashSet<>();
set.add("A");
set.add("B");
set.add("A");
System.out.println(set); // [A, B]
```

## ✔ LinkedHashSet (keeps insertion order)

## ✔ TreeSet (sorted)

---

# ⭐ **2.3 Map Interface (Key-value pairs)**

## ✔ HashMap (most used)

```java
HashMap<String, Integer> mp = new HashMap<>();
mp.put("Aman", 21);
mp.put("Rohit", 22);

System.out.println(mp.get("Aman")); // 21
System.out.println(mp.containsKey("Rohit")); // true
```

---

## ⭐ Iterating over a HashMap:

```java
for (String key : mp.keySet()) {
    System.out.println(key + " => " + mp.get(key));
}
```

---

# ⭐ **2.4 Utility Classes**

## ✔ Arrays Class

```java
int[] arr = {3, 1, 2};
Arrays.sort(arr); // [1, 2, 3]
System.out.println(Arrays.binarySearch(arr, 2)); // 1
```

## ✔ Collections Class (for List)

```java
List<Integer> nums = Arrays.asList(3,1,2);
Collections.sort(nums);
Collections.reverse(nums);
```

---

# 🚀 **3. enum in Java**

Enums are special classes for defining fixed constants.

---

# ⭐ **3.1 Basic Example**

```java
enum Day {
    MON, TUE, WED, THU, FRI, SAT, SUN
}
```

Use it:

```java
Day d = Day.MON;
System.out.println(d);
```

---

# ⭐ **3.2 Enum with Fields, Constructor, and Methods**

```java
enum Status {
    SUCCESS(200),
    ERROR(500);

    int code;

    Status(int c) {
        this.code = c;
    }

    public int getCode() {
        return this.code;
    }
}

public class Test {
    public static void main(String[] args) {
        System.out.println(Status.SUCCESS.getCode()); // 200
    }
}
```

---

# 🚀 **4. Annotations in Java**

Annotations = **metadata** about code.

Examples:

* @Override
* @Deprecated
* @SuppressWarnings

---

# ⭐ **4.1 @Override**

Ensures method is overriding a superclass method.

```java
class A {
    void show() {}
}

class B extends A {
    @Override
    void show() {
        System.out.println("Overridden");
    }
}
```

---

# ⭐ **4.2 @Deprecated**

Marks method as outdated.

```java
@Deprecated
void oldMethod() {
    System.out.println("Don't use this");
}
```

---

# ⭐ **4.3 @SuppressWarnings**

Removes compiler warnings.

```java
@SuppressWarnings("unchecked")
ArrayList list = new ArrayList(); // raw type, but warning suppressed
```

---

# ⭐ **4.4 Custom Annotations**

Define:

```java
@interface MyAnnotation {
    String value();
}
```

Use:

```java
@MyAnnotation("hello")
class Test {}
```

---

# 🎯 **Summary Table**

| Topic       | Key Concepts                           | Examples             |
| ----------- | -------------------------------------- | -------------------- |
| java.lang   | String, Math, Object, System, Wrappers | String ops, Math.max |
| java.util   | List, Set, Map, Arrays, Collections    | ArrayList, HashMap   |
| enum        | Constants + fields + methods           | Status.SUCCESS       |
| annotations | @Override, @Deprecated, custom         | @MyAnnotation        |

---
---
---
---
# 🚀 Abstraction
---
Below is a **complete, deep, interview-ready explanation** of **Abstraction in Java**, including **why it exists, how it works internally, abstract classes, interfaces, real-world analogies, rules, memory behavior, and code examples**.

This is everything you need for interviews + practical coding.

---

# 🚀 **What Is Abstraction?**

**Abstraction = hiding implementation details and showing only essential features.**

You do NOT need to know *how* something works internally, only *what* it does.

### Real-life example:

* You drive a car using:

  * steering wheel
  * accelerator
  * brake
* You don’t care about:

  * engine design
  * brake physics
  * fuel injection

**Java does the same:**
You expose an interface (what it can do)
and hide implementation (how it works).

---

# 🎯 **Why Abstraction?**

✔ Hide complex code
✔ Increase security (you expose only required things)
✔ Reduce code coupling
✔ Improve maintainability
✔ Enable plug-and-play architecture
✔ Provide common templates (common behavior for subclasses)

---

# 🧱 **How Java Achieves Abstraction?**

Java uses:

### **1. Abstract Classes**

* Can contain both **implemented** and **unimplemented** (abstract) methods.
* Can have fields, constructors, static methods.
* “Partially abstract”.

### **2. Interfaces**

* 100% abstract (in old Java versions).
* After Java 8:

  * can have **default methods**
  * can have **static methods**
* Still used for **full abstraction**.

---

# 🏛 **1. Abstract Classes — Full Explanation**

### ➤ **Definition**

A class declared with `abstract` keyword.

```java
abstract class Animal {
    abstract void sound();   // abstract method (no body)
    
    void sleep() {          // concrete method
        System.out.println("Sleeping");
    }
}
```

---

# 📌 **Rules for Abstract Classes**

✔ Cannot be instantiated
✔ Can have constructors
✔ Can have abstract + concrete methods
✔ Can have fields (including non-final fields)
✔ Child class **must** implement all abstract methods
✔ If child does not implement them → child must also be abstract

---

# ⭐ **Example: Abstract Class with Implementation**

```java
abstract class Shape {
    abstract double area();       // abstract method

    void info() {                 // concrete method
        System.out.println("This is a shape");
    }
}
```

### Subclass must implement area():

```java
class Circle extends Shape {
    double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    double area() {               // implementing abstract method
        return Math.PI * radius * radius;
    }
}
```

### Use:

```java
public class Main {
    public static void main(String[] args) {

        Shape s = new Circle(5);   // Allowed: Upcasting
        s.info();
        System.out.println("Area = " + s.area());
    }
}
```

📌 **Abstract classes support partial abstraction.**

---

# 🏛 **2. Interfaces — Full Explanation**

**Interface = pure contract**

### Example:

```java
interface Vehicle {
    void start();   // abstract method
    void stop();
}
```

Any class that implements must define all methods:

```java
class Car implements Vehicle {
    public void start() {
        System.out.println("Car starting...");
    }

    public void stop() {
        System.out.println("Car stopping...");
    }
}
```

### Usage:

```java
public class Main {
    public static void main(String[] args) {
        Vehicle v = new Car();
        v.start();
        v.stop();
    }
}
```

---

# 🔥 **Modern Interfaces (Java 8+)**

Interfaces can now have:

### ✔ default methods

```java
interface A {
    default void show() {
        System.out.println("Default show");
    }
}
```

### ✔ static methods

```java
interface A {
    static void hello() {
        System.out.println("Hello!");
    }
}
```

### ✔ private methods (Java 9+)

```java
interface A {
    private void helper() { }
}
```

---

# 🔍 **Abstract Class vs Interface — Clear Table**

| Feature              | Abstract Class             | Interface                                |
| -------------------- | -------------------------- | ---------------------------------------- |
| Methods              | Can be abstract + normal   | Mostly abstract; can have default/static |
| Variables            | Any type                   | **Always public static final**           |
| Constructors         | ✔ Yes                      | ✖ No                                     |
| Multiple Inheritance | ✖ No                       | ✔ Yes                                    |
| Access Modifiers     | public, protected, private | Only public                              |
| When to use?         | When you need shared code  | When you need a contract                 |

---

# 💡 When to Use What?

### ✔ Use **abstract class** when:

* You want to provide **some common code**
* You want fields + constructors
* You want partial abstraction

### ✔ Use **interface** when:

* You want **full abstraction**
* You want **multiple inheritance**
* You are designing a contract (e.g., Runnable, Comparable)

---

# 🧠 **Memory Behavior (Important for Interviews)**

### Abstract class memory:

* Object created via subclass
* Contains:

  * instance fields of abstract class
  * instance fields of subclass
  * v-table for overridden methods

### Interface memory:

* interface methods stored in **method table**
* implemented inside actual object’s v-table

---

# 🎯 FULL Practical Example — Abstraction in Real Use

### Scenario: Payment System

### Step 1: Abstract Class

```java
abstract class Payment {
    abstract void pay(double amount);

    void info() {
        System.out.println("Online Payment System");
    }
}
```

---

### Step 2: Subclasses

```java
class UPI extends Payment {
    void pay(double amount) {
        System.out.println("Paid Rs " + amount + " via UPI");
    }
}

class CreditCard extends Payment {
    void pay(double amount) {
        System.out.println("Paid Rs " + amount + " using Credit Card");
    }
}
```

---

### Step 3: Usage

```java
public class Main {
    public static void main(String[] args) {
        Payment p1 = new UPI();
        p1.info();
        p1.pay(500);

        Payment p2 = new CreditCard();
        p2.pay(2000);
    }
}
```

---

# 🎉 Final Summary (Interview Gold)

✔ Abstraction hides details and shows essential operations
✔ Achieved via: **abstract classes & interfaces**
✔ Abstract class = partial abstraction
✔ Interface = full abstraction
✔ Interfaces support default + static + private methods
✔ Child must implement abstract methods
✔ Used for security, loose coupling, templates & contracts

---
----
----
Here is a **complete, clear, interview-ready explanation of Java Annotations**, from basics → custom annotations → reflection → real industry use cases.
This will give you full mastery.

---

# 🚀 **What Are Annotations in Java?**

**Annotations** are metadata (information) added to Java code.

They do **not** change how your program runs directly, but tools/compilers/frameworks **use them to make decisions**.

They are used on:

* Classes
* Methods
* Variables
* Parameters
* Packages

Example:

```java
@Override
void run() { }
```

---

# 🎯 **Why Annotations? (Very Important)**

✔ Reduce boilerplate code
✔ Give instructions to compiler
✔ Used heavily in frameworks (Spring, Hibernate)
✔ Provide metadata
✔ Used for validation, mapping, config, dependency injection

---

# ⭐ **Types of Annotations**

### **1. Built-in (Predefined) Annotations**

### **2. Meta-annotations (annotations that apply to annotations)**

### **3. Custom Annotations (you create your own)**

---

# 🟦 **1. Built-in Annotations**

## ✔ `@Override`

Tells compiler a method overrides a superclass method.

```java
class A {
    void show() {}
}

class B extends A {
    @Override
    void show() { }
}
```

---

## ✔ `@Deprecated`

Marks a method/class as outdated.

```java
@Deprecated
void oldFunction() {}
```

---

## ✔ `@SuppressWarnings`

Suppress compiler warnings.

```java
@SuppressWarnings("unchecked")
void test() { }
```

---

## ✔ `@FunctionalInterface`

Ensures the interface has exactly **one abstract method**.

```java
@FunctionalInterface
interface MyFunc {
    void execute();
}
```

---

# 🟩 **2. Meta-Annotions**

(These define how your custom annotation behaves)

| Annotation    | Meaning                       |
| ------------- | ----------------------------- |
| `@Target`     | Where annotation can be used  |
| `@Retention`  | How long annotation survives  |
| `@Inherited`  | Whether subclasses inherit it |
| `@Documented` | Put into Javadoc              |
| `@Repeatable` | Annotation can repeat         |

---

## ✔ `@Target` — where annotation can be applied

```java
@Target(ElementType.METHOD)
```

Other values:

* TYPE (class/interface)
* METHOD
* FIELD
* PARAMETER
* CONSTRUCTOR
* PACKAGE

---

## ✔ `@Retention` — till when annotation is available

```java
@Retention(RetentionPolicy.RUNTIME)
```

Options:

* **SOURCE** – removed at compilation
* **CLASS** – in .class file but not at runtime
* **RUNTIME** – available via reflection (MOST IMPORTANT)

---

# 🟥 **3. Creating Custom Annotations (Very Important)**

### Step 1: Create annotation

```java
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyAnnotation {
    String value();
}
```

Here:

* `@Retention(RUNTIME)` → we can access it via reflection
* `@Target(METHOD)` → can be used only on methods

---

### Step 2: Use the annotation

```java
class Test {
    @MyAnnotation("Hello Annotation")
    public void display() {
        System.out.println("Display called");
    }
}
```

---

### Step 3: Access annotation using Reflection

```java
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws Exception {

        Method m = Test.class.getMethod("display");

        MyAnnotation ann = m.getAnnotation(MyAnnotation.class);

        System.out.println(ann.value());  // prints: Hello Annotation
    }
}
```

✔ This is exactly how **Spring**, **Hibernate**, **JUnit**, **Spring Boot**, etc. work internally.

---

# 🎉 **Real-Life Examples of Annotations**

---

## ⭐ Spring Framework

```java
@RestController
@RequestMapping("/api")
class UserController {

    @Autowired
    UserService service;

    @GetMapping("/users")
    public List<User> getUsers() { ... }
}
```

* `@RestController` → class is a controller
* `@Autowired` → dependency injection
* `@GetMapping` → mapping HTTP GET request

All done through **annotations**.

---

## ⭐ JPA / Hibernate

```java
@Entity
class User {
  
    @Id
    private int id;

    @Column(name = "user_name")
    private String name;
}
```

Annotations map classes to database tables.

---

## ⭐ JUnit (Testing)

```java
@Test
void testSum() { }
```

---

# 📌 **Annotation with Multiple Fields**

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Info {
    String author();
    int version() default 1;
}
```

Usage:

```java
@Info(author = "Pratyush", version = 2)
class Demo { }
```

---

# 🔥 **Repeatable Annotation**

```java
@Repeatable(Roles.class)
@interface Role {
    String value();
}

@interface Roles {
    Role[] value();
}
```

---

# 🔥 Summary — Important for Interviews

✔ Annotation = metadata
✔ Used by compiler, JVM, and frameworks
✔ Three types: built-in, meta-annotations, custom
✔ `@Retention(RUNTIME)` required to use reflection
✔ `@Target` defines where annotation is valid
✔ Spring, Hibernate, JUnit all work heavily with annotations

---

# 🎯 Want next topic?
