package ru.skypro.homework.controller;

import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;

import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdsController {
    @GetMapping
    public List<Ad> getAllAds() {
        return null;
    }

    @PostMapping
    public Ad createNewAd(@RequestBody CreateOrUpdateAd ad) {
        return null;
    }

    @GetMapping("/{id}")
    public Ad getAdById(@PathVariable long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteAd(@PathVariable long id) {
    }

    @PatchMapping("/{id}")
    public Ad updateAd(@PathVariable long id, @RequestBody CreateOrUpdateAd ad) {
        return null;
    }

    @GetMapping("/me")
    public List<Ad> getMineAds() {
        return null;
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getComments(@PathVariable long id) {
        return null;
    }

    @PostMapping("/{id}/comments")
    public void addComment(@PathVariable long id, @RequestBody CreateOrUpdateComment comment) {
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public void deleteComment(@PathVariable long adId, @PathVariable long commentId) {
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public void updateCommentInfo(@PathVariable long adId, @PathVariable long commentId, @RequestBody CreateOrUpdateComment comment) {
    }
}
