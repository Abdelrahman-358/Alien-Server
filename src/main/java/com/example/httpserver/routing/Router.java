package com.example.httpserver.routing;

import com.example.httpserver.handlers.HelloWorldHandler;
import com.example.httpserver.handlers.RequestHandler;
import com.example.httpserver.http.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Router {
    private final Map<String, Map<HttpMethod, RequestHandler>> routes = new HashMap<>();

    public void addRoute(String target, HttpMethod method, RequestHandler handler) {
        routes.computeIfAbsent(target, k -> new HashMap<>()).put(method, handler);
    }
    public Router() {
        addRoute("/hello", HttpMethod.GET, new HelloWorldHandler());
    }
    public HttpResponse route(HttpRequest request) {
        String target = request.getTarget();
        HttpMethod method = request.getMethod();

        return routes.getOrDefault(target, Collections.emptyMap())
                .getOrDefault(method, this::handleMethodNotAllowed)
                .apply(request);
    }

    private HttpResponse handleMethodNotAllowed(HttpRequest request) {
        return new HttpResponse.Builder(HttpStatus.CLIENT_ERROR_405_METHOD_NOT_ALLOWED)
                .withHeader("Allow", "GET, POST") // List allowed methods
                .withBody("Method not allowed for this endpoint")
                .build();
    }

}
