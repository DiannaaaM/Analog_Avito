package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ImageDTO;
import ru.skypro.homework.mapper.EntityMapper;
import ru.skypro.homework.model.ImageEntity;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private EntityMapper mapper;

    /**
     * Загружает изображение на сервер и сохраняет путь к нему в базе данных.
     *
     * @param file - объект MultipartFile, содержащий загружаемое изображение.
     * @return объект ImageDTO, представляющий загруженное изображение.
     * @throws IOException, если во время обработки файла возникает ошибка.
     */
    public ImageDTO uploadImage(MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = uuid + extension;
        Path filePath = Paths.get(uploadPath, fileName);

        Files.createDirectories(filePath.getParent());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        ImageEntity image = new ImageEntity();
        image.setImagePath(filePath.toString());
        return mapper.imageEntityToImageDTO(imageRepository.save(image));
    }

    /**
     * Получает данные изображения по указанному пути.
     *
     * @param id Уникальный идентификатор изображения.
     * @return Массив байтов, содержащий данные изображения.
     * @throws IOException Если при чтении файла возникает ошибка.
     */
    public byte[] getImageDataFromPath(Long id) throws IOException {
        Optional<ImageEntity> image = imageRepository.findById(Math.toIntExact(id));

        Path filePath = Paths.get( image.get().getImagePath());
        return Files.readAllBytes(filePath);
    }

    /**
     * Удаляет изображение с указанным идентификатором с сервера и из базы данных.
     *
     * @param id - Уникальный идентификатор изображения.
     */
    public void deleteImage(Long id) {
        Optional<ImageEntity> image = imageRepository.findById(Math.toIntExact(id));

        Path filePath = Paths.get( image.get().getImagePath());
        try {
            Files.delete(filePath);
            imageRepository.deleteById(Math.toIntExact(id));
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete image: " + e.getMessage());
        }
    }

    /**
     * Извлекает список объектов {@link ImageDTO}, представляющих изображения, связанные с указанным идентификатором объявления.
     *
     * @param id - Уникальный идентификатор объявления.
     * @return список объектов ImageDTO.
     */
    public List<ImageDTO> getAdImages(Long id) {
        return mapper.imageEntitiesToImageDTOs(imageRepository.getImagesByAdId(id));
    }

    /**
     * Обновляет путь к изображению для указанного идентификатора изображения.
     *
     * @param imagePath Новый путь к изображению.
     * @param imageId Уникальный идентификатор изображения.
     * @param upload Объект MultipartFile, содержащий обновленное изображение.
     */
    public void updateImage(String imagePath, Integer imageId, MultipartFile upload) {
        imageRepository.uploadImage(imagePath, imageId);
    }
}
