package com.knoldus.exception;

public class CassandraMappingException extends RuntimeException {
    
    public CassandraMappingException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
