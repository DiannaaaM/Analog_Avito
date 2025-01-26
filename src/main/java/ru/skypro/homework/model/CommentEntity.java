package ru.skypro.homework.model;

import jakarta.persistence.*;
@Entity
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private Long createdAt;
    @ManyToOne
    private UserEntity author;
    @OneToOne
    @JoinColumn(name = "ad_id")
    private AdEntity ad;

    public CommentEntity() {
    }

    public CommentEntity(String text, Long createdAt, UserEntity author, AdEntity ad) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
