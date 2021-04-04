package com.orange.mainservice.service;

import com.orange.mainservice.entity.Direction;
import com.orange.mainservice.exception.PathNotMatchBodyException;
import com.orange.mainservice.exception.ResourceCreateException;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.mapper.response.DirectionResponseMapper;
import com.orange.mainservice.repository.DirectionRepository;
import com.orange.mainservice.request.DirectionRequest;
import com.orange.mainservice.response.DirectionResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DirectionService {

    private final DirectionRepository directionRepository;
    private final DirectionResponseMapper responseMapper;
    private final RecipeService recipeService;

    public DirectionResponse getResponseById(Long id){
        return responseMapper.directionToDto(getById(id));
    }

    public Direction getById(Long id){
        return directionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Direction", "id", id));
    }

    public DirectionResponse add(DirectionRequest request){
        validateCreateRequest(request);
        Direction directionToAdd = createEntityFromRequest(request);
        Direction addedDirection = directionRepository.save(directionToAdd);
        return responseMapper.directionToDto(addedDirection);
    }

    public DirectionResponse edit(Long id, DirectionRequest request){
        validateEditInput(id, request);
        Direction directionToEdit = createEntityFromRequest(request);
        Direction editedDirection = directionRepository.save(directionToEdit);
        return responseMapper.directionToDto(editedDirection);
    }

    public void delete(Long id) {
        directionRepository.delete(getById(id));
    }

    private void validateEditInput(Long id, DirectionRequest request){
        if(idNotPresentORNotMatching(id, request)){
            throw new PathNotMatchBodyException(id, request.getDirectionId());
        }
        if(!directionRepository.existsById(id)){
            throw new ResourceNotFoundException("ComponentCategory", "id", id);
        }
    }

    private boolean idNotPresentORNotMatching(Long pathId, DirectionRequest request){
        return !isIdInRequest(request) || !request.getDirectionId().equals(pathId);
    }

    private void validateCreateRequest(DirectionRequest request){
        if(isIdInRequest(request)){
            throw new ResourceCreateException(request.getDirectionId());
        }
    }

    private boolean isIdInRequest(DirectionRequest request){
        return request.getDirectionId() != null;
    }

    private Direction createEntityFromRequest(DirectionRequest request) {
        return new Direction(
                request.getDirectionId(),
                request.getOrder(),
                request.getDescription(),
                recipeService.getById(request.getRecipeId())
        );
    }
}
