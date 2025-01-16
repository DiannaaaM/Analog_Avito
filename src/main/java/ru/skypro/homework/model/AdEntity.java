package ru.skypro.homework.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ads")
public class AdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String photo;
    private Integer price;
    @ManyToOne
    private UserEntity owner;
    private String comments;

    public AdEntity() {
    }

    public AdEntity(Long id, String title, String description, String photo, Integer price, UserEntity owner, String comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.photo = photo;
        this.price = price;
        this.owner = owner;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public <E> List getImages() {
        return null;
    }
}
