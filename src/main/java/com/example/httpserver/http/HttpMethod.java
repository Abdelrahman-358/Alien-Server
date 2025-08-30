package com.example.httpserver.http;

public enum HttpMethod {
    GET, HEAD, SET;
    public  static  int MAX_LENGTH ;
    static {
        MAX_LENGTH = -1;
        for(HttpMethod httpMethod : HttpMethod.values()){
            MAX_LENGTH = Math.max(MAX_LENGTH, httpMethod.name().length());
        }
    }
    int getMaxLength() {
        return MAX_LENGTH;
    }

}
