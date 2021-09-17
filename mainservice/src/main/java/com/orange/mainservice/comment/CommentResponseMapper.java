package com.orange.mainservice.comment;

import org.springframework.stereotype.Component;

@Component
class CommentResponseMapper {

    CommentResponse commentToResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getBody(),
                comment.getDateCreated(),
                comment.getUser().getUsername(),
                comment.getUser().getImgUrl());
    }
}
