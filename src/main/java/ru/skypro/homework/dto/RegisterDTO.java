package ru.skypro.homework.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Data transfer object for user registration")
public class RegisterDTO {

    @ApiModelProperty(value = "Username for registration", required = true)
    private String username;

    @ApiModelProperty(value = "Password for registration", required = true)
    private String password;

    @ApiModelProperty(value = "First name of the user")
    private String firstName;

    @ApiModelProperty(value = "Last name of the user")
    private String lastName;

    @ApiModelProperty(value = "Phone number of the user")
    private String phone;

    @ApiModelProperty(value = "Role of the user")
    private RoleDTO role;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
