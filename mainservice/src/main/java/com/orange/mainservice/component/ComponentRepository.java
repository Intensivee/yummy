package com.orange.mainservice.component;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;


interface ComponentRepository extends JpaRepository<Component, Long> {

    Optional<Component> getById(Long id);

    Set<Component> findAllByCategories_Id(Long id);
}
