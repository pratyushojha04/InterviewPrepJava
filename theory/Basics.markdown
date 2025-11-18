
---

# ✅ **1. JDK, JRE, javac/java Commands & Project Structure**

### **JDK (Java Development Kit)**

* This is the full package that developers install.
* **Contains:**

  * JRE (runtime)
  * Compiler (**javac**)
  * Tools like debugger, jar tool, etc.
* **Use case:** Writing and compiling Java programs.

### **JRE (Java Runtime Environment)**

* This is only for running Java applications.
* **Contains:**

  * JVM (Java Virtual Machine)
  * Standard libraries required to run code
* **It cannot compile code.**

### **JVM (Java Virtual Machine)**

* Executes your `.class` bytecode.
* Makes Java platform-independent.

---

## ⚙️ **javac vs java Commands**

### **javac (Compiler)**

Converts `.java` → `.class` (bytecode)

```bash
javac Main.java
```

Output:

```
Main.class
```

### **java (Runner)**

Runs the compiled bytecode:

```bash
java Main
```

⚠️ Note: **You do NOT write `.class` extension when running.**

---

## 📁 **Java Folder / Project Layout**

A simple project:

```
MyProject/
 ├── src/
 │     └── Main.java
 ├── out/ (or bin/)
 │     └── Main.class
 └── README.md
```

When using IntelliJ or Eclipse:

```
MyProject/
 ├── src/
 │    └── com/company/Main.java
 ├── target/ or out/
 ├── pom.xml (Maven) or build.gradle (Gradle)
```

---

# ✅ **2. Primitive Types, Variables, Literals, Operators**

## **Primitive Types in Java (8 total)**

| Type    | Size    | Example      |
| ------- | ------- | ------------ |
| byte    | 1 byte  | 10           |
| short   | 2 bytes | 200          |
| int     | 4 bytes | 10_000       |
| long    | 8 bytes | 123456789L   |
| float   | 4 bytes | 10.5f        |
| double  | 8 bytes | 10.234       |
| char    | 2 bytes | 'A'          |
| boolean | 1 bit   | true / false |

---

## **Variables**

A variable stores data in memory.

```java
int age = 21;
double salary = 30000.50;
char grade = 'A';
boolean isPassed = true;
```

---

## **Literals**

Literal means the *actual value* you assign.

Examples:

```java
int x = 10;         // integer literal
double d = 12.45;   // floating literal
char c = 'Q';       // character literal
boolean b = true;   // boolean literal
String s = "Java";  // string literal (not primitive)
```

---

## **Operators in Java**

### **Arithmetic**

```
+, -, *, /, %
```

### **Assignment**

```
=, +=, -=, *=, /=, %=
```

### **Comparison**

```
==, !=, >, <, >=, <=
```

### **Logical**

```
&&  AND  
||  OR  
!   NOT
```

### **Increment/Decrement**

```
++, --
```

Example:

```java
int a = 5;
a++;        // 6
boolean t = (a > 3) && (a < 10); // true
```

---

# ✅ **3. Control Flow: if, switch, loops**

## **if / else**

```java
int age = 20;

if (age >= 18) {
    System.out.println("Adult");
} else {
    System.out.println("Minor");
}
```

---

## **switch**

Useful when multiple conditions are checked on the same variable.

```java
int day = 3;

switch(day) {
    case 1: System.out.println("Mon"); break;
    case 2: System.out.println("Tue"); break;
    case 3: System.out.println("Wed"); break;
    default: System.out.println("Invalid");
}
```

---

# **Loops**

---

## **for Loop**

```java
for (int i = 1; i <= 5; i++) {
    System.out.println(i);
}
```

---

## **while Loop**

```java
int i = 1;
while(i <= 5) {
    System.out.println(i);
    i++;
}
```

---

## **do-while Loop**

Runs at least once.

```java
int i = 1;
do {
    System.out.println(i);
    i++;
} while(i <= 5);
```


---

Here is a clean and deep explanation of **methods**, **arrays**, **strings**, and related concepts — exactly what you should know before starting OOP and bigger Java projects.

---

# ✅ **1. Methods, Parameters, Return Types, Method Overloading**

## **What is a Method?**

A method is a block of code that performs a specific task.

### Syntax:

```java
returnType methodName(parameters) {
    // code
}
```

---

## 🔹 **Parameters**

Inputs passed into a method.

```java
void greet(String name) {
    System.out.println("Hello, " + name);
}
```

`name` → parameter.

---

## 🔹 **Return Type**

What the method sends back.

```java
int add(int a, int b) {
    return a + b;
}
```

If a method returns nothing, use `void`.

```java
void printMessage() {
    System.out.println("Hi!");
}
```

---

## 🔹 **Method Overloading**

Multiple methods **with the same name** but **different parameters**.

### Overloading happens when:

✔ number of parameters different
✔ OR types of parameters different
✔ OR order of parameters different

### Example:

```java
class Calculator {

    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }
}
```

