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
import ru.skypro.homework.service.AvatarService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EntityMapper mapper;

    @Autowired
    private AvatarService avatarService;

    @PostMapping("/set_password")
    public ResponseEntity<String> setPassword(@RequestBody UpdatePasswordDTO password) {
        if (password.getNewPassword().length() < 8 || password.getNewPassword().length() > 16) {
            return ResponseEntity.badRequest().body("Пароль не проходит по требованиям. Длина пароля должна быть не менее 8 символов и не более 16!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Пароль успешно изменен");
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUserInfo() {
        UserEntity userEntity = userService.getCurrentUser();
        if (userEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        UserDTO userDTO = mapper.userEntityToUserDTO(userEntity);
        return ResponseEntity.ok(userDTO);
    }

    @PatchMapping("/me")
    public ResponseEntity<Long> updateUserInfo(@RequestBody UserDTO updateUser) {
        long updatedUserId = userService.updateUserInfo(updateUser);
        return ResponseEntity.ok(updatedUserId);
    }

    @PatchMapping("/me/image")
    public ResponseEntity<Long> updateImage(@RequestParam MultipartFile image) {
        try {
            AvatarEntity avatarEntity = avatarService.uploadImage(image);
            return ResponseEntity.ok(avatarEntity.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

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
