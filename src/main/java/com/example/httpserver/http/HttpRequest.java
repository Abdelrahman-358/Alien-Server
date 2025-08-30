package com.example.httpserver.http;

import java.util.HashMap;
import java.util.List;

public class HttpRequest {
    HttpMethod method;
    String target;
    String httpVersion;
    HashMap<String, List<String>> headers;
    String body;

    HttpRequest() {}

    public HttpMethod getMethod() {
        return method;
    }

    void setMethod(String method) throws HttpParsingException {
        for (HttpMethod httpMethod : HttpMethod.values()) {
            if (httpMethod.name().equals(method)) {
                this.method = httpMethod;
                return;
            }
        }
        throw new HttpParsingException(HttpStatusCode.INTERNAL_SERVER_ERROR_501_NOT_IMPLEMENTED);
    }

    public String getTarget() {
        return target;
    }


    public String getHttpVersion() {
        return httpVersion;
    }

    public HashMap<String, List<String>> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    void setTarget(String target) {
        this.target = target;
    }

    public void setHttpVersion(String version) {
        this.httpVersion = version;
    }

    void setHeaders(HashMap<String, List<String>> headers) {
        this.headers = headers;
    }

    void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "HttpRequest [method=" + method + ", target=" + target + ", httpVersion=" + httpVersion + ", headers=" + headers + ", body=" + body + "]";
    }

}
