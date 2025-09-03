package com.example.httpserver.http;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class HttpWriter {
    private static final String CRLF = "\r\n";
    private static final String SP = ": ";

    public static void writeResponse(HttpResponse response, OutputStream outputStream) throws IOException {
        try {
            writeStatusLine(response, outputStream);
            writeHeaders(response, outputStream);
            writeBody(response, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            throw new IOException("Failed to write HTTP response", e);
        }
    }

    private static void writeStatusLine(HttpResponse response, OutputStream outputStream) throws IOException {
        String statusLine = response.getHttpVersion().getVersion() + " " +
                response.getStatus().getStatusCode() + " " +
                response.getStatus().getReasonPhrase() + CRLF;
        outputStream.write(statusLine.getBytes(StandardCharsets.US_ASCII));
    }

    private static void writeHeaders(HttpResponse response, OutputStream outputStream) throws IOException {
        for (Map.Entry<String, List<String>> entry : response.getHeaders().entrySet()) {
            String headerName = entry.getKey();
            for (String value : entry.getValue()) {
                String headerLine = headerName + SP + value + CRLF;
                outputStream.write(headerLine.getBytes(StandardCharsets.US_ASCII));
            }
        }
        // End of headers
        outputStream.write(CRLF.getBytes(StandardCharsets.US_ASCII));
    }

    private static void writeBody(HttpResponse response, OutputStream outputStream) throws IOException {
        if (response.getBody() != null && !response.getBody().isEmpty()) {
            outputStream.write(response.getBody().getBytes(StandardCharsets.UTF_8));
        }
    }
}