package ru.skypro.homework.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private Long createdAt;
    @ManyToOne
    private User author;
    @OneToOne
    @JoinColumn(name = "ad_id")
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
