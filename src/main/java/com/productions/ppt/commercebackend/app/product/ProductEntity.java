package com.productions.ppt.commercebackend.app.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.productions.ppt.commercebackend.app.category.CategoryEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class ProductEntity {

//  TODO decouple all the dto and models
//  TODO check what happens if you let user writes the id's, right now this is being used in the creation of orders
//    since we actually check the id there
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Integer id;

  @Size(min = 1, max = 200)
  private String name;

  @Size(min = 1, max = 5000)
  private String description;

  @NotNull private Double price;

  @Size(min = 1, max = 2000)
  private String imageSrc;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Column(columnDefinition = "INT  default '0'", nullable = false)
  private Integer numberOfPurchases = 0;

  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JsonIgnore
  private Set<CategoryEntity> categoryEntityList;

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

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getImageSrc() {
    return imageSrc;
  }

  public void setImageSrc(String imageSrc) {
    this.imageSrc = imageSrc;
  }

  public Integer getNumberOfPurchases() {
    return numberOfPurchases;
  }

  public void setNumberOfPurchases(Integer numberOfPurchases) {
    this.numberOfPurchases = numberOfPurchases;
  }

  public ProductEntity() {}

  public ProductEntity(Integer id, String name) {
    this.id = id;
    this.name = name;
  }
}
