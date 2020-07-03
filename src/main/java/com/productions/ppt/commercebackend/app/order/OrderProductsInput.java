package com.productions.ppt.commercebackend.app.order;

import com.productions.ppt.commercebackend.app.product.ProductEntity;

public class OrderProductsInput {
    ProductEntity product;
    Integer numberOfProduct;

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public Integer getNumberOfProduct() {
        return numberOfProduct;
    }

    public void setNumberOfProduct(Integer numberOfProduct) {
        this.numberOfProduct = numberOfProduct;
    }
}
