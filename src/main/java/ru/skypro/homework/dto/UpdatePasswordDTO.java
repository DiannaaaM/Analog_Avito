package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class UpdatePasswordDTO {
    private String currentPassword;
    private String newPassword;
}
