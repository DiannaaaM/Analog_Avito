package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ImageDTO;

import java.io.IOException;
import java.util.List;

@Service
public interface ImageService {
    ImageDTO uploadImage(MultipartFile file) throws IOException;
    byte[] getImageDataFromPath(Long id) throws IOException;
    void deleteImage(Long id);
    List<ImageDTO> getAdImages(Long id);
}
