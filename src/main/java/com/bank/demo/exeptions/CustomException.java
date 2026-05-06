package com.bank.demo.exeptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatusCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {
    private String message;
    private HttpStatusCode httpStatusCode;
    private LocalDateTime timestamp;

    public CustomException(HttpStatusCode httpStatusCode, String message) {
        super(message);

        this.message = message;
        this.httpStatusCode = httpStatusCode;
        this.timestamp = LocalDateTime.now();
    }
}
