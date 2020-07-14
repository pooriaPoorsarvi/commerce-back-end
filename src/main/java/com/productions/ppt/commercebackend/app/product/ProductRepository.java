package com.productions.ppt.commercebackend.app.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
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

  //  TODO make this lists into pages

  List<ProductEntity> findByNameIgnoreCaseContaining(String name);

  List<ProductEntity> findByDescriptionIgnoreCaseContaining(String name);

  @Query(
      value =
              "select distinct p from ProductEntity as p where p.description like %:searchExpression% or p.name like %:searchExpression% ")
  List<ProductEntity> findBySearch(@Param("searchExpression") String searchExpression);
}
