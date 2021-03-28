package com.orange.mainservice.mapper.response;

import com.orange.mainservice.entity.Comment;
import com.orange.mainservice.response.CommentResponse;
import org.springframework.stereotype.Component;

@Component
public class CommentResponseMapper {

    public CommentResponse commentToResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getBody(),
                comment.getDateCreated(),
                comment.getUser().getUsername(),
                comment.getUser().getImgUrl());
    }
}
