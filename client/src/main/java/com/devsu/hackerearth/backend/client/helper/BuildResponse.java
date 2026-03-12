package com.devsu.hackerearth.backend.client.helper;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devsu.hackerearth.backend.client.model.dto.ErrorDto;

public class BuildResponse {
    public static ResponseEntity<ErrorDto> build(HttpStatus status, String message) {
        ErrorDto error = new ErrorDto();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(status.value());
        error.setError(status.getReasonPhrase());
        error.setMessage(message);

        return ResponseEntity.status(status).body(error);
    }
}