package io.pomodoro.infra;

import io.pomodoro.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserDetailsImpl implements UserDetails {
    private List<GrantedAuthority> authorities;
    private String username;
    private String password;
    private String id;
    private String role;
    public UserDetailsImpl(User user) {
        password = user.getPassword();
        username = user.getEmail();
        id = String.valueOf(user.getId());
        role = user.getRole().name();
        authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }
}
