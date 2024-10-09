package io.pomodoro.exceptions;

import org.springframework.http.HttpStatus;

public class NicknameAlreadyTaken extends BaseAPIException {
    public NicknameAlreadyTaken(String nickname) {
        super("Nickname " + nickname + " was already taken", HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
