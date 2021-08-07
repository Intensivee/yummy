package com.orange.mainservice.repository;

import com.orange.mainservice.entity.Recipe;
import com.orange.mainservice.entity.enums.TimeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Page<Recipe> findAllByUserId(Long id, Pageable pageable);

    Page<Recipe> findAllByUser_Username(String username, Pageable pageable);

    Page<Recipe> findAllByCategories_Name(String category, Pageable pageable);

    Page<Recipe> findAllDistinctByIngredients_Component_name(String component, Pageable pageable);

    Page<Recipe> findByTitleIgnoreCaseContaining(String searchKey, Pageable pageable);

    Page<Recipe> findByTimeType(Pageable pageable, TimeType time);

    @Query("SELECT r FROM Recipe r LEFT JOIN r.rates rr GROUP BY r ORDER BY AVG(COALESCE(rr.value, 0)) DESC")
    List<Recipe> getRecipesByRateDesc(Pageable pageable);
}
