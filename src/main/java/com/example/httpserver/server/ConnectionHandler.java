package com.example.httpserver.server;

import com.example.httpserver.http.*;
import com.example.httpserver.routing.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
            Router router = new Router();
            HttpResponse response = router.route(request);
            HttpWriter.writeResponse(response, outputStream);

        } catch (Exception e) {
            logger.error("Error handling client request: {}", e.getMessage(), e);
            try {
                String errorMessage = "Error handling request: " + e.getMessage();
                HttpResponse errorResponse = new HttpResponse.Builder(HttpStatus.INTERNAL_SERVER_ERROR_500)
                        .withHeader("Content-Type", "text/plain")
                        .withHeader("Content-Length", String.valueOf(errorMessage.length()))
                        .withBody(errorMessage)
                        .build();
                HttpWriter.writeResponse(errorResponse, clientSocket.getOutputStream());
            } catch (IOException ioException) {
                logger.error("Failed to send error response: {}", ioException.getMessage());
            }
        }
    }
}
