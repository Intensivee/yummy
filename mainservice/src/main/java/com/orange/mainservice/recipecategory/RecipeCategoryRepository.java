package com.orange.mainservice.recipecategory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Long> {

    List<RecipeCategory> findAllByOrderByNameAsc();
}
