package io.pomodoro.dtos;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Getter
public class TokenMessageDTO extends BaseResponseDTO {
    private String token;
    public TokenMessageDTO(String message, String token) {
        super(message, Instant.now().toEpochMilli(), HttpStatus.OK.value());
        this.token = token;
    }
    public TokenMessageDTO(String message, String token, HttpStatus status) {
        super(message, Instant.now().toEpochMilli(), status.value());
        this.token = token;
    }
}
