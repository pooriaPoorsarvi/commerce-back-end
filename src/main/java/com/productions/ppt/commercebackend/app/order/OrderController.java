package com.productions.ppt.commercebackend.app.order;

import com.productions.ppt.commercebackend.app.product.ProductEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

@RestController
public class OrderController {

  OrderRepository orderRepository;

  public OrderController(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
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
      @PathVariable Integer ID, @Valid @RequestBody ProductEntity[] productEntities) {

    Optional<OrderEntity> orderEntityOptional = orderRepository.findById(ID);

    if (!orderEntityOptional.isPresent()||orderEntityOptional.get().finalised!=0) {
      throw new RuntimeException();
    }

    OrderEntity orderEntity = orderEntityOptional.get();
    orderEntity.getProductEntities().addAll(Arrays.asList(productEntities));
    orderRepository.save(orderEntity);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{ID}")
            .buildAndExpand(orderEntity.id)
            .toUri();
    return ResponseEntity.created(location).build();
  }
}
