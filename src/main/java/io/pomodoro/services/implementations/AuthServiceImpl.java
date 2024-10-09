package io.pomodoro.services.implementations;

import io.pomodoro.infra.UserDetailsImpl;
import io.pomodoro.services.interfaces.AuthService;
import io.pomodoro.services.interfaces.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Override
    public String authenticate(String email, String password) {
        try {
            var authenticationObj = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            var userDetails = (UserDetailsImpl) authenticationObj.getPrincipal();
            return tokenService.generateToken(userDetails.getUsername(), userDetails.getId(), userDetails.getRole());
        } catch (Exception e) {
            throw e;
        }
    }
}
