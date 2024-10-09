package io.pomodoro.services;

import io.pomodoro.entities.Profile;
import io.pomodoro.entities.Roles;
import io.pomodoro.entities.User;
import io.pomodoro.services.implementations.TokenServiceImpl;
import io.pomodoro.services.interfaces.TokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

public class TokenServiceTest {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final TokenService tokenService = new TokenServiceImpl("secret", 1);
    @Test
    public void ShouldCreateTokenAndDecodeItSuccessfully() {
        var user = new User();
        user.setId(1);
        user.setEmail("teste@gmail.com");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setProfile(null);

        var token = tokenService.generateToken(user);
        var decodedToken = tokenService.decodeJwt(token);

        Assertions.assertFalse(token.isEmpty());
        Assertions.assertEquals(user.getId(), decodedToken.getClaim("id").asInt());
        Assertions.assertEquals(user.getEmail(), decodedToken.getClaim("email").asString());
        Assertions.assertEquals(Roles.NO_PROFILE.name(), decodedToken.getClaim("role").asString());
        Assertions.assertTrue(decodedToken.getExpiresAtAsInstant().isAfter(Instant.now()));
        Assertions.assertEquals("io.pomodoro", decodedToken.getIssuer());
    }
    @Test
    public void ShouldCreateTokenWithRoleCommon() {
        var profile = new Profile();
        var user = new User();

        profile.setId(1);
        profile.setProfilePhoto("teste");
        profile.setNickname("leobloise_");
        profile.setUser(user);

        user.setId(1);
        user.setEmail("teste@gmail.com");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setProfile(profile);

        var token = tokenService.generateToken(user);
        var decodedToken = tokenService.decodeJwt(token);

        Assertions.assertFalse(token.isEmpty());
        Assertions.assertEquals(user.getId(), decodedToken.getClaim("id").asInt());
        Assertions.assertEquals(user.getEmail(), decodedToken.getClaim("email").asString());
        Assertions.assertEquals(Roles.COMMON.name(), decodedToken.getClaim("role").asString());
        Assertions.assertTrue(decodedToken.getExpiresAtAsInstant().isAfter(Instant.now()));
        Assertions.assertEquals("io.pomodoro", decodedToken.getIssuer());
    }
}
