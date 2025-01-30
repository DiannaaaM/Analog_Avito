package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.mapper.EntityMapper;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.ImageEntity;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdServiceImpl implements AdService {
    @Autowired
    private AdRepository adRepository;

    @Autowired
    private ImageRepository imageRepository;

    private final EntityMapper mapper;
    @Autowired
    private UserService userService;

    @Autowired
    public AdServiceImpl(EntityMapper mapper) {
        this.mapper = mapper;
    }

    @Value("${upload.path}")
    private String uploadPath;

    /**
     * Извлекает объявление по его уникальному идентификатору.
     *
     * @param id - Уникальный идентификатор объявления.
     * @return объявление с указанным идентификатором или null, если оно не найдено.
     */
    public AdDTO getAd(Long id) {
        Optional<AdEntity> adEntity = adRepository.findById(id);
        return mapper.adEntityToAdDTO(adEntity.orElse(null));
    }

    /**
     * Создает новое объявление.
     *
     * @param createAdDTO - параметр DTO, содержащий сведения о новом объявлении.
     * @return только что созданное объявление.
     * @throws RuntimeException, если текущий пользователь не найден.
     */
    public AdDTO createAd(AdDTO createAdDTO) {
        UserEntity currentUser = userService.getCurrentUser()
                .orElseThrow(() -> new RuntimeException("User not found"));

        AdEntity adEntity = new AdEntity();
        adEntity.setTitle(createAdDTO.getTitle());
        adEntity.setDescription(createAdDTO.getDescription());
        adEntity.setPrice(createAdDTO.getPrice());
        adEntity.setOwner(currentUser);

        AdEntity savedAd = adRepository.save(adEntity);

        return mapper.adEntityToAdDTO(savedAd);
    }

    /**
     * Обновляет информацию о существующем объявлении.
     *
     * @param id Уникальный идентификатор рекламы для обновления.
     * @param ad DTO, содержащий обновленные сведения о рекламе.
     * @return Обновленная реклама.
     * @throws IOException Если при загрузке изображения возникает ошибка.
     */
    public AdDTO updateInfoAd(long id, AdDTO ad) throws IOException {
        Optional<AdEntity> existingAd = adRepository.findById(id);

        existingAd.get().setTitle(ad.getTitle());
        existingAd.get().setDescription(ad.getDescription());
        existingAd.get().setPrice(ad.getPrice());
        existingAd.get().setComments(ad.getComments());

        if (ad.getImage() != null && !ad.getImage().isEmpty()) {
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setImagePath(uploadImage(ad.getImage()));
            existingAd.get().getImages().add(imageEntity);
        }

        Optional<AdEntity> updatedAd = adRepository.save(existingAd);
        return mapper.adEntityToAdDTO(updatedAd.orElse(null));
    }

    /**
     * Удаляет объявление по его уникальному идентификатору.
     *
     * @param id - уникальный идентификатор удаляемой рекламы.
     * @throws RuntimeException, если реклама с указанным идентификатором не найдена.
     */
    public void deleteAd(Long id) {
        if (!adRepository.existsById(id)) {
            throw new RuntimeException("Ad not found with id: " + id);
        }
        adRepository.deleteById(id);
    }

    /**
     * Получает все объявления.
     *
     * @return Список всех объявлений.
     */
    public List<AdDTO> findAllAds() {
        List<AdEntity> adEntities = adRepository.findAll();
        return mapper.adEntitiesToAdDTOs(adEntities);
    }

    /**
     * Получает все объявления, созданные конкретным пользователем.
     *
     * @param id Уникальный идентификатор пользователя.
     * @return Список объявлений, созданных пользователем.
     */
    public List<AdDTO> findAdsOfUser(long id) {
        List<AdEntity> adEntities = adRepository.findByOwnerId(id);
        return mapper.adEntitiesToAdDTOs(adEntities);
    }

    /**
     * Загружает файл изображения и возвращает путь к загруженному файлу.
     *
     * @param imageFile Файл изображения для загрузки.
     * @return Путь к загруженному файлу.
     * @throws IOException Если при загрузке изображения возникает ошибка.
     */
    public String uploadImage(MultipartFile imageFile) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String extension = imageFile.getOriginalFilename().substring(imageFile.getOriginalFilename().lastIndexOf("."));
        String imagePath = uploadPath + "/" + uuid + extension;
        Path path = Paths.get(imagePath);

        Files.createDirectories(path.getParent());
        Files.copy(imageFile.getInputStream(), path);

        return imagePath;
    }

    /**
     * Добавляет изображение к существующей рекламе.
     *
     * @param - уникальный идентификатор рекламы.
     * @param imageEntity - объект изображения, который нужно добавить в рекламу.
     */
    public void addImageToAd(Long adId, ImageEntity imageEntity) {
        Optional<AdEntity> ad = adRepository.findById(adId);

        imageEntity.setAd(ad.orElse(null));

        ImageEntity savedImage = imageRepository.save(imageEntity);

        ad.get().getImages().add(savedImage);

        adRepository.save(ad);
    }
}


