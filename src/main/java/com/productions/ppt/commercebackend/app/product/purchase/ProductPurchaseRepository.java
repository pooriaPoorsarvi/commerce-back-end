package com.productions.ppt.commercebackend.app.product.purchase;

import com.productions.ppt.commercebackend.app.product.purchase.ProductPurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface ProductPurchaseRepository extends JpaRepository<ProductPurchaseEntity, Integer> {
  @Override
  Optional<ProductPurchaseEntity> findById(Integer Id);
  @Override
  void deleteById(Integer Id);
}
