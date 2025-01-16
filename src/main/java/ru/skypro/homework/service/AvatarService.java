package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.AvatarEntity;
import ru.skypro.homework.repository.AvatarRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class AvatarService {
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private AvatarRepository avatarRepository;

    public AvatarEntity uploadImage(MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = uuid + extension;
        Path filePath = Paths.get(uploadPath, fileName);
        Files.createDirectories(filePath.getParent());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        AvatarEntity avatar = new AvatarEntity();
        avatar.setName(fileName);
        avatar.setPath(filePath.toString());
        avatar.setData(file.getBytes());
        return avatarRepository.save(avatar);
    }

    public byte[] getImageData(Long id) {
        AvatarEntity image = avatarRepository.findById(id);
        return image != null ? image.getData() : null;
    }

    public byte[] getImageDataFromPath(Long id) throws IOException {
        AvatarEntity image = avatarRepository.findById(id);
        if (image != null) {
            Path filePath = Paths.get(image.getPath());
            return Files.readAllBytes(filePath);
        }
        return null;
    }

    public List<AvatarEntity> getImagesByAdId(Long id) {
        return  avatarRepository.getImagesByAdId( id );
    }
}
