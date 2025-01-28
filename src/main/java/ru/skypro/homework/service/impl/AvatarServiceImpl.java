package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.AvatarEntity;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.service.AvatarService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class AvatarServiceImpl implements AvatarService {
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private AvatarRepository avatarRepository;

    /**
     * Этот метод обрабатывает загрузку файла изображения и сохраняет его по указанному пути загрузки.
     * Он генерирует уникальный идентификатор для файла изображения и копирует его по пути загрузки.
     * Затем путь к изображению сохраняется в базе данных.
     *
     * @param file Объект MultipartFile, представляющий загруженный файл изображения.
     * @return Объект AvatarEntity, содержащий сохраненный путь к изображению.
     * @throws IOException Если во время обработки файла или операций с базой данных возникает ошибка.
     */
    public AvatarEntity uploadImage(MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = uuid + extension;
        Path filePath = Paths.get(uploadPath, fileName);

        Files.createDirectories(filePath.getParent());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        AvatarEntity avatar = new AvatarEntity();
        avatar.setImagePath(filePath.toString());
        return avatarRepository.save(avatar);
    }

    /**
     * Этот метод извлекает данные изображения из указанного пути к изображению.
     * Он извлекает объект AvatarEntity из базы данных по указанному идентификатору,
     * а затем считывает данные изображения из пути к файлу.
     *
     * @param id Идентификатор объекта AvatarEntity, из которого нужно извлечь данные изображения.
     * @return Массив байтов, содержащий данные изображения.
     * @throws IOException Если во время обработки файла или операций с базой данных возникает ошибка.
     */
    public byte[] getImageDataFromPath(Long id) throws IOException {
        Optional<AvatarEntity> avatar = avatarRepository.findById(id);

        Path filePath = Paths.get(avatar.get().getImagePath());
        return Files.readAllBytes(filePath);
    }
}
