package com.productions.ppt.commercebackend.app.order;

import com.productions.ppt.commercebackend.app.product.purchase.ProductPurchaseEntity;
import com.productions.ppt.commercebackend.app.product.purchase.ProductPurchaseRepository;
import com.productions.ppt.commercebackend.app.user.UserEntity;
import com.productions.ppt.commercebackend.app.user.UserRepository;
import com.productions.ppt.commercebackend.exceptions.BusinessErrorException;
import com.productions.ppt.commercebackend.exceptions.EntityNotFoundInDBException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

@RestController
public class OrderController {

  OrderRepository orderRepository;
  ProductPurchaseRepository productPurchaseRepository;
  UserRepository userRepository;

  public OrderController(
      OrderRepository orderRepository,
      ProductPurchaseRepository productPurchaseRepository,
      UserRepository userRepository) {
    this.orderRepository = orderRepository;
    this.productPurchaseRepository = productPurchaseRepository;
    this.userRepository = userRepository;
  }

  //    TODO : Implement 404 for all of the functions
  @GetMapping("/orders/{ID}")
  OrderEntity getOrder(@PathVariable Integer ID) {
    return orderRepository
        .findById(ID)
        .<EntityNotFoundInDBException>orElseThrow(
            () -> {
              throw new EntityNotFoundInDBException("Order not found.");
            });
  }

  @GetMapping("/orders/{ID}/purchased-products")
  Set<ProductPurchaseEntity> getOrderPurchasedProducts(@PathVariable Integer ID) {
    return orderRepository
        .findById(ID)
        .map(OrderEntity::getProductsPurchasedEntityList)
        .<EntityNotFoundInDBException>orElseThrow(
            () -> {
              throw new EntityNotFoundInDBException("Order not found.");
            });
  }

  @PostMapping("/orders")
  ResponseEntity<Object> createOrder(@Valid @RequestBody OrderEntity orderEntity) {

    // TODO In the future you have to get the ID using jwt
    UserEntity userEntity =
        userRepository
            .findById(1)
            .<EntityNotFoundInDBException>orElseThrow(
                () -> {
                  throw new EntityNotFoundInDBException("User not found");
                });
    orderEntity.finalised = 0;
    orderEntity.setOwner(userEntity);
    orderEntity.setPurchaseDate(new Date());
    orderRepository.save(orderEntity);
    userEntity.getOrderEntityList().add(orderEntity);
    userRepository.save(userEntity);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{ID}")
            .buildAndExpand(orderEntity.id)
            .toUri();
    return ResponseEntity.created(location).build();
  }

  @PostMapping("/orders/{ID}/finalize")
  ResponseEntity<Object> finalise(@PathVariable Integer ID) {
    // TODO :   Finalize, add checks for finalising in other functions, add date here for finalising
    //    Also make this and the one in product and category which have the same pattern use a
    //    transaction
    Optional<OrderEntity> opt = orderRepository.findById(ID);
    if (!opt.isPresent()) {
      throw new EntityNotFoundInDBException("Order not found.");
    }
    OrderEntity orderEntity = opt.get();
    if (orderEntity.getFinalised() != 0) {
      throw new BusinessErrorException("Order already finalised.");
    }
    orderEntity.setFinalised(1);
    orderRepository.save(orderEntity);
    return ResponseEntity.ok().build();
  }
}
