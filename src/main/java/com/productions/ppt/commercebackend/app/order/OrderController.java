package com.productions.ppt.commercebackend.app.order;

import com.productions.ppt.commercebackend.app.product.purchase.ProductPurchaseEntity;
import com.productions.ppt.commercebackend.app.product.purchase.ProductPurchaseRepository;
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

  public OrderController(
      OrderRepository orderRepository, ProductPurchaseRepository productPurchaseRepository) {
    this.orderRepository = orderRepository;
    this.productPurchaseRepository = productPurchaseRepository;
  }

  //    TODO : Implement 404 for all of the functions
  @GetMapping("/orders/{ID}")
  OrderEntity getOrder(@PathVariable Integer ID) {
    Optional<OrderEntity> opt = orderRepository.findById(ID);
    if (!opt.isPresent()) {
      throw new RuntimeException();
    }
    return opt.get();
  }

  @GetMapping("/orders/{ID}/purchased-products")
  Set<ProductPurchaseEntity> getOrderPurchasedProducts(@PathVariable Integer ID) {
    Optional<OrderEntity> opt = orderRepository.findById(ID);
    if (!opt.isPresent()) {
      throw new RuntimeException();
    }
    return opt.get().getProductsPurchasedEntityList();
  }

  @PostMapping("/orders")
  ResponseEntity<Object> createOrder(@Valid @RequestBody OrderEntity orderEntity) {
    orderEntity.finalised = 0;
    orderRepository.save(orderEntity);
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
    if (orderEntity.getFinalised() != 0){
      throw new BusinessErrorException("Order already finalised.");
    }
    orderEntity.setFinalised(1);
    orderRepository.save(orderEntity);
    return ResponseEntity.ok().build();
  }
}
