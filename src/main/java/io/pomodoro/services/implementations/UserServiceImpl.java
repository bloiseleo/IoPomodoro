package io.pomodoro.services.implementations;

import io.pomodoro.dtos.CreateUserDTO;
import io.pomodoro.entities.User;
import io.pomodoro.exceptions.EmailAlreadyTaken;
import io.pomodoro.repositories.UserRepository;
import io.pomodoro.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Override
    public User createUser(CreateUserDTO data) {
        if(userRepository.existsByEmail(data.email())) {
            throw new EmailAlreadyTaken(data.email());
        }
        var user = new User();
        user.setEmail(data.email());
        user.setPassword(
                passwordEncoder.encode(data.password())
        );
        return userRepository.save(user);
    }
    @Override
    public Optional<User> findUserById(String id) {
        return userRepository.findById(Integer.parseInt(id));
    }
}
