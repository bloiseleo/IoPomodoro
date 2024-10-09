package io.pomodoro.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyHaveAProfile extends BaseAPIException {
    public UserAlreadyHaveAProfile(String message) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
