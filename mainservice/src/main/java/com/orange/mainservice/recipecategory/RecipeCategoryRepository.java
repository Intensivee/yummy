package com.orange.mainservice.recipecategory;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Long> {

    List<RecipeCategory> findAllByOrderByNameAsc();

    List<RecipeCategory> findAllByImgUrlIsNotNull(Pageable pageable);

    List<RecipeCategory> findAllByRecipes_id(Long recipeId);
}
