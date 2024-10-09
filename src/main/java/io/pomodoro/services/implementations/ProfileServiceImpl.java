package io.pomodoro.services.implementations;

import io.pomodoro.dtos.CreateProfileDTO;
import io.pomodoro.entities.Profile;
import io.pomodoro.entities.Roles;
import io.pomodoro.entities.User;
import io.pomodoro.exceptions.NicknameAlreadyTaken;
import io.pomodoro.exceptions.UserAlreadyHaveAProfile;
import io.pomodoro.exceptions.UserNotFound;
import io.pomodoro.infra.UserDetailsImpl;
import io.pomodoro.repositories.ProfileRepository;
import io.pomodoro.repositories.UserRepository;
import io.pomodoro.services.interfaces.FileService;
import io.pomodoro.services.interfaces.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);
    @Autowired
    private FileService fileService;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserRepository userRepository;
    private void validateRole(UserDetailsImpl userDetails) {
        if (Roles.valueOf(userDetails.getRole()).equals(Roles.COMMON)) {
            throw new UserAlreadyHaveAProfile("User already has a profile");
        }
    }
    @Override
    public Profile createProfile(UserDetailsImpl userDetails, CreateProfileDTO dto) {
        try {
            this.validateRole(userDetails);
            if(this.profileRepository.existsProfileByNickname(dto.getNickname())) {
                throw new NicknameAlreadyTaken(dto.getNickname());
            }
            var pathToProfilePhoto = fileService.upload(dto.getProfilePhoto(), dto.getNickname());
            var profile = new Profile();
            var user = this.userRepository.findById(Integer.parseInt(userDetails.getId()));
            if(user.isEmpty()) {
                throw new UserNotFound("User not found");
            }
            profile.setProfilePhoto(pathToProfilePhoto.toString());
            profile.setNickname(dto.getNickname());
            profile.setUser(user.get());
            return this.profileRepository.save(profile);
        } catch (DataIntegrityViolationException exception) {
            logger.error(exception.getMessage(), exception);
            throw new UserAlreadyHaveAProfile("User already has a profile");
        }
    }
}
