package ru.skypro.homework.model;

import org.springframework.stereotype.Component;

@Component
public class Ad {
    private String title;
    private String description;
    private String photo;
    private User owner;
    private String comments;

    public Ad() {
    }

    public Ad(String title, String description, String photo, User owner, String comments) {
        this.title = title;
        this.description = description;
        this.photo = photo;
        this.owner = owner;
        this.comments = comments;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
