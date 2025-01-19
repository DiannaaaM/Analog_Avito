package ru.skypro.homework.dto;

import org.springframework.web.multipart.MultipartFile;

public class UserDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private RoleDTO role;
    private MultipartFile image;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, String firstName, String lastName, String phone, RoleDTO role, MultipartFile image) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.role = role;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public MultipartFile getAvatar() {
        return image;
    }

    public void setAvatar(MultipartFile image) {
        this.image = image;
    }
}
