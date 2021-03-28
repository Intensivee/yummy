package com.orange.mainservice.mapper.response;

import com.orange.mainservice.entity.Comment;
import com.orange.mainservice.response.CommentResponse;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
public class CommentResponseMapper {

    @Valid
    public CommentResponse commentToResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getBody(),
                comment.getDateCreated(),
                comment.getUser().getUsername(),
                comment.getUser().getImgUrl());
    }
}
