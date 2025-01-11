package ru.skypro.homework.dto;

import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.UserEntity;

public class CreateOrUpdateCommentDTO {
    private Long id;
    private String text;
    private Long createdAt;
    private UserEntity author;
    private AdEntity ad;

    public CreateOrUpdateCommentDTO() {
    }

    public CreateOrUpdateCommentDTO(Long id, String text, Long createdAt, UserEntity author, AdEntity ad) {
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

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public AdEntity getAd() {
        return ad;
    }

    public void setAd(AdEntity ad) {
        this.ad = ad;
    }
}
