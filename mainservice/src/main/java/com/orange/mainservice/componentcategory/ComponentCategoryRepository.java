package com.orange.mainservice.componentcategory;

import org.springframework.data.jpa.repository.JpaRepository;

interface ComponentCategoryRepository extends JpaRepository<ComponentCategory, Long> {

    ComponentCategory getById(Long id);
}
