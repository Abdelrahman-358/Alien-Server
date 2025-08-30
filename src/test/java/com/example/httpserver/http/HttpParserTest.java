package com.example.httpserver.http;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HttpParserTest {
    private final HttpParser parser = new HttpParser();

    @Test
    void parseSimpleGetRequest() throws IOException {
        String request = "GET /index.html HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: TestClient\r\n" +
                "\r\n";

        HttpRequest httpRequest = parser.parseRequest(
                new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8)));

        assertEquals("GET", httpRequest.getMethod());
        assertEquals("/index.html", httpRequest.getTarget());
        assertEquals("HTTP/1.1", httpRequest.getHttpVersion());
        assertEquals("localhost:8080", httpRequest.getHeaders().get("host").get(0));
        assertEquals("TestClient", httpRequest.getHeaders().get("user-agent").get(0));
        assertTrue(httpRequest.getBody().isEmpty());
    }

    @Test
    void parsePostRequestWithBody() throws IOException {
        String requestBody = "name=test&type=user";
        String request = "POST /users HTTP/1.1\r\n" +
                "Host: api.example.com\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Content-Length: " + requestBody.length() + "\r\n" +
                "\r\n" +
                requestBody;

        HttpRequest httpRequest = parser.parseRequest(
                new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8)));

        assertEquals("POST", httpRequest.getMethod());
        assertEquals("/users", httpRequest.getTarget());
        assertEquals("HTTP/1.1", httpRequest.getHttpVersion());
        assertEquals("api.example.com", httpRequest.getHeaders().get("host").get(0));
        assertEquals("application/x-www-form-urlencoded",
                httpRequest.getHeaders().get("content-type").get(0));
        assertEquals(requestBody, httpRequest.getBody());
    }

    @Test
    void parseRequestWithMultipleHeaders() throws IOException {
        String request = "GET /test HTTP/1.1\r\n" +
                "Accept: application/json\r\n" +
                "Accept-Language: en-US,en;q=0.9\r\n" +
                "Cache-Control: no-cache\r\n" +
                "\r\n";

        HttpRequest httpRequest = parser.parseRequest(
                new ByteArrayInputStream(request.getBytes(StandardCharsets.US_ASCII)));

        Map<String, List<String>> headers = httpRequest.getHeaders();
        assertEquals("application/json", headers.get("accept").get(0));
        assertEquals("en-US,en;q=0.9", headers.get("accept-language").get(0));
        assertEquals("no-cache", headers.get("cache-control").get(0));
    }

    @Test
    void parseRequestWithDuplicateHeaders() throws IOException {
        String request = "GET /test HTTP/1.1\r\n" +
                "Set-Cookie: session=abc123\r\n" +
                "Set-Cookie: theme=dark\r\n" +
                "\r\n";

        HttpRequest httpRequest = parser.parseRequest(
                new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8)));

        List<String> cookies = httpRequest.getHeaders().get("set-cookie");
        assertEquals(2, cookies.size());
        assertTrue(cookies.contains("session=abc123"));
        assertTrue(cookies.contains("theme=dark"));
    }

    @Test
    void parseRequestWithEmptyBody() throws IOException {
        String request = "GET /empty HTTP/1.1\r\n" +
                "Content-Length: 0\r\n" +
                "\r\n";

        HttpRequest httpRequest = parser.parseRequest(
                new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8)));

        assertEquals("", httpRequest.getBody());
    }
    }
