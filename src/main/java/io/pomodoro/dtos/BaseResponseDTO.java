package io.pomodoro.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseResponseDTO {
    private String message;
    private long time;
    private int status;
    public BaseResponseDTO(
            String message,
            long time,
            int status
    ) {
        this.message = message;
        this.time = time;
        this.status = status;
    }
}

