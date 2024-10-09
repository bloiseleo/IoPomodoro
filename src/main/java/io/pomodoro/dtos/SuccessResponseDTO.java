package io.pomodoro.dtos;

import org.springframework.http.HttpStatus;

import java.time.Instant;


public class SuccessResponseDTO extends BaseResponseDTO {
    public SuccessResponseDTO(String message, HttpStatus status) {
        super(message, Instant.now().toEpochMilli(), status.value());
    }
}