---
---
---
# ✅ **2. Arrays (1D & 2D) and Basic Algorithms**

## **1D Array**

Stores multiple values of same type.

```java
int[] arr = {10, 20, 30, 40};
```

OR

```java
int[] arr = new int[5];  // default values are 0
arr[0] = 10;
```

---

## **2D Array (Matrix)**

```java
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6}
};
```

OR

```java
int[][] m = new int[3][3];
m[0][1] = 5;
```

---

# ⭐ **Basic Array Algorithms**

### **1. Traversing an array**

```java
for (int i = 0; i < arr.length; i++) {
    System.out.println(arr[i]);
}
```

Enhanced for-loop:

```java
for (int x : arr) {
    System.out.println(x);
}
```

---

### **2. Finding Maximum Element**

```java
int max = arr[0];
for (int n : arr) {
    if (n > max) max = n;
}
```

---

### **3. Searching an element (Linear Search)**

```java
int target = 30;
boolean found = false;

for (int x : arr) {
    if (x == target) {
        found = true;
        break;
    }
}
```

---

### **4. Sorting (Manual — Bubble Sort)**

```java
for (int i = 0; i < arr.length - 1; i++) {
    for (int j = 0; j < arr.length - 1 - i; j++) {
        if (arr[j] > arr[j + 1]) {
            int temp = arr[j];
            arr[j] = arr[j + 1];
            arr[j + 1] = temp;
        }
    }
}
```

---

### **5. Sum of elements**

```java
int sum = 0;
for (int x : arr) sum += x;
```

---

### **6. Matrix Traversal (2D Arrays)**

```java
for (int i = 0; i < matrix.length; i++) {
    for (int j = 0; j < matrix[i].length; j++) {
        System.out.print(matrix[i][j] + " ");
    }
}
```

---

# ✅ **3. Strings, StringBuilder, StringBuffer**

## **Strings**

* Java Strings are **immutable** (cannot be changed).
* Whenever you modify a string, a **new object is created**.

### Example:

```java
String s = "Hello";
s = s + " World";  // new object created
```

---

## 🔹 **Useful String Methods**

```java
s.length();
s.charAt(2);
s.substring(1, 4);
s.toLowerCase();
s.toUpperCase();
s.equals("Hello");
s.equalsIgnoreCase("hello");
s.contains("ell");
```

---

# 🔥 **StringBuilder and StringBuffer**

## **Why do they exist?**

Because **String is immutable** → slow for repeated modifications.

### StringBuilder → NOT thread-safe (faster)

### StringBuffer → thread-safe (slower)

---

## **StringBuilder Example**

```java
StringBuilder sb = new StringBuilder("Hello");

sb.append(" World");
sb.insert(0, "Java ");
sb.delete(5, 7);
sb.reverse();
```

---

## **StringBuilder is MUTABLE**

Modifications happen in the same object — faster and memory efficient.

---

### **When to use what?**

| Use case                          | Choose            |
| --------------------------------- | ----------------- |
| Many modifications, single thread | **StringBuilder** |
| Many modifications, multi-thread  | **StringBuffer**  |
| Few modifications                 | **String**        |

---
---
---


---

# ✅ **1. Basic I/O in Java**

Java I/O means taking input and giving output.

---

# 🔹 **System.out – Output**

Used to print data to the console.

```java
System.out.println("Hello");
System.out.print("Hi");
System.out.printf("Number: %d", 10);
```

---

# 🔹 **System.in – Input**

System.in is a byte stream, so we wrap it with **Scanner** to read input easily.

```java
import java.util.Scanner;

Scanner sc = new Scanner(System.in);

int age = sc.nextInt();
String name = sc.nextLine();
double salary = sc.nextDouble();
```

---

# 📁 **File Reading & Writing (java.io)**

## **Writing to a file (FileWriter)**

```java
import java.io.FileWriter;

FileWriter fw = new FileWriter("data.txt");
fw.write("Hello Java");
fw.close();
```

---

## **Reading a file (FileReader + BufferedReader)**

```java
import java.io.*;

BufferedReader br = new BufferedReader(new FileReader("data.txt"));

String line;
while ((line = br.readLine()) != null) {
    System.out.println(line);
}
br.close();
```

---

# ⚡ **java.nio (New I/O)**

java.nio is faster and simpler for basic tasks.

## **Writing (Files.write)**

```java
import java.nio.file.*;

Files.write(Path.of("info.txt"), "Hello NIO".getBytes());
```

---

## **Reading (Files.readAllLines)**

```java
List<String> lines = Files.readAllLines(Path.of("info.txt"));
for (String s : lines) System.out.println(s);
```

---

# 🔥 Difference between java.io and java.nio

| Feature     | java.io           | java.nio               |
| ----------- | ----------------- | ---------------------- |
| Mode        | Blocking          | Non-blocking           |
| Performance | Slower            | Faster                 |
| API Design  | Older             | Newer, simpler         |
| Buffers     | No direct buffers | Has Buffers & Channels |

