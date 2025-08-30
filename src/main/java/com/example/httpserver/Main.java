package com.example.httpserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        System.out.println("Logs from your program will appear here!");

        try {
            ServerSocket serverSocket = new ServerSocket(4221);

            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);

            Socket clientSocket = serverSocket.accept(); // Wait for connection from client.
            System.out.println("accepted new connection");
            String response ;


            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            Reader in = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader input = new BufferedReader(in);

            String urlPath = input.readLine().split(" ")[1];
            if (urlPath.equals("/")) {
                response = "HTTP/1.1 200 OK\r\n\r\n";
            } else {
                response = "HTTP/1.1 404 Not Found\r\n\r\n";
            }
            out.println(response);

            out.close();
            in.close();
            clientSocket.close();
            serverSocket.close();

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }

    }
}
