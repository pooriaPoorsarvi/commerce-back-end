package com.productions.ppt.commercebackend.app.product.purchase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.productions.ppt.commercebackend.app.order.OrderEntity;
import com.productions.ppt.commercebackend.app.product.ProductEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ProductPurchaseEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  Integer id;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Column(columnDefinition = "INT  default '0'")
  @NotNull
  private Integer finalised = 0;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @ManyToOne
  ProductEntity productEntity;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  double individualPriceAtPurchase = 0;

  //  TODO since the shopping cart is now integrated with the user check whether or not these
  // presets are still needed
  @PostLoad
  @PrePersist
  void setUpInitPrice() {
    if (this.finalised == 0) this.individualPriceAtPurchase = this.productEntity.getPrice();
  }

  @JsonIgnore
  @ManyToOne
  OrderEntity orderEntity;


  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Integer count;

  public Integer getFinalised() {
    return finalised;
  }

  public OrderEntity getOrderEntity() {
    return orderEntity;
  }

  public void setOrderEntity(OrderEntity orderEntity) {
    this.orderEntity = orderEntity;
  }

  public void setFinalised(Integer finalised) {
    this.finalised = finalised;
  }

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

  public double getIndividualPriceAtPurchase() {
    return individualPriceAtPurchase;
  }

  public void setIndividualPriceAtPurchase(double priceAtPurchase) {
    this.individualPriceAtPurchase = priceAtPurchase;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    if (finalised == 0) this.count = count;
  }
}
