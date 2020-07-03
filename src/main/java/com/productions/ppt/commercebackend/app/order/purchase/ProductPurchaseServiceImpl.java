package com.productions.ppt.commercebackend.app.order.purchase;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class ProductPurchaseServiceImpl implements ProductPurchaseService{

    private ProductPurchaseRepository productPurchaseRepository;

    public ProductPurchaseServiceImpl(ProductPurchaseRepository productPurchaseRepository) {
        this.productPurchaseRepository = productPurchaseRepository;
    }

    @Override
    public Optional<ProductPurchaseEntity> findById(Integer Id) {
        return productPurchaseRepository.findById(Id);
    }

    @Override
    public void deleteById(Integer Id) {
        productPurchaseRepository.deleteById(Id);
    }

    @Override
    public void save(ProductPurchaseEntity productPurchaseEntity){
        this.productPurchaseRepository.save(productPurchaseEntity);
    }
}
