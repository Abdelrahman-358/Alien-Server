package com.example.httpserver.http;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HttpParserTest {
    @Test
    void testParseRequestLine() throws IOException {
        String requestLine = "GET /index.html HTTP/1.1";

    }
}
