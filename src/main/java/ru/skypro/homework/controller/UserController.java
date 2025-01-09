package ru.skypro.homework.controller;

import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CreateOrUpdateImage;
import ru.skypro.homework.dto.UpdatePassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.model.User;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping("/set_password")
    public void setPassword(@RequestParam UpdatePassword password) {
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
