package com.orange.mainservice.repository;

import com.orange.mainservice.entity.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Long> {

}
