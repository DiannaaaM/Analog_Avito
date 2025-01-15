package ru.skypro.homework.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class AvatarEntity implements Serializable {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String path;

    @OneToOne
    private UserEntity user;

    @Lob
    @Getter
    @Setter
    private byte[] data;
}
