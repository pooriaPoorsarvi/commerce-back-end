package com.productions.ppt.commercebackend.app.product.purchase;

import com.productions.ppt.commercebackend.app.order.OrderEntity;
import com.productions.ppt.commercebackend.app.order.OrderRepository;
import com.productions.ppt.commercebackend.app.product.ProductEntity;
import com.productions.ppt.commercebackend.app.product.ProductRepository;
import com.productions.ppt.commercebackend.exceptions.BusinessErrorException;
import com.productions.ppt.commercebackend.exceptions.EntityNotFoundInDBException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
public class ProductPurchaseController {

  ProductPurchaseRepository productPurchaseRepository;
  ProductRepository productRepository;
  OrderRepository orderRepository;

  public ProductPurchaseController(
      ProductPurchaseRepository productPurchaseRepository,
      ProductRepository productRepository,
      OrderRepository orderRepository) {
    this.productPurchaseRepository = productPurchaseRepository;
    this.productRepository = productRepository;
    this.orderRepository = orderRepository;
  }

  @ApiOperation("Please make sure that the number that you are trying to add is positive.")
  @PostMapping("/product-purchase/{productId}/{orderId}/{number}")
  ResponseEntity<Object> createProductPurchase(
      @PathVariable Integer number,
      @PathVariable Integer productId,
      @PathVariable Integer orderId) {
    if (number <= 0) {
//      TODO  make sure that this number is less than the available products, and first reduce that
      throw new BusinessErrorException(
          "The number of products that are being bought was not positive.");
    }
    ProductEntity productEntity =
        productRepository
            .findById(productId)
            .<EntityNotFoundInDBException>orElseThrow(
                () -> {
                  throw new EntityNotFoundInDBException("Product not found.");
                });
    OrderEntity orderEntity =
        orderRepository
            .findById(orderId)
            .<EntityNotFoundInDBException>orElseThrow(
                () -> {
                  throw new EntityNotFoundInDBException("Order not found.");
                });
    ProductPurchaseEntity productPurchaseEntity = new ProductPurchaseEntity();
    productPurchaseEntity.setCount(number);
    productPurchaseEntity.setOrderEntity(orderEntity);
    productPurchaseEntity.setProductEntity(productEntity);
    productPurchaseRepository.save(productPurchaseEntity);
    orderRepository.save(orderEntity);
    URI location =
        ServletUriComponentsBuilder.fromUriString("/product-purchase")
            .path("/{ID}")
            .buildAndExpand(productPurchaseEntity.id)
            .toUri();
    return ResponseEntity.created(location).build();
  }

  @GetMapping("/product-purchase/{ID}")
  ProductPurchaseEntity getProductPurchase(@PathVariable Integer ID) {
    return productPurchaseRepository
        .findById(ID)
        .<EntityNotFoundInDBException>orElseThrow(
            () -> {
              throw new EntityNotFoundInDBException("Product not found.");
            });
  }

  @PostMapping("/product-purchase/{ID}/update-count")
  ResponseEntity<Object> updateCount(
      @PathVariable Integer ID, @Valid @RequestBody Integer newCount) {
    ProductPurchaseEntity productPurchaseEntity =
        productPurchaseRepository
            .findById(ID)
            .<EntityNotFoundInDBException>orElseThrow(
                () -> {
                  throw new EntityNotFoundInDBException("Product Purchase not found.");
                });
    if (productPurchaseEntity.getFinalised() == 0) {
      if (newCount <= 0) {
        productPurchaseRepository.deleteById(ID);
        URI location =
            ServletUriComponentsBuilder.fromUriString("/").path("").buildAndExpand().toUri();
        return ResponseEntity.created(location).build();
      }
      productPurchaseEntity.setCount(newCount);
      productPurchaseRepository.save(productPurchaseEntity);
    }
    URI location =
        ServletUriComponentsBuilder.fromUriString("/product-purchase")
            .path("/{ID}")
            .buildAndExpand(productPurchaseEntity.id)
            .toUri();
    return ResponseEntity.created(location).build();
  }
}
