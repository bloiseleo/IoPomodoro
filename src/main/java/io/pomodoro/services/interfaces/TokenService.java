package io.pomodoro.services.interfaces;

import com.auth0.jwt.interfaces.DecodedJWT;
import io.pomodoro.entities.User;

public interface TokenService {
    String generateToken(String email, String id, String role);
    String generateToken(User user);
    DecodedJWT decodeJwt(String token);
}
