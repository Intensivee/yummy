package com.orange.mainservice.service;

import com.orange.mainservice.entity.Comment;
import com.orange.mainservice.exception.ResourceCreateException;
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

    public Comment getById(Long id){
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
    }

    public CommentResponse add(CommentRequest request) {
        if(request.getCommentId() != null){
            throw new ResourceCreateException(request.getRecipeId());
        }
        Comment comment = createNewEntityFromRequest(request);
        return commentMapper.commentToResponse(commentRepository.save(comment));
    }

    public CommentResponse edit(Long id, CommentRequest request) {
        validateEditInput(id, request);

        Comment comment = createEditedEntityFromRequest(request);
        return commentMapper.commentToResponse(commentRepository.save(comment));
    }

    private Comment createNewEntityFromRequest(CommentRequest request) {
        return new Comment(
                request.getCommentId(),
                request.getBody(),
                null,
                userService.getById(request.getUserId()),
                recipeService.getById(request.getRecipeId())
        );
    }

    private Comment createEditedEntityFromRequest(CommentRequest request){
        Comment comment = getById(request.getCommentId());
        return new Comment(
                request.getCommentId(),
                request.getBody(),
                comment.getDateCreated(),
                userService.getById(request.getUserId()),
                recipeService.getById(request.getRecipeId())
        );
    }

    private void validateEditInput(Long id, CommentRequest request){
        if(idNotPresentORNotMatching(id, request)){
            throw new ResourceCreateException(id); // TODO : custom exception
        }
        if(!commentRepository.existsById(id)){
            throw new ResourceNotFoundException("Comment", "id", id);
        }
    }

    private boolean idNotPresentORNotMatching(Long pathId, CommentRequest request){
        return request.getCommentId() == null || !request.getCommentId().equals(pathId);
    }
}
