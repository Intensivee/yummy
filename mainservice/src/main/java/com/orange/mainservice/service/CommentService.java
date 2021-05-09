package com.orange.mainservice.service;

import com.orange.mainservice.entity.Comment;
import com.orange.mainservice.exception.PathNotMatchBodyException;
import com.orange.mainservice.exception.ResourceCreateException;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.mapper.response.CommentResponseMapper;
import com.orange.mainservice.repository.CommentRepository;
import com.orange.mainservice.request.CommentRequest;
import com.orange.mainservice.response.CommentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
        validateCreateRequest(request);
        Comment commentToAdd = createNewEntityFromRequest(request);
        Comment addedComment = commentRepository.save(commentToAdd);
        return commentMapper.commentToResponse(addedComment);
    }

    public CommentResponse edit(Long id, CommentRequest request) {
        validateEditRequest(id, request);
        Comment commentToEdit= createEditedEntityFromRequest(request);
        Comment editedComment = commentRepository.save(commentToEdit);
        return commentMapper.commentToResponse(editedComment);
    }

    public void delete(Long id) {
        commentRepository.delete(getById(id));
    }

    public Set<CommentResponse> getByRecipeId(Long id){
        return commentRepository.findByRecipeIdOrdered(id).stream()
                .map(commentMapper::commentToResponse)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private void validateEditRequest(Long id, CommentRequest request){
        if(isIdNotPresentORNotMatching(id, request)){
            throw new PathNotMatchBodyException(id, request.getId());
        }
        if(!commentRepository.existsById(id)){
            throw new ResourceNotFoundException("Comment", "id", id);
        }
    }

    private boolean isIdNotPresentORNotMatching(Long pathId, CommentRequest request){
        return !isIdInRequest(request) || !request.getId().equals(pathId);
    }

    private void validateCreateRequest(CommentRequest request){
        if(isIdInRequest(request)){
            throw new ResourceCreateException(request.getId());
        }
    }

    private boolean isIdInRequest(CommentRequest request){
        return request.getId() != null;
    }

    private Comment createNewEntityFromRequest(CommentRequest request) {
        return new Comment(
                request.getId(),
                request.getBody(),
                null,
                userService.getById(request.getUserId()),
                recipeService.getById(request.getRecipeId())
        );
    }

    private Comment createEditedEntityFromRequest(CommentRequest request){
        Comment comment = getById(request.getId());
        return new Comment(
                request.getId(),
                request.getBody(),
                comment.getDateCreated(),
                userService.getById(request.getUserId()),
                recipeService.getById(request.getRecipeId())
        );
    }
}
