package ru.skypro.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ImageDTO;
import ru.skypro.homework.mapper.EntityMapper;
import ru.skypro.homework.model.ImageEntity;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.impl.ImageServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ImageServiceTest {

    @InjectMocks
    private ImageServiceImpl imageService;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private EntityMapper mapper;

    private MultipartFile file;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        file = mock(MultipartFile.class);
    }

    @Test
    void uploadImage_ShouldReturnImageDTO() throws IOException {
        when(file.getOriginalFilename()).thenReturn("image.png");
        when(file.getInputStream()).thenReturn(Files.newInputStream(Paths.get("src/test/resources/test.png")));

        ImageDTO imageDTO = imageService.uploadImage(file);

        assertNotNull(imageDTO);
        verify(imageRepository).save(any(ImageEntity.class));
    }

    @Test
    void deleteImage_ShouldDeleteImage() {
        long imageId = 1L;
        ImageEntity image = new ImageEntity();
        when(imageRepository.findById(imageId)).thenReturn( image );

        imageService.deleteImage(imageId);

        verify(imageRepository).delete(image);
    }
}
