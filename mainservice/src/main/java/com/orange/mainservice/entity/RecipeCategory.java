package com.orange.mainservice.entity;

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
public class RecipeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(25)")
    private String name;

    @Column(name = "img_url")
    private String imgUrl;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private Set<Recipe> recipes;

    protected RecipeCategory(){
    }

    public RecipeCategory(Long id, String name, String imgUrl) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
    }
}
