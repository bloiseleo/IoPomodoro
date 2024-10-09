package io.pomodoro.controllers;

import io.pomodoro.dtos.CreateUserDTO;
import io.pomodoro.dtos.SuccessResponseDTO;
import io.pomodoro.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping
    public SuccessResponseDTO createUser(@RequestBody @Valid CreateUserDTO dto) {
        userService.createUser(dto);
        return new SuccessResponseDTO(
                "User created successfully",
                HttpStatus.CREATED
        );
    }
}
