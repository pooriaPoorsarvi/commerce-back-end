package com.productions.ppt.commercebackend.app.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.productions.ppt.commercebackend.app.product.ProductEntity;
import com.productions.ppt.commercebackend.app.product.ProductPurchaseEntity;
import com.productions.ppt.commercebackend.app.user.UserEntity;

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

    @Column(columnDefinition="INT  default '0'")
    @NotNull
    Integer finalised;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ProductPurchaseEntity> productsPurchasedEntityList;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    UserEntity owner;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Double prince = 0.0;


    public Integer getFinalised() {
        return finalised;
    }

    public void setFinalised(Integer finalised) {
        this.finalised = finalised;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

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


    public Set<ProductPurchaseEntity> getProductsPurchasedEntityList() {
        return productsPurchasedEntityList;
    }

    public void setProductsPurchasedEntityList(Set<ProductPurchaseEntity> productEntityList) {
        this.productsPurchasedEntityList = productEntityList;
    }
    public OrderEntity() {
    }
}
