package ru.skypro.homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.AvatarEntity;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.AvatarService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdsController {
    @Autowired
    private AdService adService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private AvatarService avatarService;

    private long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserEntity user = (UserEntity) authentication.getPrincipal();
            return user.getId();
        }
        throw new RuntimeException("User not found");
    }

    @GetMapping
    public List<AdEntity> getAllAds() {
        return adService.findAllAds();
    }

    @PostMapping
    public long createNewAd(@RequestBody AdDTO ad) {
        return adService.createAd(ad);
    }

    @GetMapping("/{id}")
    public AdDTO getAdById(@PathVariable long id) {
        return adService.getAd(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAd(@PathVariable long id) {
        adService.deleteAd(id);
    }

    @PatchMapping("/{id}")
    public AdDTO updateAd(@PathVariable long id, @RequestBody AdDTO ad) {
        return adService.updateInfoAd(id, ad);
    }

    @GetMapping("/me")
    public List<AdEntity> getMineAds() {
        long userId = getCurrentUserId();
        return adService.findAdsOfUser(userId);
    }

    @GetMapping("/{id}/comments")
    public List<CommentEntity> getComments(@PathVariable long id) {
        return commentService.showCommentToAd(id);
    }

    @PostMapping("/{id}/comments")
    public long addComment(@PathVariable long id, @RequestBody CommentDTO comment) {
        return commentService.newComment(id, comment);
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public void deleteComment(@PathVariable long adId, @PathVariable long commentId) {
        commentService.deleteComm(adId, commentId);
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public void updateCommentInfo(@PathVariable long adId, @PathVariable long commentId, @RequestBody CommentDTO comment) {
        commentService.updateCommentInfo(adId, commentId, comment);
    }

    @PostMapping("/{adId}/images")
    public ResponseEntity<Long> uploadImage(@PathVariable Long adId, @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            AvatarEntity avatarEntity = avatarService.uploadImage(imageFile);
            adService.addImageToAd(adId, avatarEntity);
            return ResponseEntity.ok(avatarEntity.getId());
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}/images")
    public List<AvatarEntity> getAdImages(@PathVariable Long id) {
        return avatarService.getImagesByAdId(id);
    }
}
