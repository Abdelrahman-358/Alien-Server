package com.example.httpserver.http;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * parsing an HTTP message is
 * to read the start-line into a structure,
 * read each header field line into a hash table by field name until the empty line,
 * and then use the parsed data to determine if a message body is expected.
 * If a message body has been indicated, then it is read as a stream
 * until an amount of octets equals to the message body length is read or the connection is closed.
 * Scanner less parser
 * */
public class HttpParser {

    private static final int CR = 13, LF = 10, SP = 32;
    private static final int MAX_HEADER_LINE = 16 * 1024;
    private static final int MAX_URI_LENGTH = 8192;
    private static final int MAX_BODY = 10 * 1024 * 1024;

    public static HttpRequest parseRequest(InputStream input) throws IOException, HttpParsingException {
        BufferedInputStream bufferedInput = new BufferedInputStream(input);
        InputStreamReader reader = new InputStreamReader(bufferedInput, StandardCharsets.US_ASCII);

        HttpRequest request = new HttpRequest();

        parseRequestLine(reader, request);
        parseHeaders(reader, request);
        parseBody(bufferedInput, request);

        return request;
    }

    private static void parseRequestLine(InputStreamReader reader, HttpRequest request) throws IOException, HttpParsingException {
        StringBuilder builder = new StringBuilder();
        int state = 0; // 0 = reading httpMethod, 1 = reading httpTarget, 2 = reading httpVersion

        int b;
        while ((b = reader.read()) != -1) {
            if (b == CR) {
                b = reader.read();
                if (b != LF) {
                    throw new HttpParsingException(HttpStatus.CLIENT_ERROR_400_BAD_REQUEST);
                }
                // End of request line
                if (state != 2) {
                    throw new HttpParsingException(HttpStatus.CLIENT_ERROR_400_BAD_REQUEST);
                }
                request.setHttpVersion(builder.toString());
                return;
            }
            if (b == SP) {
                if (state == 0) {
                    // Finished reading method
                    request.setMethod(builder.toString());
                    builder.setLength(0);
                    state = 1;
                } else if (state == 1) {
                    // Finished reading target
                    request.setTarget(builder.toString());
                    builder.setLength(0);
                    state = 2;
                } else {
                    throw new HttpParsingException(HttpStatus.CLIENT_ERROR_400_BAD_REQUEST);
                }
            } else {
                builder.append((char) b);
                // Validate method length
                if (state == 0 && builder.length() > HttpMethod.MAX_LENGTH) {
                    throw new HttpParsingException(HttpStatus.CLIENT_ERROR_400_BAD_REQUEST);
                } else if (builder.length() > MAX_URI_LENGTH) {
                    throw new HttpParsingException(HttpStatus.CLIENT_ERROR_414_URI_TOO_LONG);
                }
            }
        }
        throw new HttpParsingException(HttpStatus.CLIENT_ERROR_400_BAD_REQUEST);
    }

    private static void parseHeaders(InputStreamReader reader, HttpRequest request) throws HttpParsingException, IOException {
        HashMap<String, List<String>> headers = new HashMap<>();
        String headerLine;
        while (!(headerLine = getHeaderLine(reader)).isEmpty()) {
            int colonPos = headerLine.indexOf(':');
            if (colonPos <= 0) {
                throw new HttpParsingException(HttpStatus.CLIENT_ERROR_400_BAD_REQUEST);
            }
            String name = headerLine.substring(0, colonPos).trim().toLowerCase();
            String value = headerLine.substring(colonPos + 1).trim();

            if (name.isEmpty() || value.isEmpty()) {
                throw new HttpParsingException(HttpStatus.CLIENT_ERROR_400_BAD_REQUEST);
            }
            headers.computeIfAbsent(name, k -> new ArrayList<>()).add(value);
        }
        request.setHeaders(headers);
    }

    private static void parseBody(InputStream input, HttpRequest request) throws IOException, HttpParsingException {
        if (request.getHeaders().containsKey("content-length")) {
            try {
                int contentLength = Integer.parseInt(request.getHeaders().get("content-length").getFirst());
                if (contentLength > 0 && contentLength <= MAX_BODY) {
                    byte[] buffer = new byte[contentLength];
                    int bytesRead = 0;
                    while (bytesRead < contentLength) {
                        int read = input.read(buffer, bytesRead, contentLength - bytesRead);
                        if (read == -1) {
                            throw new IOException("Unexpected end of stream");
                        }
                        bytesRead += read;
                    }
                    request.setBody(new String(buffer, StandardCharsets.US_ASCII));
                } else if (contentLength == 0) {
                    request.setBody("");
                } else {
                    throw new HttpParsingException(HttpStatus.CLIENT_ERROR_413_PAYLOAD_TOO_LARGE);
                }
            } catch (HttpParsingException e) {
                throw new HttpParsingException(HttpStatus.CLIENT_ERROR_400_BAD_REQUEST);
            }
        } else {
            request.setBody("");
        }
    }

    private static String getHeaderLine(InputStreamReader input) throws IOException, HttpParsingException {
        StringBuilder sb = new StringBuilder();
        int b;
        while ((b = input.read()) != -1) {
            if (b == CR) {
                b = input.read();
                if (b == LF) {
                    break;
                } else {
                    throw new HttpParsingException(HttpStatus.CLIENT_ERROR_400_BAD_REQUEST);
                }
            } else if (b == LF) {
                // TODO: handle LF without CR
                break;
            }
            sb.append((char) b);

            if (sb.length() > MAX_HEADER_LINE) {
                throw new HttpParsingException(HttpStatus.CLIENT_ERROR_413_PAYLOAD_TOO_LARGE);
            }
        }
        return sb.toString();
    }
}