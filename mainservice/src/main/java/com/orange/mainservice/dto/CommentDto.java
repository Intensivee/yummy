package com.orange.mainservice.dto;

import java.util.Date;

public class CommentDto {

    private Long id;
    private String body;
    private Date dateCreated;
    private String username;
    private String imgUrl;

    public CommentDto() {
    }

    public CommentDto(Long id, String body, Date dateCreated, String username, String imgUrl) {
        this.id = id;
        this.body = body;
        this.dateCreated = dateCreated;
        this.username = username;
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
