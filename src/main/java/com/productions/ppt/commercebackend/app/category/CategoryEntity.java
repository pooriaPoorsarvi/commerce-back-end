package com.productions.ppt.commercebackend.app.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.productions.ppt.commercebackend.app.product.ProductEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class CategoryEntity {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Size(min = 1, max = 1000)
  private String name;

  @Size(min = 0, max = 2000)
  private String imgSrc;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categoryEntityList")
  private Set<ProductEntity> productEntities;

  public Set<ProductEntity> getProductEntities() {
    return productEntities;
  }

  public void setProductEntities(Set<ProductEntity> productEntities) {
    this.productEntities = productEntities;
  }

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

  public String getImgSrc() {
    return imgSrc;
  }

  public void setImgSrc(String imgSrc) {
    this.imgSrc = imgSrc;
  }
}
