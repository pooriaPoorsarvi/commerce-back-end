package com.productions.ppt.commercebackend.app.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class ProductPurchaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Integer id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne
    ProductEntity productEntity;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    double priceAtPurchase = 0;

    @PrePersist
    void setUpInitPrice(){
        this.priceAtPurchase = this.productEntity.price;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Integer count;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public double getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(double priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
