package com.productions.ppt.commercebackend.app.order;

import com.productions.ppt.commercebackend.app.product.ProductEntity;
import com.productions.ppt.commercebackend.app.product.ProductPurchaseEntity;
import com.productions.ppt.commercebackend.app.product.ProductPurchaseRepository;
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
    return this.orderRepository.findById(ID).orElse(null);
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

  @PostMapping("/orders/{ID}/add-product")
  ResponseEntity<Object> addProducts(
      @PathVariable Integer ID, @Valid @RequestBody ProductPurchaseEntity[] productEntities) {

    Optional<OrderEntity> orderEntityOptional = orderRepository.findById(ID);

    if (!orderEntityOptional.isPresent() || orderEntityOptional.get().finalised != 0) {
      throw new RuntimeException();
    }

    for (ProductPurchaseEntity productEntity : productEntities) {
      this.productPurchaseRepository.save(productEntity);
    }

    OrderEntity orderEntity = orderEntityOptional.get();
    orderEntity
        .getProductsPurchasedEntityList()
        .addAll(new ArrayList<>(Arrays.asList(productEntities)));
    orderRepository.save(orderEntity);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{ID}")
            .buildAndExpand(orderEntity.id)
            .toUri();
    return ResponseEntity.created(location).build();
  }

  @PostMapping("/orders/{ID}/finalize")
  ResponseEntity<Object> finalise() {
    // TODO :   Finalize, add checks for finalising in other functions, add date here for finalising
    //    Also make this and the one in product and category which have the same pattern use a
    //    transaction
    return null;
  }
}
