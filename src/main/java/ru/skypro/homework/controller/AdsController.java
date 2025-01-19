package ru.skypro.homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.ImageDTO;
import ru.skypro.homework.model.*;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.service.ImageService;

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
    private ImageService imageService;

    @Autowired
    private ImageRepository imageRepository;

    private long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserEntity user = (UserEntity) authentication.getPrincipal();
            return user.getId();
        }
        throw new RuntimeException("User not found");
    }

    @GetMapping
    public List<AdDTO> getAllAds() {
        return adService.findAllAds();
    }

    @PostMapping
    public AdDTO createNewAd(@RequestBody AdDTO ad) {
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
    public AdDTO updateAd(@PathVariable long id, @RequestBody AdDTO ad) throws IOException {
        return adService.updateInfoAd(id, ad);
    }

    @GetMapping("/me")
    public List<AdDTO> getMineAds() {
        long userId = getCurrentUserId();
        return adService.findAdsOfUser(userId);
    }

    @GetMapping("/{id}/comments")
    public List<CommentDTO> getComments(@PathVariable long id) {
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
        ImageEntity imageEntity = imageRepository.uploadImage(imageFile);
        adService.addImageToAd(adId, imageEntity);
        return ResponseEntity.ok(imageEntity.getId());
    }

    @GetMapping("/{id}/images")
    public List<ImageDTO> getAdImages(@PathVariable Long id) {
        return imageService.getAdImages(id);
    }
}
