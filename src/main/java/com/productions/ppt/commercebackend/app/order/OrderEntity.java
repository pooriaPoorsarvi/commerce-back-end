package com.productions.ppt.commercebackend.app.order;

import com.productions.ppt.commercebackend.app.product.ProductEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
public class OrderEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Integer id;

    @NotNull Date purchaseDate;

    @NotNull Double amountPayed;

    @Size(min = 1, max = 5000)
    String address;

    @ManyToMany(fetch = FetchType.LAZY)
    Set<ProductEntity> productEntities;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Double getAmountPayed() {
        return amountPayed;
    }

    public void setAmountPayed(Double amountPayed) {
        this.amountPayed = amountPayed;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<ProductEntity> getProductEntities() {
        return productEntities;
    }

    public void setProductEntities(Set<ProductEntity> productEntities) {
        this.productEntities = productEntities;
    }

    public OrderEntity() {
    }
}
