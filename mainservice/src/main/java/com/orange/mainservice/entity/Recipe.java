package com.orange.mainservice.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Recipe")
@Table(
        name = "recipes",
        indexes = @Index( name = "recipes_user_id_index", columnList = "user_id")
)
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "time_type", nullable = false, columnDefinition = "time_type")
    private TimeType timeType;

    @Column(name = "title", nullable = false, columnDefinition = "VARCHAR(40)")
    private String title;

    @Column(name = "img_url")
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private Set<Rate> rates;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private Set<Direction> directions;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private Set<Ingredient> ingredients;

    @ManyToMany(mappedBy = "recipes", fetch = FetchType.LAZY)
    private Set<RecipeCategory> categories;

    public enum TimeType {
        TIME_1, TIME_2, TIME_3
    }

    public Recipe() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimeType getTimeType() {
        return timeType;
    }

    public void setTimeType(TimeType timeType) {
        this.timeType = timeType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Rate> getRates() {
        return rates;
    }

    public void setRates(Set<Rate> rates) {
        this.rates = rates;
    }

    public Set<Direction> getDirections() {
        return directions;
    }

    public void setDirections(Set<Direction> directions) {
        this.directions = directions;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<RecipeCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<RecipeCategory> categories) {
        this.categories = categories;
    }
}
