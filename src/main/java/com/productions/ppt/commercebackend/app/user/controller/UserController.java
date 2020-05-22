package com.productions.ppt.commercebackend.app.user.controller;

import com.productions.ppt.commercebackend.app.product.ProductEntity;
import com.productions.ppt.commercebackend.app.product.ProductService;
import com.productions.ppt.commercebackend.app.user.services.UserService;
import com.productions.ppt.commercebackend.app.user.models.UserEntity;
import com.productions.ppt.commercebackend.exceptions.BusinessErrorException;
import com.productions.ppt.commercebackend.exceptions.EntityNotFoundInDBException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

@RestController
class UserController {

  UserService userService;
  ProductService productService;
  PasswordEncoder passwordEncoder;

  public UserController(
      UserService userService,
      ProductService productService,
      PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.productService = productService;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("users/{ID}")
  UserEntity getUser(@PathVariable Integer ID) {
    return this.userService
        .findById(ID)
        .<EntityNotFoundInDBException>orElseThrow(
            () -> {
              throw new EntityNotFoundInDBException("User not found.");
            });
  }

  @PostMapping("users")
  ResponseEntity<?> addUser(@Valid @RequestBody UserSignUpInput userSignUpInput) {
    UserEntity userEntity = new UserEntity();
    if (userService.findByEmail(userSignUpInput.email).isPresent()) {
      throw new BusinessErrorException("Email already exists");
    }
    userEntity.setEmail(userSignUpInput.email);
    userEntity.setPassword(passwordEncoder.encode(userSignUpInput.password));
    userEntity.setAccountExpired(false);
    userEntity.setAccountNonLocked(true);
    userEntity.setCredentialsNonExpired(true);
    userEntity.setEnabled(true);
    userService.save(userEntity);
    return ResponseEntity.ok(userEntity.getId());
  }

  @PostMapping("/users/{ID}/shopping-cart-products")
  ResponseEntity<Object> addProductToShoppingCart(
      @PathVariable Integer ID, @Valid @RequestBody Integer[] productEntitiesIds) {
    UserEntity userEntity =
        userService
            .findById(ID)
            .<EntityNotFoundInDBException>orElseThrow(
                () -> {
                  throw new EntityNotFoundInDBException("user not found.");
                });
    for (Integer productEntityId : productEntitiesIds) {
      if (!productService.findById(productEntityId).isPresent()) {
        throw new EntityNotFoundInDBException("Product not found.");
      } else {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productEntityId);
        userEntity.getActiveShoppingCart().add(productEntity);
      }
    }
    userService.save(userEntity);
    URI location =
        ServletUriComponentsBuilder.fromUriString("/users")
            .path("/{ID}")
            .buildAndExpand(userEntity.getId())
            .toUri();
    return ResponseEntity.created(location).build();
  }

  @GetMapping("/users/{ID}/shopping-cart-products")
  Set<ProductEntity> getShoppingCart(@PathVariable Integer ID) {
    return userService
        .findById(ID)
        .map(UserEntity::getActiveShoppingCart)
        .<EntityNotFoundInDBException>orElseThrow(
            () -> {
              throw new EntityNotFoundInDBException("User not found.");
            });
  }
}