package com.orange.mainservice.service;

import com.orange.mainservice.entity.Component;
import com.orange.mainservice.exception.ResourceCreateException;
import com.orange.mainservice.exception.ResourceNotFoundException;
import com.orange.mainservice.mapper.response.ComponentResponseMapper;
import com.orange.mainservice.repository.ComponentRepository;
import com.orange.mainservice.request.ComponentRequest;
import com.orange.mainservice.response.ComponentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ComponentService {

    private final ComponentRepository componentRepository;
    private final ComponentResponseMapper responseMapper;
    private final ComponentCategoryService categoryService;

    public ComponentResponse getResponseById(Long id){
        return responseMapper.componentToResponse(getById(id));
    }

    public Component getById(Long id) {
        return this.componentRepository.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Component", "id", id));
    }

    public ComponentResponse add(ComponentRequest request){
        if(request.getComponentId() != null){
            throw new ResourceCreateException(request.getComponentId());
        }
        Component component = createEntityFromRequest(request);
        return responseMapper.componentToResponse(componentRepository.save(component));
    }

    private Component createEntityFromRequest(ComponentRequest request) {
        return new Component(
                request.getComponentId(),
                request.getName(),
                request.getIsAccepted(),
                null,
                request.getCategoriesIds() != null ? request.getCategoriesIds()
                        .stream()
                        .map(categoryService::getById)
                        .collect(Collectors.toSet()) : null
        );
    }
}
