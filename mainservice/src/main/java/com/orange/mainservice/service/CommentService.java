package com.orange.mainservice.service;

import com.orange.mainservice.entity.Comment;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.mapper.response.CommentResponseMapper;
import com.orange.mainservice.repository.CommentRepository;
import com.orange.mainservice.request.CommentRequest;
import com.orange.mainservice.response.CommentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentResponseMapper commentMapper;
    private final UserService userService;
    private final RecipeService recipeService;

    public CommentResponse getResponseById(Long id){
        return commentMapper.commentToResponse(getById(id));
    }

    public CommentResponse add(CommentRequest commentRequest) {
        Comment comment = createCommentFromRequest(commentRequest);
        return commentMapper.commentToResponse(commentRepository.save(comment));
    }

    private Comment getById(Long id){
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
    }

    private Comment createCommentFromRequest(CommentRequest request) {
        return new Comment(
                request.getCommentId(),
                request.getBody(),
                null,
                userService.getById(request.getUserId()),
                recipeService.getById(request.getRecipeId())
        );
    }
}
