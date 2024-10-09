package io.pomodoro.controllers;

import io.pomodoro.dtos.LoginDTO;
import io.pomodoro.dtos.TokenMessageDTO;
import io.pomodoro.services.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("login")
    public ResponseEntity<TokenMessageDTO> login(@RequestBody LoginDTO loginDTO) {
        var token = this.authService.authenticate(loginDTO.email(), loginDTO.password());
        return new ResponseEntity<>(new TokenMessageDTO(
                "Logged in Successfully",
                token
        ), HttpStatus.OK);
    }
}
