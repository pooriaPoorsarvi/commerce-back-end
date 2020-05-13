package com.productions.ppt.commercebackend.app.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.productions.ppt.commercebackend.app.product.purchase.ProductPurchaseEntity;
import com.productions.ppt.commercebackend.app.user.UserEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
public class OrderEntity {

    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Integer id;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Date purchaseDate;

    @NotNull Double amountPayed;

    @Size(min = 1, max = 5000)
    String address;

    @JsonIgnore
    @Column(columnDefinition="INT  default '0'", nullable = false)
    Integer finalised=0;


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private Set<ProductPurchaseEntity> productsPurchasedEntityList;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    UserEntity owner;


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
