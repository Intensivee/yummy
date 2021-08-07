package com.orange.mainservice.recipecategory;

import org.springframework.data.jpa.repository.JpaRepository;

interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Long> {
}
