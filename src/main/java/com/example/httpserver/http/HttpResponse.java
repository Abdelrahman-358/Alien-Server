package com.example.httpserver.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HttpResponse {
    private final HttpVersion httpVersion;
    private final HttpStatus status;
    private final HashMap<String, List<String>> headers;
    private final String body;

    private HttpResponse(Builder builder) {
        this.httpVersion = builder.httpVersion;
        this.status = builder.status;
        this.headers = builder.headers;
        this.body = builder.body;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public HashMap<String, List<String>> getHeaders() {
        return new HashMap<>(headers);
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "HttpResponse [httpVersion=" + httpVersion + ", status=" + status + ", headers=" + headers + ", body=" + body + "]";
    }

    // Builder class
    public static class Builder {
        private final HttpStatus status;
        
        private HttpVersion httpVersion = HttpVersion.HTTP_1_1;
        private final HashMap<String, List<String>> headers = new HashMap<>();
        private String body = "";

        public Builder(HttpStatus status) {
            this.status = status;
        }

        public Builder withHttpVersion(HttpVersion httpVersion) {
            this.httpVersion = httpVersion;
            return this;
        }

        public Builder withHeader(String name, String value) {
            this.headers.computeIfAbsent(name, k -> new ArrayList<>()).add(value);
            return this;
        }

        public Builder withHeaders(HashMap<String, String> headers) {
            headers.forEach((key, value) -> 
                this.headers.computeIfAbsent(key, k -> new ArrayList<>()).add(value)
            );
            return this;
        }

        public Builder withBody(String body) {
            this.body = body != null ? body : "";
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(this);
        }
    }
}

