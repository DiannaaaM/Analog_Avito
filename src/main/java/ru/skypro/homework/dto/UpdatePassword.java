package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class UpdatePassword {
    private String currentPassword;
    private String newPassword;
}
