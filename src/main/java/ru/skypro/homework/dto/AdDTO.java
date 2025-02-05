package ru.skypro.homework.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

@ApiModel(description = "Data transfer object for advertisements")
public class AdDTO {

    @ApiModelProperty(value = "Unique identifier for the advertisement", required = true)
    private Long id;

    @ApiModelProperty(value = "Title of the advertisement", required = true)
    private String title;

    @ApiModelProperty(value = "Description of the advertisement", required = true)
    private String description;

    @ApiModelProperty(value = "Photo of the advertisement")
    private MultipartFile image;

    @ApiModelProperty(value = "Price of the advertisement", required = true)
    private Integer price;

    @ApiModelProperty(value = "Owner ID of the advertisement", required = true)
    private Long ownerId;

    @ApiModelProperty(value = "Show comments of ad", required = true)
    private String comments;

    public AdDTO() {
    }

    public AdDTO(Long id, String title, String description, MultipartFile image, Integer price, Long ownerId, String comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.price = price;
        this.ownerId = ownerId;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
