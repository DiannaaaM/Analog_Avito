package ru.skypro.homework.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.RoleDTO;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Setter
@Getter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private RoleDTO role;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "avatar_id")
    private AvatarEntity avatar;


    public UserEntity() {
    }

    public UserEntity(String username, String password, String firstName, String lastName, String phone, RoleDTO role, AvatarEntity avatar) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.role = role;
        this.avatar = avatar;
    }

}
