package com.orange.mainservice.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "ComponentCategory")
@Table(
        name = "component_categories",
        uniqueConstraints = {
                @UniqueConstraint(name = "component_category_name_unique", columnNames = "name")
        }
)
public class ComponentCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(25)")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "component_categories_component",
            joinColumns = @JoinColumn(name = "component_category_id"),
            inverseJoinColumns = @JoinColumn(name = "component_id")
    )
    private Set<Component> components;

    public ComponentCategory() {
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

    public Set<Component> getComponents() {
        return components;
    }

    public void setComponents(Set<Component> components) {
        this.components = components;
    }
}
