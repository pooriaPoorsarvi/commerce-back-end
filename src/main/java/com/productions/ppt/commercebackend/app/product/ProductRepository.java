package com.productions.ppt.commercebackend.app.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface ProductRepository
    extends JpaRepository<ProductEntity, Integer>,
        PagingAndSortingRepository<ProductEntity, Integer> {
  @Override
  Optional<ProductEntity> findById(Integer id);

  @Override
  Page<ProductEntity> findAll(Pageable pageable);

}
