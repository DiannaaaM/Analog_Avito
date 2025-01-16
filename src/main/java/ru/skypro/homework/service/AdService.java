package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.mapper.EntityMapper;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.AvatarEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.AvatarRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class AdService {
    @Autowired
    private AdRepository adRepository;

    @Autowired
    private EntityMapper mapper;

    @Autowired
    private AvatarRepository avatarRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public AdDTO getAd(Long id) {
        AdEntity adEntity = adRepository.findById(id);
        return mapper.adEntityToAdDTO(adEntity);
    }

    public long createAd(AdDTO adDTO) {
        AdEntity adEntity = mapper.adDTOToAdEntity(adDTO);
        AdEntity savedAd = adRepository.save(adEntity);
        return savedAd.getId();
    }

    public AdDTO updateInfoAd(long id, AdDTO ad) {
        AdEntity existingAd = adRepository.findById(id);

        existingAd.setTitle(ad.getTitle());
        existingAd.setDescription(ad.getDescription());
        existingAd.setPrice(ad.getPrice());
        existingAd.setComments(ad.getComments());

        if (ad.getPhoto() != null && !ad.getPhoto().isEmpty()) {
            existingAd.setPhoto(uploadImage(ad.getPhoto()));
        }

        AdEntity updatedAd = adRepository.save(existingAd);
        return mapper.adEntityToAdDTO(updatedAd);
    }

    public void deleteAd(Long id) {
        if (!adRepository.existsById(id)) {
            throw new RuntimeException("Ad not found with id: " + id);
        }
        adRepository.deleteById(id);
    }

    public List<AdEntity> findAllAds() {
        return adRepository.findAll();
    }

    public List<AdEntity> findAdsOfUser(long id) {
        return adRepository.findByOwnerId(id);
    }

    private String uploadImage(MultipartFile imageFile) {
        try {
            String imagePath = uploadPath + "/" + imageFile.getOriginalFilename();
            Path path = Paths.get(imagePath);

            Files.copy(imageFile.getInputStream(), path);

            return imagePath;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image: " + e.getMessage());
        }
    }

    public void addImageToAd(Long adId, AvatarEntity avatarEntity) {
        AdEntity ad = adRepository.findById(adId);
        if (ad != null) {
            ad.getImages().add(avatarEntity);
            adRepository.save(ad);
        } else {
            throw new RuntimeException("Ad not found with id: " + adId);
        }
    }

    public List<AvatarEntity> getImagesByAdId(Long adId) {
        AdEntity ad = adRepository.findById(adId);
        if (ad != null) {
            return ad.getImages();
        } else {
            throw new RuntimeException("Ad not found with id: " + adId);
        }
    }
}
