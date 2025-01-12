package ru.skypro.homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CreateOrUpdateImageDTO;
import ru.skypro.homework.dto.UpdatePasswordDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/set_password")
    public ResponseEntity setPassword(@RequestParam UpdatePasswordDTO password) {
        System.out.println( "Придумай пароль, длина которого составляет от 8 до 16 символов" );
        if (password.getNewPassword().length() < 8 || password.getNewPassword().length() > 16) {
            System.out.println( "Пароль не проходит по требованиям. " +
                    "Попробуй еще раз. " +
                    "Длина пароля должна быть не менее 8 символов и не более 16!" );
            ResponseEntity.badRequest();
        }
        return (ResponseEntity) ResponseEntity.status( 201 );
    }

    @GetMapping("/me")
    public UserEntity getUserInfo() {
        return null;
    }

    @PatchMapping("/me")
    public long updateUserInfo(@RequestParam UserDTO updateUser) {
        return userService.updateUserInfo(updateUser);
    }

    @PatchMapping("/me/image")
    public long updateImage(@RequestParam CreateOrUpdateImageDTO image) {
        return userService.updateUserImage(image);
    }

}