---

# ✅ **2. Exception Handling**

Exception = runtime error that stops the program.

Java uses **try–catch–finally** to handle exceptions.

---

# 🔹 **try–catch**

```java
try {
    int x = 10 / 0;            // risky code
} catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero");
}
```

---

# 🔹 **Multiple catch blocks**

```java
try {
    int[] arr = new int[3];
    arr[5] = 10;
} 
catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Index wrong");
}
catch (Exception e) {
    System.out.println("Other error");
}
```

**Always keep the parent `Exception` last.**

---

# 🔹 **finally**

Runs **always** (whether or not exception occurs).

```java
try {
    System.out.println(10 / 2);
} catch (Exception e) {
    System.out.println("Error");
} finally {
    System.out.println("Done");    // always runs
}
```

---

# 🔹 **throws keyword**

Used when a method wants to **pass responsibility** to caller.

```java
void readFile() throws IOException {
    FileReader fr = new FileReader("data.txt");
}
```

Caller must handle it:

```java
try {
    readFile();
} catch (IOException e) {
    e.printStackTrace();
}
```

---

# 🔥 **Custom Exception**

Create your own exception by extending `Exception`.

```java
class AgeException extends Exception {
    AgeException(String msg) {
        super(msg);
    }
}
```

Throw it:

```java
void checkAge(int age) throws AgeException {
    if (age < 18)
        throw new AgeException("Age must be 18+");
}
```

Use it:

```java
try {
    checkAge(16);
} catch (AgeException e) {
    System.out.println(e.getMessage());
}
```

---

---
---
---


---

# ✅ **1. Exception Class Hierarchy in Java (Most Important)**

```
java.lang.Object
   └── java.lang.Throwable
          ├── java.lang.Error   (unchecked, serious issues)
          │      ├── OutOfMemoryError
          │      ├── StackOverflowError
          │      └── VirtualMachineError
          │
          └── java.lang.Exception   (mostly checked)
                 ├── IOException
                 │      ├── FileNotFoundException
                 │      ├── EOFException
                 │      └── SocketException
                 │
                 ├── SQLException
                 ├── ClassNotFoundException
                 ├── CloneNotSupportedException
                 ├── InterruptedException
                 │
                 └── RuntimeException   (unchecked)
                        ├── ArithmeticException
                        ├── NullPointerException
                        ├── ArrayIndexOutOfBoundsException
                        ├── NumberFormatException
                        ├── IllegalArgumentException
                        └── IllegalStateException
```

### ✔ Key points:

* **Checked exceptions** → must be handled (IOException, SQLException)
* **Unchecked exceptions (RuntimeException)** → do NOT need try/catch
* **Errors** → cannot be handled

---

# ✅ **2. File System Class Hierarchy (java.io & java.nio)**

There are two systems:

* **Old I/O (java.io)**
* **New I/O (java.nio)**

---

## 📁 **A. java.io File Class Hierarchy**

`java.io.File` is NOT a subclass of InputStream/OutputStream.
It only represents a file path or directory.

```
java.lang.Object
   └── java.io.File
```

But file reading/writing streams have large families:

### **Input Streams (binary data)**

```
java.lang.Object
   └── java.io.InputStream
          ├── FileInputStream
          ├── ByteArrayInputStream
          ├── ObjectInputStream
          ├── FilterInputStream
          │      ├── BufferedInputStream
          │      └── DataInputStream
          └── PipedInputStream
```

### **Output Streams (binary data)**

```
java.lang.Object
   └── java.io.OutputStream
          ├── FileOutputStream
          ├── ByteArrayOutputStream
          ├── ObjectOutputStream
          ├── FilterOutputStream
          │      ├── BufferedOutputStream
          │      └── DataOutputStream
          └── PipedOutputStream
```

### **Readers/Writers (character data)**

#### Reader (input)

```
java.lang.Object
   └── java.io.Reader
          ├── FileReader
          ├── BufferedReader
          ├── InputStreamReader
          └── StringReader
```

#### Writer (output)

```
java.lang.Object
   └── java.io.Writer
          ├── FileWriter
          ├── BufferedWriter
          ├── OutputStreamWriter
          └── StringWriter
```

---

## 📂 **B. java.nio (Modern File System)**

### **Core classes**

```
java.lang.Object
   └── java.nio.file.Path   (interface)
         └── implemented by sun.nio.fs.* classes
```

```
java.lang.Object
   └── java.nio.file.Files   (utility class)
```

```
java.lang.Object
   └── java.nio.file.FileSystem
            └── DefaultFileSystemProvider
```

### Simplified Hierarchy

```
java.nio.file.Path     (represents file/directory path)
java.nio.file.Files    (utility class with static methods)
java.nio.file.FileSystem
java.nio.file.FileStore
java.nio.file.DirectoryStream
```

### NIO Stream Replacements:

* Path → better than File
* Files.readAllLines() → replaces BufferedReader
* Files.write() → replaces FileWriter / OutputStream

---


