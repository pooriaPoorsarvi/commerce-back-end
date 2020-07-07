package com.productions.ppt.commercebackend.app.order;

import com.productions.ppt.commercebackend.app.order.purchase.ProductPurchaseEntity;
import com.productions.ppt.commercebackend.app.order.purchase.ProductPurchaseService;
import com.productions.ppt.commercebackend.app.product.ProductEntity;
import com.productions.ppt.commercebackend.app.product.ProductService;
import com.productions.ppt.commercebackend.app.user.models.UserEntity;
import com.productions.ppt.commercebackend.app.user.services.UserService;
import com.productions.ppt.commercebackend.exceptions.BusinessErrorException;
import com.productions.ppt.commercebackend.exceptions.EntityNotFoundInDBException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

@RestController
class OrderController {

  OrderRepository orderRepository;
  UserService userService;
  ProductPurchaseService productPurchaseService;
  ProductService productService;

  public OrderController(
      OrderRepository orderRepository,
      UserService userService,
      ProductPurchaseService productPurchaseService,
      ProductService productService) {
    this.orderRepository = orderRepository;
    this.userService = userService;
    this.productPurchaseService = productPurchaseService;
    this.productService = productService;
  }

  //    TODO : Implement 404 for all of the functions
//  TODO make the gets work with the user ids
  @CrossOrigin()
  @GetMapping("/orders/{ID}")
  OrderEntity getOrder(@PathVariable Integer ID) {
    return orderRepository
        .findById(ID)
        .<EntityNotFoundInDBException>orElseThrow(
            () -> {
              throw new EntityNotFoundInDBException("Order not found.");
            });
  }

  @CrossOrigin()
  @GetMapping("/orders/{ID}/purchased-products")
  Set<ProductPurchaseEntity> getOrderPurchasedProducts(@PathVariable Integer ID) {
    return orderRepository
        .findById(ID)
        .map(OrderEntity::getProductsPurchasedEntityListCopy)
        .<EntityNotFoundInDBException>orElseThrow(
            () -> {
              throw new EntityNotFoundInDBException("Order not found.");
            });
  }


  @CrossOrigin()
  @GetMapping("/orders")
  List<OrderEntity> getAllOrders(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    UserEntity userEntity =
            userService
                    .findByEmail(userDetails.getUsername())
                    .<EntityNotFoundInDBException>orElseThrow(
                            () -> {
                              throw new EntityNotFoundInDBException("User not found");
                            });
    return orderRepository.findByEmail(userEntity.getEmail());
  }
  @CrossOrigin()
  @PostMapping("/orders")
  ResponseEntity<?> createNewOrder(@RequestBody @Valid List<OrderProductsInput> orderProductsInputs) {
    int totalSum = 0;
    for (OrderProductsInput in: orderProductsInputs) {
        totalSum += in.numberOfProduct;
    }
    if (orderProductsInputs.size() == 0 || totalSum == 0){
      throw new BusinessErrorException("The number of orders are not sufficient for ordering");
    }
    OrderEntity orderEntity = createOrder(new OrderEntity());
    // TODO  do all the checks here that the order is valid
    for (OrderProductsInput orderProductsInput : orderProductsInputs) {

      System.out.println(productService);
      System.out.println(orderProductsInput);
      System.out.println(orderProductsInput.getProduct());
      System.out.println(orderProductsInput.getProduct().getId());
      ProductEntity productEntity =
          productService
              .findById(orderProductsInput.getProduct().getId())
              .<BusinessErrorException>orElseThrow(
                  () -> {
                    throw new BusinessErrorException("Product not found for adding to the order");
                  });
      ProductPurchaseEntity productPurchaseEntity = new ProductPurchaseEntity();
      productPurchaseEntity.setProductEntity(productEntity);
      productPurchaseEntity.setCount(orderProductsInput.getNumberOfProduct());
      productPurchaseEntity.setOrderEntity(orderEntity);
      productPurchaseEntity.setIndividualPriceAtPurchase(productEntity.getPrice());
      //      TODO check whether or not you can use the finalised variable later
      productPurchaseEntity.setFinalised(1);
      productPurchaseService.save(productPurchaseEntity);
      orderEntity.addProductPurchaseEntity(productPurchaseEntity);
      orderRepository.save(orderEntity);
      orderRepository.flush();
    }
    URI location =
            ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{ID}")
                    .buildAndExpand(orderEntity.getId())
                    .toUri();
    return ResponseEntity.created(location).build();
  }

  OrderEntity createOrder(@Valid @RequestBody OrderEntity orderEntity) {

    // TODO In the future you have to get the ID using jwt
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    UserEntity userEntity =
        userService
            .findByEmail(userDetails.getUsername())
            .<EntityNotFoundInDBException>orElseThrow(
                () -> {
                  throw new EntityNotFoundInDBException("User not found");
                });
    orderEntity.setFinalised(0);
    orderEntity.setOwner(userEntity);
    orderEntity.setPurchaseDate(new Date());
    orderEntity.setProductsPurchasedEntityList(new HashSet<>());
    orderEntity.setAmountPayed(0.);
    orderRepository.save(orderEntity);
    userEntity.getOrderEntityList().add(orderEntity);
    userService.save(userEntity);
    return orderEntity;
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
