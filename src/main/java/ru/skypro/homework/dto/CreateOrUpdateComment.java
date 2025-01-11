package ru.skypro.homework.dto;

import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.User;

public class CreateOrUpdateComment {
    private Long id;
    private String text;
    private Long createdAt;
    private User author;
    private Ad ad;

    public CreateOrUpdateComment() {
    }

    public CreateOrUpdateComment(Long id, String text, Long createdAt, User author, Ad ad) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.author = author;
        this.ad = ad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }
}
