package com.bank.demo.exeptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExeptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomExceptionEntity> customExceptionHandler(CustomException e) {
        CustomExceptionEntity entity = CustomExceptionEntity.fromException(e);

        return ResponseEntity.status(HttpStatusCode.valueOf(e.getHttpStatusCode())).body(entity);
    } 
}
