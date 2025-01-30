package ru.skypro.homework.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.service.impl.AdServiceImpl;
import ru.skypro.homework.service.impl.CommentServiceImpl;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ads")
@Api(tags = "Advertisement Management")
public class AdsController {
    private final AdServiceImpl adService;

    private final CommentServiceImpl commentService;

    private final UserServiceImpl userService;

    public AdsController(AdServiceImpl adService, CommentServiceImpl commentService, UserServiceImpl userService) {
        this.adService = adService;
        this.commentService = commentService;
        this.userService = userService;
    }

    /**
     * Retrieves all advertisements.
     *
     * @return a list of all advertisements as {@link AdDTO} objects.
     */
    @GetMapping
    @ApiOperation(value = "Get all advertisements", response = AdEntity.class, responseContainer = "List")
    public List<AdDTO> getAllAds() {
        return adService.findAllAds();
    }

    /**
     * Creates a new advertisement.
     *
     * @param ad the advertisement data to be created, encapsulated in an {@link AdDTO}.
     * @return the created advertisement as an {@link AdDTO}.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "Create a new advertisement", response = Long.class)
    public AdDTO createNewAd(@ApiParam(value = "Advertisement data", required = true) @RequestBody AdDTO ad) {
        return adService.createAd(ad);
    }

    /**
     * Retrieves an advertisement by its ID.
     *
     * @param id the ID of the advertisement to retrieve.
     * @return the advertisement as an {@link AdDTO}.
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Get advertisement by ID", response = AdDTO.class)
    public AdDTO getAdById(@ApiParam(value = "Advertisement ID", required = true) @PathVariable long id) {
        return adService.getAd(id);
    }

    /**
     * Deletes an advertisement by its ID.
     *
     * @param id the ID of the advertisement to delete.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.isAdOwner(authentication.principal.id, #id)")
    @ApiOperation(value = "Delete advertisement by ID")
    public void deleteAd(@ApiParam(value = "Advertisement ID", required = true) @PathVariable long id) {
        adService.deleteAd(id);
    }

    /**
     * Updates an advertisement by its ID.
     *
     * @param id the ID of the advertisement to update.
     * @param ad the updated advertisement data, encapsulated in an {@link AdDTO}.
     * @return the updated advertisement as an {@link AdDTO}.
     * @throws IOException if an I/O error occurs during the update process.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.isAdOwner(authentication.principal.id, #id)")
    @ApiOperation(value = "Update advertisement by ID", response = AdDTO.class)
    public AdDTO updateAd(@ApiParam(value = "Advertisement ID", required = true) @PathVariable long id,
                          @ApiParam(value = "Updated advertisement data", required = true) @RequestBody AdDTO ad) throws IOException {
        return adService.updateInfoAd(id, ad);
    }

    /**
     * Retrieves the current user's advertisements.
     *
     * @return a list of the current user's advertisements as {@link AdDTO} objects.
     */
    @GetMapping("/me")
    @ApiOperation(value = "Get current user's advertisements", response = AdEntity.class, responseContainer = "List")
    public List<AdDTO> getMineAds() {
        List<AdDTO> userAds = userService.getUserAds();
        return ResponseEntity.ok(userAds).getBody();
    }

    /**
     * Retrieves comments for a specific advertisement.
     *
     * @param id the ID of the advertisement for which to retrieve comments.
     * @return a list of comments as {@link CommentDTO} objects.
     */
    @GetMapping("/{id}/comments")
    @ApiOperation(value = "Get comments for an advertisement", response = CommentEntity.class, responseContainer = "List")
    public List<CommentDTO> getComments(@ApiParam(value = "Advertisement ID", required = true) @PathVariable long id) {
        return commentService.showCommentToAd(id);
    }

    /**
     * Adds a comment to a specific advertisement.
     *
     * @param id the ID of the advertisement to which the comment will be added.
     * @param comment the comment data, encapsulated in a {@link CommentDTO}.
     * @return the ID of the newly created comment.
     */
    @PostMapping("/{id}/comments")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ApiOperation(value = "Add a comment to an advertisement", response = Long.class)
    public long addComment(@ApiParam(value = "Advertisement ID", required = true) @PathVariable long id,
                           @ApiParam(value = "Comment data", required = true) @RequestBody CommentDTO comment) {
        return commentService.newComment(id, comment);
    }

    /**
     * Deletes a comment from a specific advertisement.
     *
     * @param adId the ID of the advertisement from which the comment will be deleted.
     * @param commentId the ID of the comment to delete.
     */
    @DeleteMapping("/{adId}/comments/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or @commentService.isCommentOwner(authentication.principal.id, #commentId)")
    @ApiOperation(value = "Delete a comment from an advertisement")
    public void deleteComment(@ApiParam(value = "Advertisement ID", required = true) @PathVariable long adId,
                              @ApiParam(value = "Comment ID", required = true) @PathVariable long commentId) {
        commentService.deleteComm(adId, commentId);
    }

    /**
     * Updates a comment on a specific advertisement.
     *
     * @param adId the ID of the advertisement on which the comment is located.
     * @param commentId the ID of the comment to update.
     * @param comment the updated comment data, encapsulated in a {@link CommentDTO}.
     */
    @PatchMapping("/{adId}/comments/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or @commentService.isCommentOwner(authentication.principal.id, #commentId)")
    @ApiOperation(value = "Update a comment on an advertisement")
    public void updateCommentInfo(@ApiParam(value = "Advertisement ID", required = true) @PathVariable long adId,
                                  @ApiParam(value = "Comment ID", required = true) @PathVariable long commentId,
                                  @ApiParam(value = "Updated comment data", required = true) @RequestBody CommentDTO comment) {
        commentService.updateCommentInfo(adId, commentId, comment);
    }
}
