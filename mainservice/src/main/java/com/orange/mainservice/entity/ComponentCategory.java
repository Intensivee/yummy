package com.orange.mainservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "ComponentCategory")
@Table(
        name = "component_categories",
        uniqueConstraints = {
                @UniqueConstraint(name = "component_category_name_unique", columnNames = "name")
        }
)
@Getter
@AllArgsConstructor
public class ComponentCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(25)")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "component_categories_component",
            joinColumns = @JoinColumn(name = "component_category_id"),
            inverseJoinColumns = @JoinColumn(name = "component_id")
    )
    private Set<Component> components;

    protected ComponentCategory() {
    }
}
