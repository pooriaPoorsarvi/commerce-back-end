package com.productions.ppt.commercebackend.app.user.controller;

import com.productions.ppt.commercebackend.app.product.ProductEntity;
import com.productions.ppt.commercebackend.app.product.ProductService;
import com.productions.ppt.commercebackend.app.user.models.Role;
import com.productions.ppt.commercebackend.app.user.services.UserService;
import com.productions.ppt.commercebackend.app.user.models.UserEntity;
import com.productions.ppt.commercebackend.config.security.UsersConfiguration.GeneralUserDetailsService;
import com.productions.ppt.commercebackend.exceptions.AuthenticationError;
import com.productions.ppt.commercebackend.exceptions.BusinessErrorException;
import com.productions.ppt.commercebackend.exceptions.EntityNotFoundInDBException;
import com.productions.ppt.commercebackend.shared.utils.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@RestController
class UserController {

  UserService userService;
  ProductService productService;
  PasswordEncoder passwordEncoder;
  JWTUtil jwtUtil;
  GeneralUserDetailsService generalUserDetailsService;

  public UserController(
      UserService userService,
      ProductService productService,
      PasswordEncoder passwordEncoder,
      JWTUtil jwtUtil,
      GeneralUserDetailsService generalUserDetailsService) {
    this.userService = userService;
    this.productService = productService;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
    this.generalUserDetailsService = generalUserDetailsService;
  }

//  TODO right now users is at conflict with users post "security wise" because one need authentication
//    and the other doesn't. Hence the instanceof. Check if you can remove this.
  @GetMapping("users")
  UserEntity getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication.getPrincipal() instanceof UserDetails)){
      throw new AuthenticationError("User not authenticated.");
    }
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    return this.userService
        .findByEmail(userDetails.getUsername())
        .<EntityNotFoundInDBException>orElseThrow(
            () -> {
              throw new EntityNotFoundInDBException("User not found.");
            });
  }

  @CrossOrigin()
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
    userEntity.setRoles(new HashSet<>());
    Role role = new Role();
    role.setRole("ROLE_USER");
    userEntity.getRoles().add(role);
    userService.save(userEntity);
    String jwt = jwtUtil.generateToken(
            generalUserDetailsService.loadUserByUsername(userEntity.getEmail()));
    return ResponseEntity.ok(new SingUpResult(jwt, userEntity.getEmail()));
  }

  @PostMapping("/users/shopping-cart-products")
  ResponseEntity<Object> addProductToShoppingCart(
      @Valid @RequestBody Integer[] productEntitiesIds) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    UserEntity userEntity =
        userService
            .findByEmail(userDetails.getUsername())
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

  @GetMapping("/users/shopping-cart-products")
  Set<ProductEntity> getShoppingCart() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    return userService
        .findByEmail(userDetails.getUsername())
        .map(UserEntity::getActiveShoppingCart)
        .<EntityNotFoundInDBException>orElseThrow(
            () -> {
              throw new EntityNotFoundInDBException("User not found.");
            });
  }
}
