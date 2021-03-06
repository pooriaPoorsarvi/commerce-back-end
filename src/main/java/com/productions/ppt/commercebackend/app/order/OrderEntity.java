package com.productions.ppt.commercebackend.app.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.productions.ppt.commercebackend.app.order.purchase.ProductPurchaseEntity;
import com.productions.ppt.commercebackend.app.user.models.UserEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class OrderEntity {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date purchaseDate;

    @NotNull Double amountPayed;

    @Size(min = 1, max = 5000)
    private String address;

    @JsonIgnore
    @Column(columnDefinition="INT  default '0'", nullable = false)
    private Integer finalised=0;


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private Set<ProductPurchaseEntity> productsPurchasedEntityList;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private UserEntity owner;

    @Column(nullable = false, columnDefinition = "INT default '0'")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer numberOfProductPurchase = 0;


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


    Set<ProductPurchaseEntity> getProductsPurchasedEntityListCopy(){
        return new HashSet<>(productsPurchasedEntityList);
    }
    public void addProductPurchaseEntity(ProductPurchaseEntity p){
        this.productsPurchasedEntityList.add(p);
        this.numberOfProductPurchase = this.productsPurchasedEntityList.size();
//        TODO check if this value is updated at all places
        this.amountPayed += p.getCount() * p.getIndividualPriceAtPurchase();
    }

    public void setProductsPurchasedEntityList(Set<ProductPurchaseEntity> productEntityList) {
        this.productsPurchasedEntityList = productEntityList;
    }
    public OrderEntity() {
    }


}
