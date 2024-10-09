package io.pomodoro.services.interfaces;

import io.pomodoro.dtos.CreateProfileDTO;
import io.pomodoro.entities.Profile;
import io.pomodoro.infra.UserDetailsImpl;

public interface ProfileService {
    Profile createProfile(UserDetailsImpl userDetails, CreateProfileDTO profile);
}
