package com.orange.mainservice.service;

import com.orange.mainservice.entity.Component;
import com.orange.mainservice.repository.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComponentService {

    private final ComponentRepository componentRepository;

    @Autowired
    public ComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    public Component getById(Long id) {
        return this.componentRepository.getById(id)
                .orElseThrow(RuntimeException::new);
    }
}
