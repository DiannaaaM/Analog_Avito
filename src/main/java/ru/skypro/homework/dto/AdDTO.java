package ru.skypro.homework.dto;

public class AdDTO {
    private Long id;
    private String title;
    private String description;
    private String photo;
    private Integer price;
    private Long ownerId; // Ссылка на идентификатор владельца
    private String comments;

    public AdDTO() {
    }

    public AdDTO(Long id, String title, String description, String photo, Integer price, Long ownerId, String comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.photo = photo;
        this.price = price;
        this.ownerId = ownerId;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
