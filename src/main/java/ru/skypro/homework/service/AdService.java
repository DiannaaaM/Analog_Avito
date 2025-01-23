package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.model.ImageEntity;

import java.io.IOException;
import java.util.List;

public interface AdService {
    AdDTO getAd(Long id);
    AdDTO createAd(AdDTO adDTO);
    AdDTO updateInfoAd(long id, AdDTO ad) throws IOException;
    void deleteAd(Long id);
    List<AdDTO> findAllAds();
    List<AdDTO> findAdsOfUser(long id);
    String uploadImage(MultipartFile imageFile) throws IOException;
    void addImageToAd(Long adId, ImageEntity imageEntity);
}
