package io.pomodoro.services;

import io.pomodoro.dtos.CreateProfileDTO;
import io.pomodoro.entities.Profile;
import io.pomodoro.entities.Roles;
import io.pomodoro.entities.User;
import io.pomodoro.exceptions.NicknameAlreadyTaken;
import io.pomodoro.exceptions.UserAlreadyHaveAProfile;
import io.pomodoro.infra.UserDetailsImpl;
import io.pomodoro.repositories.ProfileRepository;
import io.pomodoro.repositories.UserRepository;
import io.pomodoro.services.implementations.ProfileServiceImpl;
import io.pomodoro.services.interfaces.FileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import java.nio.file.Path;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {
    @Mock
    private FileService fileService;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ProfileServiceImpl profileService;
    private MockMultipartFile createFile() {
        var content = new byte[10];
        for (int i = 0; i < 10; i++) {
            content[i] = (byte) i;
        }
        return new MockMultipartFile("teste.png", content);
    }
    @Test
    public void ShouldCreateProfileForUser() {
        var profile = new Profile();
        profile.setId(1);
        profile.setNickname("teste");
        profile.setProfilePhoto("/teste");
        var user = new User();
        user.setId(1);
        user.setEmail("teste@gmail.com");
        user.setPassword("password");
        Mockito.when(
                profileRepository.existsProfileByNickname(Mockito.anyString())
        ).thenReturn(false);
        Mockito.when(
                fileService.upload(Mockito.any(), Mockito.anyString())
        ).thenReturn(Path.of("teste"));
        Mockito.when(
                profileRepository.save(Mockito.any())
        ).then(invocation -> profile);
        Mockito.when(
                userRepository.findById(Mockito.anyInt())
        ).thenReturn(Optional.of(user));
        var userDetails = new UserDetailsImpl();
        userDetails.setRole(Roles.NO_PROFILE.name());
        userDetails.setId("1");
        userDetails.setUsername("teste@gmail.com");
        userDetails.setPassword("");
        var dto = new CreateProfileDTO();
        dto.setNickname("teste");
        dto.setProfilePhoto(createFile());
        var result = profileService.createProfile(userDetails, dto);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(profile.getId(), result.getId());
        Assertions.assertEquals(profile.getNickname(), result.getNickname());
        Assertions.assertEquals(profile.getProfilePhoto(), result.getProfilePhoto());
        Assertions.assertEquals(profile.getProfilePhoto(), result.getProfilePhoto());
        Mockito.verify(profileRepository, Mockito.times(1)).existsProfileByNickname(dto.getNickname());
        Mockito.verify(fileService, Mockito.times(1)).upload(dto.getProfilePhoto(), dto.getNickname());
        Mockito.verify(userRepository, Mockito.times(1)).findById(user.getId());
    }
    @Test
    public void ShouldNotCreateDueNicknameExisting() {
        Mockito.when(
                profileRepository.existsProfileByNickname(Mockito.any())
        ).thenReturn(true);
        var userDetails = new UserDetailsImpl();
        userDetails.setRole(Roles.NO_PROFILE.name());
        userDetails.setId("1");
        userDetails.setUsername("teste@gmail.com");
        userDetails.setPassword("");
        var dto = new CreateProfileDTO();
        dto.setNickname("teste");
        dto.setProfilePhoto(createFile());
        Assertions.assertThrows(NicknameAlreadyTaken.class, () -> profileService.createProfile(userDetails, dto));
        Mockito.verify(profileRepository, Mockito.times(1)).existsProfileByNickname(dto.getNickname());
    }
    @Test
    public void ShouldNotCreateDueFileUploadFail() {
        Mockito.when(
                profileRepository.existsProfileByNickname(Mockito.anyString())
        ).thenReturn(false);
        Mockito.when(
                fileService.upload(Mockito.any(), Mockito.anyString())
        ).thenThrow(new RuntimeException());
        var userDetails = new UserDetailsImpl();
        userDetails.setRole(Roles.NO_PROFILE.name());
        userDetails.setId("1");
        userDetails.setUsername("teste@gmail.com");
        userDetails.setPassword("");
        var dto = new CreateProfileDTO();
        dto.setNickname("teste");
        dto.setProfilePhoto(createFile());
        Assertions.assertThrows(RuntimeException.class, () -> {
           profileService.createProfile(userDetails, dto);
        });
        Mockito.verify(profileRepository, Mockito.times(1)).existsProfileByNickname(dto.getNickname());
        Mockito.verify(fileService, Mockito.times(1)).upload(dto.getProfilePhoto(), dto.getNickname());
    }
    @Test
    public void ShouldNotCreateDueUserAlreadyHasProfile() {
        var userDetails = new UserDetailsImpl();
        userDetails.setRole(Roles.COMMON.name());
        userDetails.setId("1");
        userDetails.setUsername("teste@gmail.com");
        userDetails.setPassword("");
        var dto = new CreateProfileDTO();
        dto.setNickname("teste");
        dto.setProfilePhoto(createFile());
        Assertions.assertThrows(UserAlreadyHaveAProfile.class, () -> profileService.createProfile(userDetails, dto));
    }
}
