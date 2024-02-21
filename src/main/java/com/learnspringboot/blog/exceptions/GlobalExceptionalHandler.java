package com.learnspringboot.blog.exceptions;

import com.learnspringboot.blog.payloads.ExceptionResponse;
import org.springframework.boot.autoconfigure.amqp.RabbitRetryTemplateCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionalHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception){

        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.name());

        return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> methodArgNotValidationExceptionHandler(MethodArgumentNotValidException exception){

        Map<String,String> resp= new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error -> {
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            resp.put(fieldName, message);
        }));

        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }
}
