package io.pomodoro.services.interfaces;

public interface AuthService {
    String authenticate(String email, String password);
}
