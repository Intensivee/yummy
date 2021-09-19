package com.orange.mainservice.component;

import com.orange.mainservice.componentcategory.ComponentCategoryFacade;
import com.orange.mainservice.exception.PathNotMatchBodyException;
import com.orange.mainservice.exception.ResourceCreateException;
import com.orange.mainservice.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ComponentService {

    private final ComponentRepository componentRepository;
    private final ComponentResponseMapper responseMapper;
    private final ComponentCategoryFacade categoryFacade;

    Component getById(Long id) {
        return this.componentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Component", "id", id));
    }

    Set<ComponentResponse> getByCategoryId(Long id) {
        return componentRepository.findAllByCategories_Id(id).stream()
                .map(responseMapper::componentToResponse)
                .collect(Collectors.toSet());
    }

    ComponentResponse getResponseById(Long id) {
        return responseMapper.componentToResponse(getById(id));
    }

    ComponentResponse createComponent(ComponentRequest request) {
        validateCreateRequest(request);
        var createdComponent = componentRepository.save(createEntityFromRequest(request));
        return responseMapper.componentToResponse(createdComponent);
    }

    ComponentResponse editComponent(Long id, ComponentRequest request) {
        validateEditInput(id, request);
        var editedComponent = componentRepository.save(createEntityFromRequest(request));
        return responseMapper.componentToResponse(editedComponent);
    }

    void deleteComponent(Long id) {
        componentRepository.delete(getById(id));
    }


    Set<String> getAllNamesOrdered() {
        return this.componentRepository.findAllNamesOrdered();
    }

    Component getOrCreateComponentByName(String componentName) {
        return componentRepository.findByName(componentName)
                .orElseGet(() -> componentRepository.save(new Component(componentName, false)));
    }

    private void validateEditInput(Long id, ComponentRequest request) {
        if (isIdNotPresentOrNotMatching(id, request)) {
            throw new PathNotMatchBodyException(id, request.getId());
        }
        if (!componentRepository.existsById(id)) {
            throw new ResourceNotFoundException("ComponentCategory", "id", id);
        }
    }

    private boolean isIdNotPresentOrNotMatching(Long pathId, ComponentRequest request) {
        return !isIdInRequest(request) || !request.getId().equals(pathId);
    }

    private void validateCreateRequest(ComponentRequest request) {
        if (isIdInRequest(request)) {
            throw new ResourceCreateException(request.getId());
        }
    }

    private boolean isIdInRequest(ComponentRequest request) {
        return Objects.nonNull(request.getId());
    }

    private Component createEntityFromRequest(ComponentRequest request) {
        return new Component(
                request.getId(),
                request.getName(),
                request.getIsAccepted(),
                Objects.nonNull(request.getCategoriesIds())
                        ? request.getCategoriesIds().stream()
                        .map(categoryFacade::getById)
                        .collect(Collectors.toSet())
                        : null
        );
    }
}
