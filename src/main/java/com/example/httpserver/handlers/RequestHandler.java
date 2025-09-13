package com.example.httpserver.handlers;

import com.example.httpserver.http.HttpRequest;
import com.example.httpserver.http.HttpResponse;
import com.example.httpserver.http.HttpStatus;

import java.util.function.Function;

@FunctionalInterface
public interface RequestHandler extends Function<HttpRequest, HttpResponse> {

    @Override
    default HttpResponse apply(HttpRequest request) {
        try {
            return handle(request);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    HttpResponse handle(HttpRequest request) throws Exception;

    default HttpResponse handleException(Throwable t) {
        String errorMessage = "Internal Server Error: " + t.getMessage();
        return new HttpResponse.Builder(HttpStatus.INTERNAL_SERVER_ERROR_500)
                .withHeader("Content-Type", "text/plain")
                .withHeader("Content-Length", String.valueOf(errorMessage.length()))
                .withBody(errorMessage)
                .build();
    }
}