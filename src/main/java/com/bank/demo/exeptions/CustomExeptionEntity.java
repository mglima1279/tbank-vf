package com.bank.demo.exeptions;

import java.time.LocalDateTime;

public class CustomExeptionEntity extends RuntimeException {

    private String message;
    private int httpStatusCode;
    private LocalDateTime timestamp;

    public CustomExeptionEntity(String message, int httpStatusCode) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
