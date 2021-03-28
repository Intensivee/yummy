package com.orange.mainservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class CommentResponse {

    private Long commentId;
    private String body;
    private Date dateCreated;
    private String username;
    private String userImg;
}
