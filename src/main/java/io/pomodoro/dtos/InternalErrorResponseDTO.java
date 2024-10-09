package io.pomodoro.dtos;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public class InternalErrorResponseDTO extends BaseResponseDTO {
    public InternalErrorResponseDTO(String message) {
        super(message, Instant.now().toEpochMilli(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
