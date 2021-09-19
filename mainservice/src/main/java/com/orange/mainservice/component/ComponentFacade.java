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

    public Component getOrCreateComponentByName(String componentName) {
        return componentService.getOrCreateComponentByName(componentName);
    }

    public void deleteById(Long componentId) {
        componentService.deleteComponent(componentId);
    }

    public boolean isNotAcceptedAndReferencedInJustOneIngredient(Component component) {
        return this.componentService.isNotAcceptedAndReferencedInJustOneIngredient(component);
    }
}
