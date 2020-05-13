package com.productions.ppt.commercebackend.app.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    @Override
    Optional<CategoryEntity> findById(Integer id);
    List<CategoryEntity> findAll();
}
