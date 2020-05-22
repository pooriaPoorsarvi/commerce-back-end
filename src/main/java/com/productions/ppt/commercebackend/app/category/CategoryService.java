package com.productions.ppt.commercebackend.app.category;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {

  Optional<CategoryEntity> findById(Integer id);

  List<CategoryEntity> findAll();
}
