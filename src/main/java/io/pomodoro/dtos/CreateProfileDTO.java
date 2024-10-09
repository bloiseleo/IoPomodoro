package io.pomodoro.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProfileDTO {
    @NotNull
    private MultipartFile profilePhoto;
    @NotEmpty
    @Length(min = 3, max = 20)
    private String nickname;
}
