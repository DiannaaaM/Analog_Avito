package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.mapper.EntityMapper;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.ImageEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImageRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class AdService {
    @Autowired
    private AdRepository adRepository;

    @Autowired
    private EntityMapper mapper;

    @Autowired
    private ImageRepository imageRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public AdDTO getAd(Long id) {
        AdEntity adEntity = adRepository.findById(id);
        return mapper.adEntityToAdDTO(adEntity);
    }

    public AdDTO createAd(AdDTO adDTO) {
        AdEntity adEntity = mapper.adDTOToAdEntity(adDTO);
        AdEntity savedAd = adRepository.save(adEntity);
        return mapper.adEntityToAdDTO(savedAd);
    }

    public AdDTO updateInfoAd(long id, AdDTO ad) throws IOException {
        AdEntity existingAd = adRepository.findById(id);

        existingAd.setTitle(ad.getTitle());
        existingAd.setDescription(ad.getDescription());
        existingAd.setPrice(ad.getPrice());
        existingAd.setComments(ad.getComments());

        if (ad.getPhoto() != null && !ad.getPhoto().isEmpty()) {
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setImagePath(uploadImage(ad.getPhoto()));
            existingAd.getImages().add(imageEntity);
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

    public List<AdDTO> findAllAds() {
        List<AdEntity> adEntities = adRepository.findAll();
        return mapper.adEntitiesToAdDTOs(adEntities);
    }

    public List<AdDTO> findAdsOfUser(long id) {
        List<AdEntity> adEntities = adRepository.findByOwnerId(id);
        return mapper.adEntitiesToAdDTOs(adEntities);
    }

    private String uploadImage(MultipartFile imageFile) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String extension = imageFile.getOriginalFilename().substring(imageFile.getOriginalFilename().lastIndexOf("."));
        String imagePath = uploadPath + "/" + uuid + extension;
        Path path = Paths.get(imagePath);

        Files.createDirectories(path.getParent());
        Files.copy(imageFile.getInputStream(), path);

        return imagePath;
    }

    public void addImageToAd(Long adId, ImageEntity imageEntity) {
        AdEntity ad = adRepository.findById(adId);

        imageEntity.setAd(ad);

        ImageEntity savedImage = imageRepository.save(imageEntity);

        ad.getImages().add(savedImage);

        adRepository.save(ad);
    }
}
