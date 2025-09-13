package com.example.httpserver.handlers;

import com.example.httpserver.http.HttpRequest;
import com.example.httpserver.http.HttpResponse;
import com.example.httpserver.http.HttpStatus;

public class HelloWorldHandler implements RequestHandler {

    @Override
    public HttpResponse handle(HttpRequest request) {
        String responseBody = "Hello, World!";
        return new HttpResponse.Builder(HttpStatus.SUCCESS_200_OK)
                .withHeader("Content-Type", "text/plain")
                .withHeader("Content-Length", String.valueOf(responseBody.length()))
                .withBody(responseBody)
                .build();
    }
}