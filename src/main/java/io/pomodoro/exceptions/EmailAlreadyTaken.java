package io.pomodoro.exceptions;

import org.springframework.http.HttpStatus;

public class EmailAlreadyTaken extends BaseAPIException {
    public EmailAlreadyTaken(String email) {
        super("Email " + email +  " already taken", HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
