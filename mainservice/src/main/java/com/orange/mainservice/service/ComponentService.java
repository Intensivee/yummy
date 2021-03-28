package com.orange.mainservice.service;

import com.orange.mainservice.entity.Component;
import com.orange.mainservice.mapper.response.ComponentResponseMapper;
import com.orange.mainservice.repository.ComponentRepository;
import com.orange.mainservice.response.ComponentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ComponentService {

    private final ComponentRepository componentRepository;
    private final ComponentResponseMapper responseMapper;

    public ComponentResponse getResponseById(Long id){
        return responseMapper.componentToResponse(getById(id));
    }

    private Component getById(Long id) {
        return this.componentRepository.getById(id)
                .orElseThrow(RuntimeException::new);
    }
}
