package ru.skypro.homework.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Data transfer object for comments on advertisements")
public class CommentDTO {

    @ApiModelProperty(value = "Unique identifier for the comment", required = true)
    private Long id;

    @ApiModelProperty(value = "Comment text", required = true)
    private String text;

    @ApiModelProperty(value = "Timestamp of when the comment was created", required = true)
    private Long createdAt;

    @ApiModelProperty(value = "Author of the comment")
    private UserDTO author;

    @ApiModelProperty(value = "Advertisement associated with this comment")
    private Long adId;

    public void setId(Long id) {
        this.id = id;
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

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }
}
