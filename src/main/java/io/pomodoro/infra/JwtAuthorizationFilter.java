package io.pomodoro.infra;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.pomodoro.dtos.SuccessResponseDTO;
import io.pomodoro.entities.Roles;
import io.pomodoro.services.interfaces.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private TokenService tokenService;
    private UsernamePasswordAuthenticationToken createToken(DecodedJWT decoded) {
        var role = decoded.getClaim("role").asString();
        var authority = new SimpleGrantedAuthority(role);
        var userDetailsImpl = new UserDetailsImpl();
        userDetailsImpl.setId(decoded.getClaim("id").asString());
        userDetailsImpl.setUsername(decoded.getClaim("email").asString());
        userDetailsImpl.setRole(decoded.getClaim("role").asString());
        return new UsernamePasswordAuthenticationToken(
                userDetailsImpl,
                "",
                List.of(authority)
        );
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            var token = authHeader.split("Bearer ")[1];
            var decoded = tokenService.decodeJwt(token);
            SecurityContextHolder.getContext().setAuthentication(createToken(decoded));
        } catch (Exception e) {

        }
        filterChain.doFilter(request, response);
    }
    private SuccessResponseDTO generateDTOMap() {
        return new SuccessResponseDTO(
                "Unauthorized",
                HttpStatus.UNAUTHORIZED
        );
    }
}
