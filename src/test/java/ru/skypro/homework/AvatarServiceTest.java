package ru.skypro.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.AvatarEntity;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.service.AvatarService;
import ru.skypro.homework.service.impl.AvatarServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AvatarServiceTest {

    @InjectMocks
    private AvatarServiceImpl avatarService;

    @Mock
    private AvatarRepository avatarRepository;

    private MultipartFile file;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        file = mock(MultipartFile.class);
    }

    @Test
    void uploadImage_ShouldReturnAvatarEntity() throws IOException {
        when(file.getOriginalFilename()).thenReturn("avatar.png");
        when(file.getInputStream()).thenReturn(Files.newInputStream(Paths.get("src/test/resources/test.png")));

        AvatarEntity avatar = avatarService.uploadImage(file);

        assertNotNull(avatar);
        verify(avatarRepository).save(avatar);
    }

    @Test
    void getImageDataFromPath_ShouldReturnImageData() throws IOException {
        AvatarEntity avatarEntity = new AvatarEntity();
        avatarEntity.setImagePath("src/test/resources/test.png");
        when(avatarRepository.findById(1L)).thenReturn( Optional.of( avatarEntity ) );

        byte[] imageData = avatarService.getImageDataFromPath(1L);

        assertNotNull(imageData);
        assertTrue(imageData.length > 0);
    }
}

