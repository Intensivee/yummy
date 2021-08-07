package com.orange.mainservice.direction;

import com.orange.mainservice.exception.PathNotMatchBodyException;
import com.orange.mainservice.exception.ResourceCreateException;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.recipe.RecipeFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class DirectionService {

    private final DirectionRepository directionRepository;
    private final DirectionResponseMapper responseMapper;
    private final RecipeFacade recipeFacade;

    DirectionResponse getResponseById(Long id) {
        return responseMapper.directionToDto(getById(id));
    }

    DirectionResponse add(DirectionRequest request) {
        validateCreateRequest(request);
        Direction directionToAdd = createEntityFromRequest(request);
        Direction addedDirection = directionRepository.save(directionToAdd);
        return responseMapper.directionToDto(addedDirection);
    }

    DirectionResponse edit(Long id, DirectionRequest request) {
        validateEditInput(id, request);
        Direction directionToEdit = createEntityFromRequest(request);
        Direction editedDirection = directionRepository.save(directionToEdit);
        return responseMapper.directionToDto(editedDirection);
    }

    void delete(Long id) {
        directionRepository.delete(getById(id));
    }

    Set<DirectionResponse> getByRecipeId(Long id) {
        return directionRepository.findByRecipeIdOrdered(id).stream()
                .map(responseMapper::directionToDto)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Direction getById(Long id) {
        return directionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Direction", "id", id));
    }

    private void validateEditInput(Long id, DirectionRequest request) {
        if (isIdNotPresentOrNotMatching(id, request)) {
            throw new PathNotMatchBodyException(id, request.getId());
        }
        if (!directionRepository.existsById(id)) {
            throw new ResourceNotFoundException("ComponentCategory", "id", id);
        }
    }

    private boolean isIdNotPresentOrNotMatching(Long pathId, DirectionRequest request) {
        return !isIdInRequest(request) || !request.getId().equals(pathId);
    }

    private void validateCreateRequest(DirectionRequest request){
        if(isIdInRequest(request)){
            throw new ResourceCreateException(request.getId());
        }
    }

    private boolean isIdInRequest(DirectionRequest request){
        return request.getId() != null;
    }

    private Direction createEntityFromRequest(DirectionRequest request) {
        return new Direction(
                request.getId(),
                request.getOrder(),
                request.getDescription(),
                recipeFacade.getById(request.getRecipeId())
        );
    }
}
