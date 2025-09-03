package com.example.httpserver.http;

public enum HttpStatus {
    // Client
    CLIENT_ERROR_400_BAD_REQUEST(400, "Bad Request"),
    CLIENT_ERROR_404_NOT_FOUND(404, "Not Found"),
    CLIENT_ERROR_405_METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    CLIENT_ERROR_414_URI_TOO_LONG(414, "URI Too Long"),
    CLIENT_ERROR_413_PAYLOAD_TOO_LARGE(413, "Payload Too Large"),

    // Server Errors
    INTERNAL_SERVER_ERROR_500(414, "Internal Server Error"),
    INTERNAL_SERVER_ERROR_501_NOT_IMPLEMENTED(501, "Not Implemented"),


    SUCCESS_200_OK(200, "OK");

    private final int statusCode;
    private final String reasonPhrase;
    HttpStatus(int statusCode, String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
