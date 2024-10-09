package io.pomodoro.controllers;

import io.pomodoro.dtos.CreateProfileDTO;
import io.pomodoro.dtos.TokenMessageDTO;
import io.pomodoro.entities.Roles;
import io.pomodoro.infra.UserDetailsImpl;
import io.pomodoro.services.interfaces.ProfileService;
import io.pomodoro.services.interfaces.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private TokenService tokenService;
    @PostMapping("create")
    public ResponseEntity<TokenMessageDTO> createProfile(@ModelAttribute @Valid CreateProfileDTO dto) {
        var authToken = SecurityContextHolder.getContext().getAuthentication();
        var userDetails = (UserDetailsImpl) authToken.getPrincipal();
        profileService.createProfile(userDetails, dto);
        var newToken = tokenService.generateToken(userDetails.getUsername(), userDetails.getId(), Roles.COMMON.name());
        return new ResponseEntity<>(
                new TokenMessageDTO(
                        "Profile created successfully",
                        newToken,
                        HttpStatus.CREATED
                ),
                HttpStatus.CREATED
        );
    }
}
