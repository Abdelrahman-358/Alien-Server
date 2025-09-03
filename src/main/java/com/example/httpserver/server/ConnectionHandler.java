package com.example.httpserver.server;

import com.example.httpserver.http.HttpParser;
import com.example.httpserver.http.HttpRequest;
import com.example.httpserver.routing.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public record ConnectionHandler(Socket clientSocket) implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionHandler.class);

    @Override
    public void run() {
        logger.info("Handling connection from: {}", clientSocket.getRemoteSocketAddress());

        try (clientSocket;
             InputStream inputStream = clientSocket.getInputStream();
             OutputStream outputStream = clientSocket.getOutputStream()) {

            HttpRequest request = HttpParser.parseRequest(inputStream);
            Router.root(request, outputStream);

        } catch (Exception e) {
            logger.error("Error handling client request: {}", e.getMessage(), e);
            throw new RuntimeException("Error handling client request: " + e.getMessage());
        }
    }
}
