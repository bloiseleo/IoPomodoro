package io.pomodoro.services;

import io.pomodoro.dtos.CreateUserDTO;
import io.pomodoro.entities.User;
import io.pomodoro.exceptions.EmailAlreadyTaken;
import io.pomodoro.repositories.UserRepository;
import io.pomodoro.services.implementations.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Spy
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @InjectMocks
    private UserServiceImpl userService;
    private String hashPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
    @Test
    public void Should_Create_User() {
        var dto = new CreateUserDTO(
                "teste@gmail.com",
                "12345678"
        );
        Mockito
                .when(
                        userRepository.existsByEmail(
                                Mockito.anyString())
                )
                .thenReturn(false);
        Mockito.when(
                userRepository.save(Mockito.any())
        ).thenReturn(new User(1, dto.email(), hashPassword(dto.password()), null));
        var userCreated = userService.createUser(dto);
        Assertions.assertTrue(userCreated.getId() > 0);
        Assertions.assertEquals(dto.email(), userCreated.getEmail());
        Assertions.assertNotEquals(dto.password(), userCreated.getPassword());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(dto.password());
    }
    @Test
    public void Should_Not_Create_User_Due_Email_Taken() {
        var dto = new CreateUserDTO(
                "teste@gmail.com",
                "12345678"
        );
        Mockito.when(
                userRepository.existsByEmail(Mockito.any())
        ).thenReturn(true);
        Assertions.assertThrows(EmailAlreadyTaken.class, () -> {
            userService.createUser(dto);
        });
        Mockito.verify(userRepository, Mockito.times(1)).existsByEmail(dto.email());
    }
}
