package com.orange.mainservice.repository;

import com.orange.mainservice.entity.ComponentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentCategoryRepository extends JpaRepository<ComponentCategory, Long> {

}
