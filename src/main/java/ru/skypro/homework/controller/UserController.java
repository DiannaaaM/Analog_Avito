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

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private EntityMapper mapper;

    @Autowired
    private AvatarServiceImpl avatarService;

    /**
     * Sets a new password for the user.
     *
     * @param password an UpdatePasswordDTO object containing the new password information
     * @return a ResponseEntity containing a success message if the password meets the requirements,
     *         or a bad request message if the password length is not between 8 and 16 characters
     */
    @PostMapping("/set_password")
    public ResponseEntity<String> setPassword(@RequestBody UpdatePasswordDTO password) {
        if (password.getNewPassword().length() < 8 || password.getNewPassword().length() > 16) {
            return ResponseEntity.badRequest().body("Пароль не проходит по требованиям. Длина пароля должна быть не менее 8 символов и не более 16!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Пароль успешно изменен");
    }

    /**
     * Retrieves the information of the current user.
     *
     * @return a ResponseEntity containing the UserDTO of the current user if found,
     *         or a status of NOT_FOUND if the user does not exist
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
     * Updates the information of the current user.
     *
     * @param updateUser a UserDTO object containing the updated user information
     * @return a ResponseEntity containing the ID of the updated user
     */
    @PatchMapping("/me")
    public ResponseEntity<Long> updateUserInfo(@RequestBody UserDTO updateUser) {
        long updatedUserId = userService.updateUserInfo(updateUser);
        return ResponseEntity.ok(updatedUserId);
    }

    /**
     * Updates the user's avatar image.
     *
     * @param image the MultipartFile containing the new image to be uploaded
     * @return a ResponseEntity containing the ID of the uploaded avatar if successful,
     *         or a status of INTERNAL_SERVER_ERROR if the upload fails
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
     * Uploads an image for a specific user.
     *
     * @param userId    the ID of the user for whom the image is being uploaded
     * @param imageFile the image file to be uploaded
     * @return a ResponseEntity containing a success message if the upload is successful,
     *         or an error message if the upload fails
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