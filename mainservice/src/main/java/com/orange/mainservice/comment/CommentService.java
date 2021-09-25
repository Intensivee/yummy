package com.orange.mainservice.comment;

import com.orange.mainservice.exception.PathNotMatchBodyException;
import com.orange.mainservice.exception.ResourceCreateException;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.recipe.RecipeFacade;
import com.orange.mainservice.security.AuthenticationFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class CommentService {

    private final CommentRepository commentRepository;
    private final CommentResponseMapper commentMapper;
    private final AuthenticationFacade authenticationFacade;
    private final RecipeFacade recipeFacade;

    CommentResponse getResponseById(Long id) {
        return commentMapper.commentToResponse(getById(id));
    }

    CommentResponse createComment(CommentRequest request) {
        validateCreateRequest(request);
        var createdComment = commentRepository.save(createNewEntityFromRequest(request));
        return commentMapper.commentToResponse(createdComment);
    }

    CommentResponse editComment(Long id, CommentRequest request) {
        validateEditRequest(id, request);
        var editedComment = commentRepository.save(createEditedEntityFromRequest(request));
        return commentMapper.commentToResponse(editedComment);
    }

    void deleteComponent(Long id) {
        commentRepository.delete(getById(id));
    }

    List<CommentResponse> getByRecipeId(Long id) {
        return commentRepository.findByRecipeIdOrdered(id).stream()
                .map(commentMapper::commentToResponse)
                .collect(Collectors.toList());
    }

    private Comment getById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
    }

    private void validateEditRequest(Long id, CommentRequest request) {
        if (isIdNotPresentOrNotMatching(id, request)) {
            throw new PathNotMatchBodyException(id, request.getId());
        }
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comment", "id", id);
        }
    }

    private boolean isIdNotPresentOrNotMatching(Long pathId, CommentRequest request) {
        return !isIdInRequest(request) || !request.getId().equals(pathId);
    }

    private void validateCreateRequest(CommentRequest request){
        if(isIdInRequest(request)){
            throw new ResourceCreateException(request.getId());
        }
    }

    private boolean isIdInRequest(CommentRequest request){
        return Objects.nonNull(request.getId());
    }

    private Comment createNewEntityFromRequest(CommentRequest request) {
        return new Comment(
                request.getBody(),
                authenticationFacade.getCurrentUser(),
                recipeFacade.getById(request.getRecipeId())
        );
    }

    private Comment createEditedEntityFromRequest(CommentRequest request){
        Comment comment = getById(request.getId());
        return new Comment(
                request.getId(),
                request.getBody(),
                comment.getDateCreated(),
                authenticationFacade.getCurrentUser(),
                recipeFacade.getById(request.getRecipeId())
        );
    }
}
