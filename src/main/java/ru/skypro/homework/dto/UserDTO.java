package ru.skypro.homework.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

@ApiModel(description = "Data transfer object for user information")
public class UserDTO {
    @ApiModelProperty(value = "Unique identifier for the user", required = true)
    private Long id;

    @ApiModelProperty(value = "Username of the user", required = true)
    private String username;

    @ApiModelProperty(value = "First name of the user")
    private String firstName;

    @ApiModelProperty(value = "Last name of the user")
    private String lastName;

    @ApiModelProperty(value = "Phone number of the user")
    private String phone;

    @ApiModelProperty(value = "Role of the user")
    private RoleDTO role;

    @ApiModelProperty(value = "Profile image of the user")
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
