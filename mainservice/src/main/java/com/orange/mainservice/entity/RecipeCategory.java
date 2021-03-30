package com.orange.mainservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "RecipeCategory")
@Table(
        name = "recipe_categories",
        uniqueConstraints = {
                @UniqueConstraint(name = "recipe_category_name_unique", columnNames = "name")
        }
)
@Getter
@AllArgsConstructor
public class RecipeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(25)")
    private String name;

    @Column(name = "img_url")
    private String imgUrl;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "recipe_categories_recipes",
            joinColumns = @JoinColumn(name = "recipe_category_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private Set<Recipe> recipes;

    protected RecipeCategory(){
    }
}
