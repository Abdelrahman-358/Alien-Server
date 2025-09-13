package com.example.httpserver.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void start() throws Exception {

        int threadCount = Integer.parseInt(
                System.getProperty("server.threads",
                        String.valueOf(Runtime.getRuntime().availableProcessors() * 2)
                )
        );
        ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
        
        try (ServerSocket serverSocket = new ServerSocket(4221)) {
            logger.info("Alien Server started on port 4221");
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.debug("New connection accepted: {}", clientSocket.getRemoteSocketAddress());
            threadPool.execute(new ConnectionHandler(clientSocket));
            }
        } finally {
            threadPool.shutdown();
        }
    }
}
