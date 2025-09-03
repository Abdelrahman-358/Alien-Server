package com.example.httpserver.http;

public class HttpParsingException extends  Exception {
   private final HttpStatus httpStatusCode;

   public HttpParsingException(HttpStatus httpStatusCode) {
       super(httpStatusCode.getReasonPhrase());
       this.httpStatusCode = httpStatusCode;
   }

   public HttpStatus getHttpStatus() {
       return httpStatusCode;
   }

}
