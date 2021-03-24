package com.orange.mainservice.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Component")
@Table(name = "components")
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(30)")
    private String name;

    @Column(name = "is_accepted", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isAccepted;

    @OneToMany(mappedBy = "component")
    private Set<Ingredient> ingredients;

    @ManyToMany(mappedBy = "components")
    private Set<ComponentCategory> categories;

    public Component() {
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

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<ComponentCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<ComponentCategory> categories) {
        this.categories = categories;
    }
}
