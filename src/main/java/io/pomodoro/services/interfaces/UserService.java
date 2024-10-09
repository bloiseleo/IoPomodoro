package io.pomodoro.services.interfaces;

import io.pomodoro.dtos.CreateUserDTO;
import io.pomodoro.entities.User;

import java.util.Optional;

public interface UserService {
    User createUser(CreateUserDTO data);
    Optional<User> findUserById(String id);
}
