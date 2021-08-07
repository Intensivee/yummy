package com.orange.mainservice.repository;

import com.orange.mainservice.entity.ComponentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentCategoryRepository extends JpaRepository<ComponentCategory, Long> {

    ComponentCategory getById(Long id);
}
