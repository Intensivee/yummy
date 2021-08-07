package com.orange.mainservice.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComponentFacade {

    private final ComponentService componentService;

    public Component getById(Long id) {
        return componentService.getById(id);
    }
}
