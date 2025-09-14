# Alien Server

A lightweight, custom HTTP server implementation written in Java that provides basic HTTP/1.1 functionality with a simple routing system.

## Features

- **HTTP/1.1 Protocol Support**: Handles standard HTTP requests and responses
- **Multi-threaded**: Uses a thread pool to handle concurrent connections
- **Custom HTTP Parser**: Implements HTTP message parsing without external dependencies
- **Routing System**: Simple route-based request handling
- **Error Handling**: Comprehensive error responses for various HTTP status codes

## Architecture

The server is built with a modular architecture:

- **HTTP Layer**: Core HTTP protocol implementation (parsing, responses, status codes)
- **Routing**: Simple route matching and handler delegation
- **Handlers**: Request processing logic
- **Server**: Connection management and threading

## Getting Started

### Prerequisites

- Java 8 or higher

### Running the Server

1. Compile the project:
```bash
javac -cp . com/example/httpserver/*.java com/example/httpserver/**/*.java
```

2. Run the server:
```bash
java com.example.httpserver.HttpServerApp
```

3. The server will start on port **4221**


## API Endpoints

### Built-in Routes

| Method | Path | Description |
|--------|------|-------------|
| GET | `/hello` | Returns "Hello, World!" message |

### Example Request

```http
GET /hello HTTP/1.1
Host: localhost:4221
```

### Example Response

```http
HTTP/1.1 200 OK
Content-Type: text/plain
Content-Length: 13

Hello, World!
```


## Adding Custom Routes

To add new routes, modify the `Router` constructor:

```java
public Router() {
    addRoute("/hello", HttpMethod.GET, new HelloWorldHandler());
    addRoute("/custom", HttpMethod.GET, new CustomHandler());
}
```

Create custom handlers by implementing the `RequestHandler` interface:

```java
public class CustomHandler implements RequestHandler {
    @Override
    public HttpResponse handle(HttpRequest request) {
        // Your custom logic here
        return new HttpResponse.Builder(HttpStatus.SUCCESS_200_OK)
                .withHeader("Content-Type", "application/json")
                .withBody("{\"message\": \"Custom response\"}")
                .build();
    }
}
```

## Project Structure

```
com.example.httpserver/
├── HttpServerApp.java          # Main application entry point
├── handlers/
│   ├── RequestHandler.java     # Handler interface
│   └── HelloWorldHandler.java  # Default hello world handler
├── http/
│   ├── HttpMethod.java         # HTTP method enum
│   ├── HttpParser.java         # HTTP message parser
│   ├── HttpParsingException.java
│   ├── HttpRequest.java        # HTTP request model
│   ├── HttpResponse.java       # HTTP response model
│   ├── HttpStatus.java         # HTTP status codes
│   ├── HttpVersion.java        # HTTP version enum
│   └── HttpWriter.java         # HTTP response writer
├── routing/
│   └── Router.java             # Request routing logic
└── server/
    ├── ConnectionHandler.java  # Individual connection handler
    └── Server.java            # Main server class
```

## Error Handling

The server provides comprehensive error handling:

- **Parsing Errors**: Invalid HTTP format returns 400 Bad Request
- **Unsupported Methods**: Returns 501 Not Implemented
- **Route Not Found**: Returns 404 Not Found
- **Method Not Allowed**: Returns 405 Method Not Allowed
- **Internal Errors**: Returns 500 Internal Server Error

## Logging

The application uses SLF4J for logging. Configure your preferred logging implementation (Logback, Log4j, etc.) to see server logs.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is open source and available under the [MIT License](LICENSE).