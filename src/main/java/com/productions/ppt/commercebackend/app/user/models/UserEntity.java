package com.productions.ppt.commercebackend.app.user.models;

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
  @JsonIgnore
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Integer id;

  @Size(min = 1, max = 100)
  private String firstName;

  @Size(min = 1, max = 500)
  private String lastName;

  @Email
  @Size(min = 5, max = 500)
  @Column(unique = true)
  private String email;

  @Size(min = 5, max = 500)
  private String password;

  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY)
  private Set<OrderEntity> orderEntityList;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY)
  private Set<ProductEntity> activeShoppingCart;

  @Column(nullable = false, columnDefinition = "INT  default '1'")
  private boolean isEnabled = true;

  @Column(nullable = false, columnDefinition = "INT  default '1'")
  private boolean isCredentialsNonExpired = true;

  @Column(nullable = false, columnDefinition = "INT  default '1'")
  private boolean isAccountNonLocked = true;

  @Column(nullable = false, columnDefinition = "INT  default '1'")
  private boolean isAccountExpired = true;

  @ManyToMany(fetch = FetchType.EAGER)
  Set<Role> roles;

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public void setEnabled(boolean enabled) {
    isEnabled = enabled;
  }

  public boolean isCredentialsNonExpired() {
    return isCredentialsNonExpired;
  }

  public void setCredentialsNonExpired(boolean credentialsNonExpired) {
    isCredentialsNonExpired = credentialsNonExpired;
  }

  public boolean isAccountNonLocked() {
    return isAccountNonLocked;
  }

  public void setAccountNonLocked(boolean accountNonLocked) {
    isAccountNonLocked = accountNonLocked;
  }

  public boolean isAccountExpired() {
    return isAccountExpired;
  }

  public void setAccountExpired(boolean accountExpired) {
    isAccountExpired = accountExpired;
  }

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