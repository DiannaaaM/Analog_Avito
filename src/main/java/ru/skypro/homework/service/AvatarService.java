package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.AvatarEntity;

import java.io.IOException;

public interface AvatarService {
    AvatarEntity uploadImage(MultipartFile file) throws IOException;
    byte[] getImageDataFromPath(Long id) throws IOException;
}
