package com.example.httpserver.routing;

import com.example.httpserver.http.*;


import java.io.IOException;
import java.io.OutputStream;

public class HelloHandler {

    public static String body = "Hello World";

    public static void handleGetRequest(HttpRequest request, OutputStream outputStream) throws IOException, HttpParsingException {

        HttpResponse response = new HttpResponse();
        String contentType = "text/html";
        String contentLength = String.valueOf(body.length());
        response.setStatusCode(HttpStatus.SUCCESS_200_OK);
        response.addHeader("Content-Type", contentType);
        response.addHeader("Content-Length", contentLength);
        response.setBody(body);
        HttpWriter.writeResponse(response, outputStream);
    }


    public static  void handlePostRequest(HttpRequest request, OutputStream outputStream) throws IOException, HttpParsingException {
        HttpResponse response = new HttpResponse();
        body = request.getBody();
        response.setStatusCode(HttpStatus.SUCCESS_200_OK);
        response.addHeader("Content-Type", "text/html");
        response.addHeader("Content-Length", String.valueOf(body.length()));
        response.setBody(body);
        HttpWriter.writeResponse(response, outputStream);
    }
}
