package com.productions.ppt.commercebackend.app.product.purchase;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ProductPurchaseService {
  Optional<ProductPurchaseEntity> findById(Integer Id);

  void deleteById(Integer Id);
}
