package com.productions.ppt.commercebackend.app.shopping.cart;

import com.productions.ppt.commercebackend.app.product.ProductEntity;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.Set;

@Entity
public class ShoppingCartEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  Integer id;


  @ManyToMany(fetch = FetchType.LAZY)
  Set<ProductEntity> productEntities;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Set<ProductEntity> getProductEntities() {
    return productEntities;
  }

  public void setProductEntities(Set<ProductEntity> productEntities) {
    this.productEntities = productEntities;
  }

  public ShoppingCartEntity() {}
}
