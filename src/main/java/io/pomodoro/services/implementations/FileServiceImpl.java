package io.pomodoro.services.implementations;

import io.pomodoro.services.interfaces.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {
    private final Path uploadDir = Path.of("upload");
    private Path createNicknamePath(String nickname) {
        return Path.of(uploadDir.toString(), nickname);
    }
    @Override
    public Path upload(MultipartFile file, String nickname) {
        try {
            if(!Files.exists(uploadDir)) {
                Files.createDirectory(uploadDir);
            }
            var nicknamePath = createNicknamePath(nickname);
            if(!Files.exists(nicknamePath)) {
                Files.createDirectory(nicknamePath);
            }
            var path = Path.of(nicknamePath.toString(), "profile.png");
            file.transferTo(path);
            return path;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
