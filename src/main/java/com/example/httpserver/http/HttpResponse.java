package com.example.httpserver.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpResponse {

    // Start-line
    private HttpVersion httpVersion;
    private HttpStatus status;

    // Header
    private HashMap<String, List<String>> headers;

    // Body
    private String body;

    public HttpResponse() {
        this.httpVersion = HttpVersion.HTTP_1_1;
        this.status = HttpStatus.SUCCESS_200_OK;
        this.headers = new HashMap<>();
        this.body = "";
    }

    public HttpResponse(HttpVersion httpVersion, HttpStatus statusCode,String body) {
        this.httpVersion = httpVersion;
        this.status= statusCode;
        this.headers = new HashMap<>();
    }
    public void  setHttpVersion(HttpVersion httpVersion) {
        this.httpVersion = httpVersion;
    }
    public void setStatusCode(HttpStatus status) {
        this.status = status;
    }


    public void addHeader(String name, String value) {
       this.headers.computeIfAbsent(name, k -> new ArrayList<>()).add(value);
    }

    public void setBody(String body) {
        this.body = body;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public HashMap<String, List<String>> getHeaders() {
        return headers;
    }


    public String getBody() {
        return body;
    }
    @Override
    public String toString() {
        return "HttpResponse [httpVersion=" + httpVersion + ", status=" + status + ", headers=" + headers + ", body=" + body + "]";
    }
}
