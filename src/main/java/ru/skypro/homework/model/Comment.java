package ru.skypro.homework.model;

import org.springframework.stereotype.Component;

@Component
public class Comment {
    private String text;
    private Long createdAt;
    private User author;
    private Ad ad;

    public Comment() {
    }

    public Comment(String text, Long createdAt, User author, Ad ad) {
        this.text = text;
        this.createdAt = createdAt;
        this.author = author;
        this.ad = ad;
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
