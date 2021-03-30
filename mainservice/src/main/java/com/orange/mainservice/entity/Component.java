package com.orange.mainservice.entity;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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

    @ManyToMany(mappedBy = "components", fetch = FetchType.LAZY)
    private Set<ComponentCategory> categories;

    protected Component() {
    }
}
