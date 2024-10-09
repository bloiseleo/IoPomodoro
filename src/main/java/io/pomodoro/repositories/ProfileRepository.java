package io.pomodoro.repositories;

import io.pomodoro.entities.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {
    boolean existsProfileByNickname(String nickname);
}
