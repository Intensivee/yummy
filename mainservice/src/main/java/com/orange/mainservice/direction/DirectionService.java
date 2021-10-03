package com.orange.mainservice.direction;

import com.orange.mainservice.exception.PathNotMatchBodyException;
import com.orange.mainservice.exception.ResourceCreateException;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.recipe.Recipe;
import com.orange.mainservice.recipe.RecipeFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
class DirectionService {

    private final DirectionRepository directionRepository;
    private final DirectionResponseMapper responseMapper;
    private final RecipeFacade recipeFacade;

    @Transactional(rollbackFor = Exception.class)
    public void replaceDirections(List<String> descriptions, Recipe recipe) {
        directionRepository.deleteAllByRecipe_Id(recipe.getId());
        directionRepository.flush();
        createDirections(descriptions, recipe);
    }

    void createDirections(List<String> descriptions, Recipe recipe) {
        var directions = IntStream.range(0, descriptions.size())
                .mapToObj(i -> new Direction(i + 1, descriptions.get(i), recipe))
                .collect(Collectors.toList());
        this.directionRepository.saveAll(directions);
    }

    DirectionResponse getResponseById(Long id) {
        return responseMapper.directionToDto(getById(id));
    }

    DirectionResponse createDirection(DirectionRequest request) {
        validateCreateRequest(request);
        var createdDirection = directionRepository.save(createEntityFromRequest(request));
        return responseMapper.directionToDto(createdDirection);
    }

    DirectionResponse editDirection(Long id, DirectionRequest request) {
        validateEditInput(id, request);
        var editedDirection = directionRepository.save(createEntityFromRequest(request));
        return responseMapper.directionToDto(editedDirection);
    }

    void deleteDirection(Long id) {
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

    private void validateCreateRequest(DirectionRequest request) {
        if (isIdInRequest(request)) {
            throw new ResourceCreateException(request.getId());
        }
    }

    private boolean isIdInRequest(DirectionRequest request) {
        return Objects.nonNull(request.getId());
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
