package ru.skypro.homework.controller;

import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.CommentEntity;

import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdsController {
    @GetMapping
    public List<AdEntity> getAllAds() {
        return null;
    }

    @PostMapping
    public AdEntity createNewAd(@RequestBody CreateOrUpdateAdDTO ad) {
        return null;
    }

    @GetMapping("/{id}")
    public AdEntity getAdById(@PathVariable long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteAd(@PathVariable long id) {
    }

    @PatchMapping("/{id}")
    public AdEntity updateAd(@PathVariable long id, @RequestBody CreateOrUpdateAdDTO ad) {
        return null;
    }

    @GetMapping("/me")
    public List<AdEntity> getMineAds() {
        return null;
    }

    @GetMapping("/{id}/comments")
    public List<CommentEntity> getComments(@PathVariable long id) {
        return null;
    }

    @PostMapping("/{id}/comments")
    public void addComment(@PathVariable long id, @RequestBody CreateOrUpdateCommentDTO comment) {
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public void deleteComment(@PathVariable long adId, @PathVariable long commentId) {
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public void updateCommentInfo(@PathVariable long adId, @PathVariable long commentId, @RequestBody CreateOrUpdateCommentDTO comment) {
    }
}
