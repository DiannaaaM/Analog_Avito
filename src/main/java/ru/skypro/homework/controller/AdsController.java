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
     * Извлекает все объявления.
     *
     * @return список всех объявлений в виде объектов {@link AdDTO}.
     */
    @GetMapping
    @ApiOperation(value = "Get all advertisements", response = AdEntity.class, responseContainer = "List")
    public List<AdDTO> getAllAds() {
        return adService.findAllAds();
    }

    /**
     * Создает новое объявление.
     *
     * @param ad - данные рекламного объявления, которые должны быть созданы, заключены в {@link AdDTO}.
     * @return созданное объявление в виде {@link AdDTO}.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "Create a new advertisement", response = Long.class)
    public AdDTO createNewAd(@ApiParam(value = "Advertisement data", required = true) @RequestBody AdDTO ad) {
        return adService.createAd(ad);
    }

    /**
     * Извлекает объявление по его идентификатору.
     *
     * @param id - идентификатор извлекаемого объявления.
     * @return объявление в виде {@link AdDTO}.
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Get advertisement by ID", response = AdDTO.class)
    public AdDTO getAdById(@ApiParam(value = "Advertisement ID", required = true) @PathVariable long id) {
        return adService.getAd(id);
    }

    /**
     * Удаляет объявление по ее идентификатору.
     *
     * @param id - идентификатор объявления, которую нужно удалить.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.isAdOwner(authentication.principal.id, #id)")
    @ApiOperation(value = "Delete advertisement by ID")
    public void deleteAd(@ApiParam(value = "Advertisement ID", required = true) @PathVariable long id) {
        adService.deleteAd(id);
    }

    /**
     * Обновляет объявление по его идентификатору.
     *
     * @param id - идентификатор обновляемого объявления.
     * @param ad - обновленные данные, заключенные в {@link AdDTO}.
     * @return обновленное объявление в виде {@link AdDTO}.
     * @throws IOException, если в процессе обновления возникает ошибка ввода-вывода.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.isAdOwner(authentication.principal.id, #id)")
    @ApiOperation(value = "Update advertisement by ID", response = AdDTO.class)
    public AdDTO updateAd(@ApiParam(value = "Advertisement ID", required = true) @PathVariable long id,
                          @ApiParam(value = "Updated advertisement data", required = true) @RequestBody AdDTO ad) throws IOException {
        return adService.updateInfoAd(id, ad);
    }

    /**
     * Получает объявления текущего пользователя.
     *
     * @return список объявлений текущего пользователя в виде объектов {@link AdDTO}.
     */
    @GetMapping("/me")
    @ApiOperation(value = "Get current user's advertisements", response = AdEntity.class, responseContainer = "List")
    public List<AdDTO> getMineAds() {
        List<AdDTO> userAds = userService.getUserAds();
        return ResponseEntity.ok(userAds).getBody();
    }

    /**
     * Извлекает комментарии к конкретному объявлению.
     *
     * @param id - идентификатор объявления, по которому нужно получить комментарии.
     * @return список комментариев в виде объектов {@link CommentDTO}.
     */
    @GetMapping("/{id}/comments")
    @ApiOperation(value = "Get comments for an advertisement", response = CommentEntity.class, responseContainer = "List")
    public List<CommentDTO> getComments(@ApiParam(value = "Advertisement ID", required = true) @PathVariable long id) {
        return commentService.showCommentToAd(id);
    }

    /**
     * Добавляет комментарий к конкретному объявлению.
     *
     * @param id идентификатор объявления, к которому будет добавлен комментарий.
     * @param comment данные комментария, заключенные в {@link CommentDTO}.
     * @return идентификатор вновь созданного комментария.
     */
    @PostMapping("/{id}/comments")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ApiOperation(value = "Add a comment to an advertisement", response = Long.class)
    public long addComment(@ApiParam(value = "Advertisement ID", required = true) @PathVariable long id,
                           @ApiParam(value = "Comment data", required = true) @RequestBody CommentDTO comment) {
        return commentService.newComment(id, comment);
    }

    /**
     * Удаляет комментарий к определенному объявлению.
     *
     * @param - идентификатор объявления, из которой будет удален комментарий.
     * @param - идентификатор комментария, который нужно удалить.
     */
    @DeleteMapping("/{adId}/comments/{commentId}")
    @PreAuthorize("hasRole('ADMIN') or @commentService.isCommentOwner(authentication.principal.id, #commentId)")
    @ApiOperation(value = "Delete a comment from an advertisement")
    public void deleteComment(@ApiParam(value = "Advertisement ID", required = true) @PathVariable long adId,
                              @ApiParam(value = "Comment ID", required = true) @PathVariable long commentId) {
        commentService.deleteComm(adId, commentId);
    }

    /**
     * Обновляет комментарий к конкретному объявлению.
     *
     * @param adId идентификатор объявления, к которому относится комментарий.
     * @param commentId идентификатор комментария для обновления.
     * @param comment обновленные данные комментария, заключенные в {@link CommentDTO}.
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
