package com.orange.mainservice.component;

import com.orange.mainservice.componentcategory.ComponentCategory;
import com.orange.mainservice.componentcategory.ComponentCategoryFacade;
import com.orange.mainservice.exception.PathNotMatchBodyException;
import com.orange.mainservice.exception.ResourceCreateException;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.exception.ResourceUniqueConstraintException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    List<ComponentResponse> findAllByIsAcceptedAndIsReviewed(boolean isAccepted, boolean isReviewed) {
        return componentRepository.findAllByIsAcceptedAndIsReviewedOrderByNameAsc(isAccepted, isReviewed).stream()
                .map(responseMapper::componentToResponse)
                .collect(Collectors.toList());
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
        validateEditRequest(id, request);
        var editedComponent = componentRepository.save(createEntityFromRequest(request));
        return responseMapper.componentToResponse(editedComponent);
    }

    ComponentResponse patchComponent(Long id, ComponentRequest request) {
        validateEditRequest(id, request);
        Component component = getById(id);
        component.setIsAccepted(Objects.requireNonNullElse(request.getIsAccepted(), component.getIsAccepted()));
        component.setIsReviewed(Objects.requireNonNullElse(request.getIsReviewed(), component.getIsReviewed()));
        Set<ComponentCategory> categories = Objects.nonNull(request.getCategoriesIds())
                ? request.getCategoriesIds().stream().map(categoryFacade::getById).collect(Collectors.toSet())
                : component.getCategories();
        component.setCategories(categories);
        if (isComponentNameValidForUpdate(request.getName(), component.getName())) {
            component.setName(request.getName());
        }
        return responseMapper.componentToResponse(componentRepository.save(component));
    }

    void deleteComponent(Long id) {
        componentRepository.delete(getById(id));
    }

    List<String> getAllNamesOrdered() {
        return this.componentRepository.findAllAcceptedNamesOrdered();
    }

    Component getOrCreateComponentByName(String componentName) {
        return componentRepository.findByNameIgnoreCase(componentName)
                .orElseGet(() -> componentRepository.save(new Component(componentName, false)));
    }

    boolean isNotAcceptedAndReferencedInJustOneIngredient(Component component) {
        return !component.getIsAccepted() && component.getIngredients().size() == 1;
    }

    private boolean isComponentNameValidForUpdate(String newName, String oldName) {
        if (Objects.nonNull(newName) && !newName.equalsIgnoreCase(oldName)) {
            componentRepository.findByNameIgnoreCase(newName).ifPresent(existingComponent -> {
                throw new ResourceUniqueConstraintException("Component", "name", existingComponent.getName());
            });
            return true;
        }
        return false;
    }

    private void validateEditRequest(Long id, ComponentRequest request) {
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
