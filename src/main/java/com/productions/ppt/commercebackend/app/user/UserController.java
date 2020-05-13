package com.productions.ppt.commercebackend.app.user;

import com.productions.ppt.commercebackend.app.product.ProductEntity;
import com.productions.ppt.commercebackend.app.product.ProductRepository;
import com.productions.ppt.commercebackend.exceptions.EntityNotFoundInDBException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.Set;

@RestController
public class UserController {

  UserRepository userRepository;
  ProductRepository productRepository;

  public UserController(UserRepository userRepository, ProductRepository productRepository) {
    this.userRepository = userRepository;
    this.productRepository = productRepository;
  }

  @GetMapping("users/{ID}")
  UserEntity getUser(@PathVariable Integer ID) {
    return this.userRepository
        .findById(ID)
        .<EntityNotFoundInDBException>orElseThrow(
            () -> {
              throw new EntityNotFoundInDBException("User not found.");
            });
  }

  @PostMapping("/users/{ID}/shopping-cart-products")
  ResponseEntity<Object> addProductToShoppingCart(
      @PathVariable Integer ID, @Valid @RequestBody Integer[] productEntitiesIds) {
    UserEntity userEntity =
        userRepository
            .findById(ID)
            .<EntityNotFoundInDBException>orElseThrow(
                () -> {
                  throw new EntityNotFoundInDBException("user not found.");
                });
    for (Integer productEntityId : productEntitiesIds) {
      if (!productRepository.findById(productEntityId).isPresent()) {
        throw new EntityNotFoundInDBException("Product not found.");
      } else {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productEntityId);
        userEntity.getActiveShoppingCart().add(productEntity);
      }
    }
    userRepository.save(userEntity);
    URI location =
        ServletUriComponentsBuilder.fromUriString("/users")
            .path("/{ID}")
            .buildAndExpand(userEntity.id)
            .toUri();
    return ResponseEntity.created(location).build();
  }

  @GetMapping("/users/{ID}/shopping-cart-products")
  Set<ProductEntity> getShoppingCart(@PathVariable Integer ID) {
    return userRepository
        .findById(ID)
        .map(UserEntity::getActiveShoppingCart)
        .<EntityNotFoundInDBException>orElseThrow(
            () -> {
              throw new EntityNotFoundInDBException("User not found.");
            });
  }
}
