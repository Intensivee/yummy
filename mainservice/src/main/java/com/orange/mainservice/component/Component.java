package com.orange.mainservice.component;

import com.orange.mainservice.componentcategory.ComponentCategory;
import com.orange.mainservice.ingredient.Ingredient;
import lombok.Getter;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Component")
@Table(
        name = "components",
        uniqueConstraints = {
                @UniqueConstraint(name = "component_name_unique", columnNames = "name")
        },
        indexes = @Index(name="components_name_index", columnList = "name")
)
@Getter
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(30)")
    private String name;

    @Column(name = "is_accepted", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isAccepted;

    @OneToMany(mappedBy = "component", fetch = FetchType.LAZY)
    private Set<Ingredient> ingredients;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "component_categories_component",
            joinColumns = @JoinColumn(name = "component_id"),
            inverseJoinColumns = @JoinColumn(name = "component_category_id")
    )
    private Set<ComponentCategory> categories;

    protected Component() {
    }

    public Component(Long id, String name, Boolean isAccepted, Set<ComponentCategory> categories) {
        this.id = id;
        this.name = name;
        this.isAccepted = isAccepted;
        this.categories = categories;
    }
}
