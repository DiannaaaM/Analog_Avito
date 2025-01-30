package ru.skypro.homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UpdatePasswordDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.mapper.EntityMapper;
import ru.skypro.homework.model.AvatarEntity;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.service.impl.AvatarServiceImpl;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    private final EntityMapper mapper;

    private final AvatarServiceImpl avatarService;

    public UserController(UserServiceImpl userService, EntityMapper mapper, AvatarServiceImpl avatarService) {
        this.userService = userService;
        this.mapper = mapper;
        this.avatarService = avatarService;
    }

    /**
     * Устанавливает новый пароль для пользователя.
     *
     * @param password объект {@link UpdatePasswordDTO}, содержащий информацию о новом пароле
     * @return объект ResponseEntity, содержащий сообщение об успешном выполнении, если пароль соответствует требованиям,
     * или сообщение об ошибке, если длина пароля не составляет от 8 до 16 символов
     */
    @PostMapping("/set_password")
    public ResponseEntity<String> setPassword(@RequestBody UpdatePasswordDTO password) {
        if (password.getNewPassword().length() < 8 || password.getNewPassword().length() > 16) {
            return ResponseEntity.badRequest().body("Пароль не проходит по требованиям. Длина пароля должна быть не менее 8 символов и не более 16!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Пароль успешно изменен");
    }

    /**
     * Получает информацию о текущем пользователе.
     *
     * @return объект ResponseEntity, содержащий UserDTO текущего пользователя, если он найден,
     * или статус NOT_FOUND, если пользователь не найден
     */
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUserInfo() {
        Optional<UserEntity> userEntity = userService.getCurrentUser();
        if (userEntity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        UserDTO userDTO = mapper.userEntityToUserDTO( userEntity.orElse( null ) );
        return ResponseEntity.ok(userDTO);
    }

    /**
     * Обновляет информацию о текущем пользователе.
     *
     * @param updateUser объект UserDTO, содержащий обновленную информацию о пользователе
     * @return объект ResponseEntity, содержащий идентификатор обновленного пользователя
     */
    @PatchMapping("/me")
    public ResponseEntity<Long> updateUserInfo(@RequestBody UserDTO updateUser) {
        long updatedUserId = userService.updateUserInfo(updateUser);
        return ResponseEntity.ok(updatedUserId);
    }

    /**
     * Обновляет изображение аватара пользователя.
     *
     * @param image — многокомпонентный файл, содержащий новое изображение для загрузки
     * @return — объект ResponseEntity, содержащий идентификатор загруженного аватара в случае успеха или статус INTERNAL_SERVER_ERROR в случае сбоя загрузки
     */
    @PatchMapping("/me/image")
    public ResponseEntity<Long> updateImage(@RequestParam MultipartFile image) {
        try {
            AvatarEntity avatarEntity = avatarService.uploadImage(image);
            return ResponseEntity.ok(avatarEntity.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Загружает изображение для конкретного пользователя.
     *
     * @param userId    идентификатор пользователя, для которого загружается изображение
     * @param imageFile загружаемый файл изображения
     * @return объект ResponseEntity, содержащий сообщение об успешном выполнении, если загрузка прошла успешно,
     * или сообщение об ошибке, если загрузка не удалась
     */
    @PostMapping("/{userId}/image")
    public ResponseEntity<String> uploadImage(@PathVariable Long userId, @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            avatarService.uploadImage( imageFile);
            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed: " + e.getMessage());
        }
    }
}