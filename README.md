# 🚀 SparkLite Web Framework

A lightweight, minimalist Java web framework for building REST APIs and serving static content with an elegant lambda-based routing system.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Project Objectives](#project-objectives)
- [Architecture](#architecture)
- [Core Components](#core-components)
- [Quick Start](#quick-start)
- [Usage Examples](#usage-examples)
- [API Reference](#api-reference)
- [Testing](#testing)
- [Technical Stack](#technical-stack)
- [Project Structure](#project-structure)
- [How to Build](#how-to-build)
- [How to Run](#how-to-run)
- [Contributing](#contributing)
- [License](#license)

---

## Overview

SparkLite is a lightweight Java web framework that transforms a basic HTTP server into a powerful REST API platform. Built with simplicity and performance in mind, it leverages Java's functional programming capabilities (lambda expressions) to provide an intuitive, Spark-like framework experience for developers.

The framework empowers developers to:

- Define REST endpoints with minimal boilerplate
- Extract and manipulate query parameters effortlessly
- Serve static assets (HTML, CSS, JavaScript, images) efficiently
- Build scalable web applications with clean, readable code

---

## Features

✨ **Lambda-Based Routing**

- Define REST endpoints using concise lambda expressions
- Type-safe route handlers with Request/Response objects
- Fluent API design for better code readability

🔄 **Query Parameter Handling**

- Automatic parsing of URL query strings
- Easy parameter extraction with `getValues(String key)`
- Support for multiple parameters in a single request

📁 **Static File Serving**

- Configurable static file directories
- Support for multiple MIME types (HTML, CSS, JS, JSON, images)
- Binary stream support for image rendering
- Automatic content-type detection

🧪 **Comprehensive Testing**

- Full unit test coverage for Request and Router classes
- JUnit 5 framework for modern testing patterns
- Extensive test cases covering edge cases and common scenarios

🏗️ **Clean Architecture**

- Modular component design
- Clear separation of concerns
- Easy to extend and maintain

---

## Project Objectives

This project was designed to accomplish:

1. **Transform a basic HTTP server** into a production-ready web framework
2. **Implement REST services** with elegant lambda expression support
3. **Parse HTTP requests** and extract query parameters efficiently
4. **Serve static resources** with proper content-type headers
5. **Demonstrate fundamental concepts** of:
   - HTTP protocol architecture and request/response lifecycle
   - Client-server communication patterns
   - REST API design principles
   - Static resource management
   - Distributed system architecture

---

## Architecture

SparkLite follows a clean, modular architecture with clear separation of concerns:

```
edu.escuelaing.arep.webframework
│
├── framework
│   └── SparkLite.java                 # Public API interface
│
├── server
│   ├── WebServer.java                 # HTTP server implementation
│   └── Router.java                    # Request routing and dispatching
│
├── http
│   ├── Request.java                   # HTTP request abstraction
│   └── Response.java                  # HTTP response abstraction
│
└── Main.java                          # Application entry point
```

## Core Components

### 1. **WebServer** 🖥️

The foundation of SparkLite's HTTP handling capabilities.

**Responsibilities:**

- Manages low-level TCP socket communication
- Parses raw HTTP requests into structured data
- Delegates REST requests to the Router component
- Serves static files with appropriate MIME types
- Handles binary file transmission for images
- Implements HTTP status codes (200 OK, 404 Not Found)

**Key Methods:**

- `start()` - Initializes and starts the HTTPServer
- `handleRequest(InputStream, OutputStream)` - Processes incoming HTTP requests

### 2. **Router** 🛣️

The intelligent request dispatcher of the framework.

**Responsibilities:**

- Maintains a registry of route handlers
- Maps URL paths to lambda function handlers
- Executes the appropriate handler when a request matches a registered route
- Returns null for unregistered paths (allowing 404 responses)

**Key Methods:**

- `get(String path, BiFunction<Request, Response, String> handler)` - Register a GET route
- `handle(Request request)` - Dispatch request to appropriate handler

### 3. **SparkLite** ✨

The public-facing API for developers.

**Provides:**

- `staticfiles(String folder)` - Configure static resource directory
- `get(String path, handler)` - Define REST endpoints
- Acts as a facade to WebServer functionality

### 4. **Request** 📨

Encapsulates HTTP request data with query parameter support.

**Features:**

- Extracts HTTP method (GET, POST, etc.)
- Parses request path/URI
- Automatically parses query string into key-value pairs
- Thread-safe parameter retrieval

**Key Methods:**

- `getMethod()` - Returns HTTP method
- `getPath()` - Returns request path
- `getValues(String key)` - Retrieves query parameter value

### 5. **Response** 📤

Placeholder for future response customization (status codes, custom headers).

Currently serves as a context object passed to handlers for potential future enhancements.

---

## Quick Start

### Prerequisites

- **Java 17 or higher**
- **Maven 3.6+**
- **Git**

### Installation

1. **Clone the repository:**

   ```bash
   git clone <repository-url>
   cd Microframeworks-WEB-Lab
   ```

2. **Build the project:**

   ```bash
   mvn clean package
   ```

3. **Run the application:**

   ```bash
   java -cp target/classes edu.escuelaing.arep.webframework.Main
   ```

4. **Open your browser:**
   ```
   http://localhost:35000/index.html
   ```

---

## Usage Examples

### Defining REST Endpoints

#### Simple Endpoint

```java
SparkLite.get("/App/hello", (req, res) ->
    "Hello World");
```

#### Endpoint with Query Parameters

```java
SparkLite.get("/App/hello", (req, res) ->
    "Hello " + req.getValues("name"));
```

**Usage:** `http://localhost:35000/App/hello?name=Pedro`
**Response:** `Hello Pedro`

<img width="473" height="157" alt="image" src="https://github.com/user-attachments/assets/d96565c5-e67d-44aa-8b7f-fdd1943c963f" />


#### Mathematical Endpoint

```java
SparkLite.get("/App/pi", (req, res) ->
    String.valueOf(Math.PI));
```

**Usage:** `http://localhost:35000/App/pi`
**Response:** `3.141592653589793`

<img width="336" height="111" alt="image" src="https://github.com/user-attachments/assets/8f2e2b5b-6573-4900-a2cb-c383001cc303" />


#### Endpoint with Multiple Parameters

```java
SparkLite.get("/App/search", (req, res) -> {
    String query = req.getValues("q");
    String category = req.getValues("category");
    return "Searching for: " + query + " in " + category;
});
```

**Usage:** `http://localhost:35000/App/search?q=java&category=tutorials`

<img width="565" height="104" alt="image" src="https://github.com/user-attachments/assets/dfbb1266-93f2-4a11-9f79-30a217d727a8" />


### Configuring Static Files

```java
public static void main(String[] args) throws Exception {
    // Configure static files location
    SparkLite.staticfiles("/webroot");

    // Define REST endpoints
    SparkLite.get("/App/hello", (req, res) ->
            "Hello " + req.getValues("name"));

    SparkLite.get("/App/pi", (req, res) ->
            String.valueOf(Math.PI));

    // Start the server
    WebServer.start();
}
```

### Accessing Resources

**Static Files:**

- `http://localhost:35000/index.html`
- `http://localhost:35000/style.css`
- `http://localhost:35000/script.js`
- `http://localhost:35000/image.png`
- `http://localhost:35000/test.GIF`

<img width="1907" height="938" alt="image" src="https://github.com/user-attachments/assets/df78111f-cf49-4dec-9770-ff275d979413" />


---

## API Reference

### SparkLite Class

#### `staticfiles(String folder)`

Configures the directory for static files.

**Parameters:**

- `folder` (String) - Relative path to static files directory (e.g., "/webroot")

**Example:**

```java
SparkLite.staticfiles("/webroot");
```

#### `get(String path, BiFunction<Request, Response, String> handler)`

Registers a GET route handler.

**Parameters:**

- `path` (String) - The route path (e.g., "/App/hello")
- `handler` (BiFunction) - Lambda function handling the request

**Handler Signature:**

```java
(Request req, Response res) -> String
```

**Example:**

```java
SparkLite.get("/users/:id", (req, res) ->
    "User: " + req.getValues("id"));
```

### Request Class

#### `getMethod()`

Returns the HTTP method of the request.

**Returns:** String - HTTP method (e.g., "GET", "POST")

#### `getPath()`

Returns the request path/URI.

**Returns:** String - The path (e.g., "/App/hello")

#### `getValues(String key)`

Retrieves a query parameter value by key.

**Parameters:**

- `key` (String) - Parameter name

**Returns:** String - Parameter value, or null if not found

**Example:**

```java
String name = request.getValues("name");
```

---

## Testing

### Test Coverage

The project includes comprehensive unit tests for critical components:

#### Request Tests (`RequestTest.java`)

- Request creation with method, path, and query parameters
- Query parameter extraction
- Null and empty query string handling
- Single and multiple parameter scenarios
- Special character handling in parameters
- POST request support

**Run Request Tests:**

```bash
mvn test -Dtest=RequestTest
```

#### Router Tests (`RouterTest.java`)

- Route registration and execution
- Multiple route handling
- Unregistered route handling (returns null)
- Request/Response object passing
- Handler logic execution
- Query parameter passing to handlers
- Path distinction between similar routes

**Run Router Tests:**

```bash
mvn test -Dtest=RouterTest
```

#### Run All Tests

```bash
mvn test
```

### Test Framework

- **Framework:** JUnit 5 (Jupiter)
- **Assertions:** `org.junit.jupiter.api.Assertions`
- **Annotations:** `@Test`, `@DisplayName`, `@BeforeEach`

---

## Supported MIME Types

| File Type  | MIME Type                |
| ---------- | ------------------------ |
| HTML       | `text/html`              |
| CSS        | `text/css`               |
| JavaScript | `application/javascript` |
| JSON       | `application/json`       |
| PNG        | `image/png`              |
| JPEG       | `image/jpeg`             |
| GIF        | `image/gif`              |
| SVG        | `image/svg+xml`          |
| Plain Text | `text/plain`             |

---

## Technical Stack

| Technology | Version | Purpose          |
| ---------- | ------- | ---------------- |
| Java       | 17+     | Core language    |
| Maven      | 3.6+    | Build automation |
| JUnit      | 5.11.0  | Unit testing     |
| Git        | Latest  | Version control  |

---

## Project Structure

```
Microframeworks-WEB-Lab/
│
├── pom.xml                                          # Maven configuration
├── README.md                                        # This file
│
├── src/
│   ├── main/
│   │   ├── java/edu/escuelaing/arep/webframework/
│   │   │   ├── Main.java                           # Entry point
│   │   │   ├── framework/
│   │   │   │   └── SparkLite.java                  # Public API
│   │   │   ├── server/
│   │   │   │   ├── WebServer.java                  # HTTP server
│   │   │   │   └── Router.java                     # Request router
│   │   │   └── http/
│   │   │       ├── Request.java                    # Request wrapper
│   │   │       └── Response.java                   # Response wrapper
│   │   │
│   │   └── resources/
│   │       └── webroot/                            # Static files
│   │           ├── index.html                      # Main page
│   │           ├── style.css                       # Styles
│   │           └── script.js                       # Client-side logic
│   │
│   └── test/
│       └── java/edu/escuelaing/arep/webframework/
│           ├── http/
│           │   └── RequestTest.java                # Request tests
│           └── server/
│               └── RouterTest.java                 # Router tests
│
└── target/                                          # Build output
    ├── classes/
    └── test-classes/
```

---

## How to Build

### Prerequisites Check

Ensure Java 17+ is installed:

```bash
java -version
```

### Build Command

```bash
# Clean previous builds and create new package
mvn clean package

# Alternative: Build without running tests
mvn clean package -DskipTests
```

### Verify Build

```bash
# Check target directory for webframework-1.0-SNAPSHOT.jar
ls target/
```

---

## How to Run

### Method 1: Direct Execution

```bash
java -cp target/classes edu.escuelaing.arep.webframework.Main
```

### Expected Output

```
Server is running. On main thread and still running...
```

### Access the Application

Open your browser and navigate to:

- **Main Page:** `http://localhost:35000/index.html`
- **REST Endpoint:** `http://localhost:35000/App/hello?name=Your-Name`
- **Math Endpoint:** `http://localhost:35000/App/pi`

### Stop the Server

Press `Ctrl + C` in the terminal

---

## Example Application

### Complete Main Class Example

```java
import edu.escuelaing.arep.webframework.framework.SparkLite;
import edu.escuelaing.arep.webframework.server.WebServer;

public class Main {
    public static void main(String[] args) throws Exception {
        // Configure static files
        SparkLite.staticfiles("/webroot");

        // Define REST endpoints
        SparkLite.get("/App/hello", (req, res) ->
                "Hello " + req.getValues("name"));

        SparkLite.get("/App/pi", (req, res) ->
                String.valueOf(Math.PI));

        SparkLite.get("/App/search", (req, res) -> {
            String query = req.getValues("q");
            return "Results for: " + query;
        });

        // Start the webserver
        WebServer.start();
    }
}
```

---

## Performance & Best Practices

### Route Definition

- Keep route paths consistent and RESTful
- Use meaningful path names (e.g., `/api/users` instead of `/u`)
- Avoid complex query string parsing in handlers

### Static Files

- Place all static files in the configured static directory
- Use appropriate file names and extensions
- Consider file sizes for web delivery

### Handler Implementation

- Keep handler logic concise and efficient
- Use lambdas for simple operations
- Consider extracting complex logic to separate methods

---

## Troubleshooting

### Issue: Port Already in Use

**Problem:** `Address already in use` error

**Solution:**

- Change the PORT constant in WebServer.java
- Or kill the process using port 35000

### Issue: Static Files Not Found

**Problem:** 404 error for HTML/CSS/JS files

**Solution:**

- Verify `SparkLite.staticfiles("/webroot")` is called
- Ensure files are in `target/classes/webroot/`
- Check file names match exactly (case-sensitive)

### Issue: Query Parameters Not Extracted

**Problem:** `getValues()` returns null

**Solution:**

- Verify query string is properly formatted: `?key1=value1&key2=value2`
- Check parameter names are spelled correctly
- Use `getValues()` only for query parameters, not path variables

---

## Browser Developer Tools Verification

The following aspects have been verified using browser Developer Tools:

✅ **HTTP Status Codes**

- 200 OK for successful requests and static files
- 404 Not Found for non-existent routes/files

✅ **Content-Type Headers**

- Correct MIME types for all file types
- Proper character encoding

✅ **Content-Length**

- Accurate file size reporting
- Proper handling of binary content

✅ **Response Times**

- Fast static file serving
- Minimal latency for REST endpoints

---

## License

This project is developed for educational purposes as part of the AREP course at Escuela Colombiana de Ingeniería Julio Garavito.

---

## Author

**Nicole Calderón**

**Course:** Transformación Digital y Soluciones Empresariales (TDSE)  
**Institution:** Escuela Colombiana de Ingeniería Julio Garavito  
**Program:** Systems Engineering

---

**Last Updated:** March 2, 2026  
**Version:** 1.0.0  
**Status:** ✅ Ready
