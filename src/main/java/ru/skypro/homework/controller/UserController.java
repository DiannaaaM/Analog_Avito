package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CreateOrUpdateImage;
import ru.skypro.homework.dto.UpdatePassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.model.User;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping("/set_password")
    public ResponseEntity setPassword(@RequestParam UpdatePassword password) {
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
    public User getUserInfo() {
        return null;
    }

    @PatchMapping("/me")
    public void updateUserInfo(@RequestParam UpdateUser updateUser) {
    }

    @PatchMapping("/me/image")
    public void updateImage(@RequestParam CreateOrUpdateImage image) {
    }

}
