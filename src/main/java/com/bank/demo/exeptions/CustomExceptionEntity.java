package com.bank.demo.exeptions;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CustomExceptionEntity {
    private String message;
    private LocalDateTime timestamp;
    
    public static CustomExceptionEntity fromException(CustomException e) {
        CustomExceptionEntity entity = new CustomExceptionEntity();

        entity.setMessage(e.getMessage());
        entity.setTimestamp(e.getTimestamp());

        return entity;
    }
}
