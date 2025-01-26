package ru.skypro.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.mapper.EntityMapper;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.ImageEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.impl.AdServiceImpl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AdServiceTest {

    @InjectMocks
    private AdServiceImpl adService;

    @Mock
    private AdRepository adRepository;

    @Mock
    private EntityMapper mapper;

    @Mock
    private ImageRepository imageRepository;

    private MultipartFile imageFile;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        imageFile = mock(MultipartFile.class);
        when(imageFile.getOriginalFilename()).thenReturn("test.png");
        when(imageFile.getInputStream()).thenReturn(Files.newInputStream(Paths.get("src/test/resources/test.png")));
    }

    @Test
    void createAd_ShouldReturnAdDTO() {
        AdDTO adDTO = new AdDTO();
        AdEntity adEntity = new AdEntity();
        when(mapper.adDTOToAdEntity(adDTO)).thenReturn(adEntity);
        when(adRepository.save(adEntity)).thenReturn(adEntity);
        when(mapper.adEntityToAdDTO(adEntity)).thenReturn(adDTO);

        AdDTO createdAd = adService.createAd(adDTO);

        assertNotNull(createdAd);
        verify(adRepository).save(adEntity);
    }

    @Test
    void updateInfoAd_ShouldUpdateAd() throws Exception {
        long adId = 1L;
        AdDTO adDTO = new AdDTO();
        AdEntity existingAd = new AdEntity();
        existingAd.setPk(adId);
        when(adRepository.findById(adId)).thenReturn(existingAd);
        when(adRepository.save(existingAd)).thenReturn(existingAd);
        when(mapper.adEntityToAdDTO(existingAd)).thenReturn(adDTO);

        AdDTO updatedAd = adService.updateInfoAd(adId, adDTO);

        assertNotNull(updatedAd);
        verify(adRepository).save(existingAd);
    }

    @Test
    void deleteAd_ShouldDeleteAd() {
        long adId = 1L;
        when(adRepository.existsById(adId)).thenReturn(true);

        adService.deleteAd(adId);

        verify(adRepository).deleteById(adId);
    }

    @Test
    void findAllAds_ShouldReturnListOfAds() {
        AdEntity adEntity = new AdEntity();
        when(adRepository.findAll()).thenReturn(Collections.singletonList(adEntity));
        when(mapper.adEntitiesToAdDTOs(Collections.singletonList(adEntity))).thenReturn(Collections.singletonList(new AdDTO()));

        var ads = adService.findAllAds();

        assertFalse(ads.isEmpty());
        verify(adRepository).findAll();
    }

    @Test
    void uploadImage_ShouldReturnImagePath() throws Exception {
        String imagePath = adService.uploadImage(imageFile);

        assertNotNull(imagePath);
        verify(imageRepository).save(any(ImageEntity.class));
    }
}

