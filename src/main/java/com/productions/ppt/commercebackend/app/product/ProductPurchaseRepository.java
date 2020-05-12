package com.productions.ppt.commercebackend.app.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductPurchaseRepository extends JpaRepository<ProductPurchaseEntity, Integer> {
  @Override
  Optional<ProductPurchaseEntity> findById(Integer Id);
}
