package com.diego.projetos.springboot_studies.handler;

import com.diego.projetos.springboot_studies.exception.BadRequestException;
import com.diego.projetos.springboot_studies.exception.BadRequestExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    // what is happening here is: if any of the Controllers in my project get
    // a BadRequestException, it will use the @ExceptionHandler and return
    // the value that you defined inside the method with the annotation

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails>
            badRequestExceptionHandler(BadRequestException exception) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception. Check the Documentation")
                        .details(exception.getMessage())
                        .developerMessage(exception.getClass().getName())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }
}
