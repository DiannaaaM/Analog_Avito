package ru.skypro.homework.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
    private UserEntity owner;
    private String comments;

    public AdEntity() {
    }

    public AdEntity(Long id, String title, String description, List<ImageEntity> images, Integer price, UserEntity owner, String comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.images = images;
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

    public void setId(long adId) {
    }
}
