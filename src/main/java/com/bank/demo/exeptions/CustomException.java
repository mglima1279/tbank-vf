package com.bank.demo.exeptions;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException{
    private String message;
    private int httpStatusCode;
    private LocalDateTime timestamp;

    public CustomException(int httpStatusCode, String message) {
        super(message);
        
        this.message = message;
        this.httpStatusCode = httpStatusCode;
        this.timestamp = LocalDateTime.now();
    }
}
