package ru.skypro.homework.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ads")
@Api(tags = "Advertisement Management")
public class AdsController {
    @Autowired
    private AdService adService;

    @Autowired
    private CommentService commentService;

    private long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserEntity user = (UserEntity) authentication.getPrincipal();
            return user.getId();
        }
        throw new RuntimeException("User not found");
    }

    @GetMapping
    @ApiOperation(value = "Get all advertisements", response = AdEntity.class, responseContainer = "List")
    public List<AdDTO> getAllAds() {
        return adService.findAllAds();
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ApiOperation(value = "Create a new advertisement", response = Long.class)
    public AdDTO createNewAd(@ApiParam(value = "Advertisement data", required = true) @RequestBody AdDTO ad) {
        return adService.createAd(ad);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get advertisement by ID", response = AdDTO.class)
    public AdDTO getAdById(@ApiParam(value = "Advertisement ID", required = true) @PathVariable long id) {
        return adService.getAd(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (@userService.findAdsOfUser(authentication.principal.id).stream().anyMatch(ad > ad.id == #id))")
    @ApiOperation(value = "Delete advertisement by ID")
    public void deleteAd(@ApiParam(value = "Advertisement ID", required = true) @PathVariable long id) {
        adService.deleteAd(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (@userService.findAdsOfUser(authentication.principal.id).stream().anyMatch(ad > ad.id == #id))")
    @ApiOperation(value = "Update advertisement by ID", response = AdDTO.class)
    public AdDTO updateAd(@ApiParam(value = "Advertisement ID", required = true) @PathVariable long id,
                          @ApiParam(value = "Updated advertisement data", required = true) @RequestBody AdDTO ad) throws IOException {
        return adService.updateInfoAd(id, ad);
    }

    @GetMapping("/me")
    @ApiOperation(value = "Get current user's advertisements", response = AdEntity.class, responseContainer = "List")
    public List<AdDTO> getMineAds() {
        long userId = getCurrentUserId();
        return adService.findAdsOfUser(userId);
    }

    @GetMapping("/{id}/comments")
    @ApiOperation(value = "Get comments for an advertisement", response = CommentEntity.class, responseContainer = "List")
    public List<CommentDTO> getComments(@ApiParam(value = "Advertisement ID", required = true) @PathVariable long id) {
        return commentService.showCommentToAd(id);
    }

    @PostMapping("/{id}/comments")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ApiOperation(value = "Add a comment to an advertisement", response = Long.class)
    public long addComment(@ApiParam(value = "Advertisement ID", required = true) @PathVariable long id,
                           @ApiParam(value = "Comment data", required = true) @RequestBody CommentDTO comment) {
        return commentService.newComment(id, comment);
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or (@commentService.getCommentById(#adId, #commentId).user.id == authentication.principal.id)")
    @ApiOperation(value = "Delete a comment from an advertisement")
    public void deleteComment(@ApiParam(value = "Advertisement ID", required = true) @PathVariable long adId,
                              @ApiParam(value = "Comment ID", required = true) @PathVariable long commentId) {
        commentService.deleteComm(adId, commentId);
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or (@commentService.getCommentById(#adId, #commentId).user.id == authentication.principal.id)")
    @ApiOperation(value = "Update a comment on an advertisement")
    public void updateCommentInfo(@ApiParam(value = "Advertisement ID", required = true) @PathVariable long adId,
                                  @ApiParam(value = "Comment ID", required = true) @PathVariable long commentId,
                                  @ApiParam(value = "Updated comment data", required = true) @RequestBody CommentDTO comment) {
        commentService.updateCommentInfo(adId, commentId, comment);
    }
}
