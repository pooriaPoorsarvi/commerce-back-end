package com.productions.ppt.commercebackend.app.user;

import com.productions.ppt.commercebackend.app.product.ProductEntity;
import com.productions.ppt.commercebackend.app.product.ProductPurchaseEntity;
import com.productions.ppt.commercebackend.app.product.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
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
    return this.userRepository.findById(ID).orElse(null);
  }

  @PostMapping("/users/{ID}/shopping-cart-products")
  ResponseEntity<Object> addProductToShoppingCart(
      @PathVariable Integer ID, @Valid @RequestBody ProductEntity[] productEntities) {
    Optional<UserEntity> opt = userRepository.findById(ID);
    if (!opt.isPresent()) {
      throw new RuntimeException();
    }
    UserEntity userEntity = opt.get();
    for (ProductEntity productEntity : productEntities) {
      if (!productRepository.findById(productEntity.getId()).isPresent()) {
        throw new RuntimeException();
      } else {
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
    Optional<UserEntity> opt = userRepository.findById(ID);
    if (!opt.isPresent()) {
      throw new RuntimeException("User not found");
    }
    return opt.get().getActiveShoppingCart();
  }
}
