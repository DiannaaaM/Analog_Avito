package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.ImageEntity;
import ru.skypro.homework.repository.ImageRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private ImageRepository imageRepository;

    public ImageEntity uploadImage(MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = uuid + extension;
        Path filePath = Paths.get(uploadPath, fileName);

        Files.createDirectories(filePath.getParent());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        ImageEntity image = new ImageEntity();
        image.setImagePath(filePath.toString());
        return imageRepository.save(image);
    }

    public byte[] getImageDataFromPath(Long id) throws IOException {
        Optional<ImageEntity> image = imageRepository.findById(id);

        Path filePath = Paths.get( image.get().getImagePath());
        return Files.readAllBytes(filePath);
    }

    public void deleteImage(Long id) {
        Optional<ImageEntity> image = imageRepository.findById(id);

        Path filePath = Paths.get( image.get().getImagePath());
        try {
            Files.delete(filePath);
            imageRepository.delete(image);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete image: " + e.getMessage());
        }
    }
}