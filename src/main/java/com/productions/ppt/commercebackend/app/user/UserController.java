package com.productions.ppt.commercebackend.app.user;

import com.productions.ppt.commercebackend.app.product.ProductEntity;
import com.productions.ppt.commercebackend.app.product.ProductRepository;
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
    return this.userRepository.findById(ID).orElse(null);
  }

  @PostMapping("/users/{ID}/shopping-cart-products")
  ResponseEntity<Object> addProductToShoppingCart(
      @PathVariable Integer ID, @Valid @RequestBody Integer[] productEntitiesIds) {
    Optional<UserEntity> opt = userRepository.findById(ID);
    if (!opt.isPresent()) {
      throw new RuntimeException();
    }
    UserEntity userEntity = opt.get();
    for (Integer productEntityId : productEntitiesIds) {
      if (!productRepository.findById(productEntityId).isPresent()) {
        throw new RuntimeException("Product not found");
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
    Optional<UserEntity> opt = userRepository.findById(ID);
    if (!opt.isPresent()) {
      throw new RuntimeException("User not found");
    }
    return opt.get().getActiveShoppingCart();
  }
}
