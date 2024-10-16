package io.pomodoro.repositories;

import io.pomodoro.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);
    boolean existsByEmail(String email);
}
