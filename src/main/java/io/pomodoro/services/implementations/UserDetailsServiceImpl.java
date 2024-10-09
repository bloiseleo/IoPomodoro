package io.pomodoro.services.implementations;

import io.pomodoro.infra.UserDetailsImpl;
import io.pomodoro.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findUserByEmail(username);
        if (user.isEmpty()) throw new UsernameNotFoundException(username);
        return new UserDetailsImpl(user.get());
    }
}
