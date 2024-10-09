package io.pomodoro.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BaseAPIException extends RuntimeException {
    private HttpStatus status;
    public BaseAPIException(String message) {
        super(message);
        status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    public BaseAPIException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
