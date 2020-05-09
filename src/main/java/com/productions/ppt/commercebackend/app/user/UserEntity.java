package com.productions.ppt.commercebackend.app.user;

import com.productions.ppt.commercebackend.app.order.OrderEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class UserEntity {

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

  @OneToMany(fetch = FetchType.LAZY)
  Set<OrderEntity> orderEntityList;

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
