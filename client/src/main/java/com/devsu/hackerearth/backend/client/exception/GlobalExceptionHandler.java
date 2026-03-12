package com.devsu.hackerearth.backend.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.devsu.hackerearth.backend.client.helper.BuildResponse;
import com.devsu.hackerearth.backend.client.model.dto.ErrorDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFound(ResourceNotFoundException ex) {
        return BuildResponse.build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDto> handleBadRequest(BadRequestException ex) {
        return BuildResponse.build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDto> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return BuildResponse.build(HttpStatus.BAD_REQUEST, "Invalid parameter type");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handlerGeneric(Exception ex) {
        return BuildResponse.build(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred" + ex.getMessage());
    }

}
