package com.productions.ppt.commercebackend.app.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
  Optional<ProductEntity> findById(Integer id);
  Page<ProductEntity> findAll(Pageable pageable);
  void save(ProductEntity productEntity);
}
