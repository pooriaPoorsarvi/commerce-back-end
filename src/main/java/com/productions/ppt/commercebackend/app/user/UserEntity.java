package com.productions.ppt.commercebackend.app.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.productions.ppt.commercebackend.app.order.OrderEntity;
import com.productions.ppt.commercebackend.app.product.ProductEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class UserEntity {

//  TODO : add user password field and jwt

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  Integer id;

  @Size(min = 1, max = 100)
  String firstName;

  @Size(min = 1, max = 500)
  String lastName;

  @Email
  @Size(min = 5, max = 500)
  @Column(unique = true)
  String email;

  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY)
  Set<OrderEntity> orderEntityList;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY)
  Set<ProductEntity> activeShoppingCart;




  public Set<ProductEntity> getActiveShoppingCart() {
    return activeShoppingCart;
  }

  public void setActiveShoppingCart(Set<ProductEntity> activeShoppingCart) {
    this.activeShoppingCart = activeShoppingCart;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<OrderEntity> getOrderEntityList() {
    return orderEntityList;
  }

  public void setOrderEntityList(Set<OrderEntity> orderEntityList) {
    this.orderEntityList = orderEntityList;
  }

  public UserEntity() {}
}
