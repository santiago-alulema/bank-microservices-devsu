package com.devsu.hackerearth.backend.client.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ErrorDto {

    public ErrorDto() {
    }

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
}
