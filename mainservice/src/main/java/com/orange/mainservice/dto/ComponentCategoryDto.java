package com.orange.mainservice.dto;

import java.util.Set;

public class ComponentCategoryDto {

    private Long id;
    private String name;
    private Set<ComponentDto> components;

    public ComponentCategoryDto() {
    }

    public ComponentCategoryDto(Long id, String name, Set<ComponentDto> components) {
        this.id = id;
        this.name = name;
        this.components = components;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ComponentDto> getComponents() {
        return components;
    }

    public void setComponents(Set<ComponentDto> components) {
        this.components = components;
    }
}
