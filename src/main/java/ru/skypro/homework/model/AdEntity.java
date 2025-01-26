package ru.skypro.homework.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "ads")
@Setter
@Getter
public class AdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL)
    private List<ImageEntity> images;
    private Integer price;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String comments;

    public AdEntity() {
    }

    public AdEntity(Long id, String title, String description, List<ImageEntity> images, Integer price, UserEntity user, String comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.images = images;
        this.price = price;
        this.user = user;
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

    public List<ImageEntity> getImages() {
        return images;
    }

    public void setImages(List<ImageEntity> images) {
        this.images = images;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setId(long adId) {
    }
}
