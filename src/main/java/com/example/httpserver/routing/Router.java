package com.example.httpserver.routing;

import com.example.httpserver.http.HttpRequest;

import java.io.OutputStream;

public class Router {

    public static void root(HttpRequest request, OutputStream outputStream) throws Exception{
        switch (request.getMethod()) {
            case GET:
                HelloHandler.handleGetRequest(request,outputStream);
                break;
            case POST:
                HelloHandler.handlePostRequest(request,outputStream);
                break;
            default:
                throw new Exception("Method not supported");
        }
    }
}
