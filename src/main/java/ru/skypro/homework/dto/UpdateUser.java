package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class UpdateUser {
    private String username;
    private String firstName;
    private String lastName;
    private String phone;

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }
}
