package com.productions.ppt.commercebackend.app.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProductEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  Integer id;

  String name;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ProductEntity() {}

  public ProductEntity(Integer id, String name) {
    this.id = id;
    this.name = name;
  }
}
