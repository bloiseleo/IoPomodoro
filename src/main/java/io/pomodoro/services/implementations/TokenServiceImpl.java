package io.pomodoro.services.implementations;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.pomodoro.entities.User;
import io.pomodoro.services.interfaces.TokenService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {
    @Value("${security.jwt.secret}")
    private String secret;
    @Value("${security.jwt.expiration-time}")
    private long expiration;
    private Algorithm algorithm() {
        return Algorithm.HMAC256(secret);
    }
    @Override
    public String generateToken(User user) {
        return JWT.create()
                .withIssuer("io.pomodoro")
                .withClaim("id", user.getId())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole().name())
                .withExpiresAt(Instant.now().plus(expiration, ChronoUnit.DAYS))
                .sign(algorithm());
    }
    @Override
    public String generateToken(String email, String id, String role) {
        return JWT.create()
                .withIssuer("io.pomodoro")
                .withClaim("id", id)
                .withClaim("email", email)
                .withClaim("role", role)
                .withExpiresAt(Instant.now().plus(expiration, ChronoUnit.DAYS))
                .sign(algorithm());
    }
    @Override
    public DecodedJWT decodeJwt(String token) {
        try {
            return JWT.require(algorithm())
                    .withIssuer("io.pomodoro")
                    .build()
                    .verify(token);
        } catch (Exception exception) {
            throw new AuthenticationCredentialsNotFoundException(exception.getMessage());
        }
    }
}
