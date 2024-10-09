package io.pomodoro.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record CreateUserDTO(
        @Email
        @NotEmpty
        String email,
        @NotEmpty
        String password
) { }
