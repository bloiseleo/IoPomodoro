package io.pomodoro.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFound extends BaseAPIException{
    public UserNotFound(String message) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
