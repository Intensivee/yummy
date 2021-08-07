package com.orange.mainservice.repository;

import com.orange.mainservice.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;


public interface ComponentRepository extends JpaRepository<Component, Long> {

    Optional<Component> getById(Long id);

    Set<Component> findAllByCategories_Id(Long id);
}
