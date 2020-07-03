package com.productions.ppt.commercebackend.app.order.purchase;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface ProductPurchaseRepository extends JpaRepository<ProductPurchaseEntity, Integer> {
  @Override
  Optional<ProductPurchaseEntity> findById(Integer Id);
  @Override
  void deleteById(Integer Id);
}
