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

    public DirectionResponse add(DirectionRequest request){
        if(request.getDirectionId() != null){
            throw new ResourceCreateException(request.getDirectionId());
        }
        Direction direction = createEntityFromRequest(request);
        return responseMapper.directionToDto(directionRepository.save(direction));
    }

    public DirectionResponse edit(Long id, DirectionRequest request){
        validateEditInput(id, request);
        Direction direction = createEntityFromRequest(request);
        return responseMapper.directionToDto(directionRepository.save(direction));
    }

    private Direction getById(Long id){
        return directionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Direction", "id", id));
    }

    private Direction createEntityFromRequest(DirectionRequest request) {
        return new Direction(
                request.getDirectionId(),
                request.getOrder(),
                request.getDescription(),
                recipeService.getById(request.getRecipeId())
        );
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
        return request.getDirectionId() == null || !request.getDirectionId().equals(pathId);
    }
}
