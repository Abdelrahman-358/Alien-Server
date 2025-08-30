package com.example.httpserver.http;

public class HttpParsingException extends  Exception {
   private final HttpStatusCode httpStatusCode;

   public HttpParsingException(HttpStatusCode httpStatusCode) {
       super(httpStatusCode.getMessage());
       this.httpStatusCode = httpStatusCode;
   }

   public HttpStatusCode getHttpStatusCode() {
       return httpStatusCode;
   }

}
