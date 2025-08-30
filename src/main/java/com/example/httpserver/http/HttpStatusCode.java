package com.example.httpserver.http;

public enum HttpStatusCode {
    // Client Errors
    CLIENT_ERROR_400_BAD_REQUEST(400, "Bad Request"),
    CLIENT_ERROR_404_NOT_FOUND(404, "Not Found"),
    CLIENT_ERROR_405_METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    CLIENT_ERROR_414_URI_TOO_LONG(414, "URI Too Long"),
    CLIENT_ERROR_413_PAYLOAD_TOO_LARGE(413, "Payload Too Large"),

    // Server Errors
    INTERNAL_SERVER_ERROR_500(414, "Internal Server Error"),
    INTERNAL_SERVER_ERROR_501_NOT_IMPLEMENTED(501, "Not Implemented");


    private final int STATUS_CODE;
    private final String MESSAGE;
    HttpStatusCode(int statusCode, String message) {
        this.STATUS_CODE = statusCode;
        this.MESSAGE = message;
    }

    public int getStatusCode() {
        return STATUS_CODE;
    }

    public String getMessage() {
        return MESSAGE;
    }
}
