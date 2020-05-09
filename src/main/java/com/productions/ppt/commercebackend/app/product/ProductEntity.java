package com.productions.ppt.commercebackend.app.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.productions.ppt.commercebackend.app.category.CategoryEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ProductEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  Integer id;

  @Size(min = 1, max = 200)
  String name;

  @Size(min = 1, max = 5000)
  String description;

  @NotNull Float price;

  @Size(min = 1, max = 2000)
  String imageSrc;

  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JsonIgnore
  private Set<CategoryEntity> categoryEntityList = new HashSet<>();

  public Set<CategoryEntity> getCategoryEntityList() {
    return categoryEntityList;
  }

  public void setCategoryEntityList(Set<CategoryEntity> categoryEntityList) {
    this.categoryEntityList = categoryEntityList;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  public String getImageSrc() {
    return imageSrc;
  }

  public void setImageSrc(String imageSrc) {
    this.imageSrc = imageSrc;
  }

  public ProductEntity() {}

  public ProductEntity(Integer id, String name) {
    this.id = id;
    this.name = name;
  }
}
