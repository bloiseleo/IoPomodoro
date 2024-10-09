package io.pomodoro.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileService {
    Path upload(MultipartFile file, String nickname);
}
