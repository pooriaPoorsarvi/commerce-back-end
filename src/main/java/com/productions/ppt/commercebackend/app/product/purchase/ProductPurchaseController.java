package com.productions.ppt.commercebackend.app.product.purchase;

import com.productions.ppt.commercebackend.app.order.OrderEntity;
import com.productions.ppt.commercebackend.app.order.OrderRepository;
import com.productions.ppt.commercebackend.app.product.ProductEntity;
import com.productions.ppt.commercebackend.app.product.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
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

  @PostMapping("/product-purchase/{productId}/{orderId}")
  ResponseEntity<Object> createProductPurchase(
      @Valid @RequestBody Integer number,
      @PathVariable Integer productId,
      @PathVariable Integer orderId) {
    ProductEntity productEntity = productRepository.findById(productId).orElse(null);
    OrderEntity orderEntity = orderRepository.findById(orderId).orElse(null);
    if (productEntity == null || orderEntity == null) {
      throw new RuntimeException("product or order does not exist");
    }
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
    ProductPurchaseEntity productPurchaseEntity =
        productPurchaseRepository.findById(ID).orElse(null);
    if (productPurchaseEntity == null) {
      throw new RuntimeException("product does not exist");
    }
    return productPurchaseEntity;
  }

  @PostMapping("/product-purchase/{ID}/update-count")
  ResponseEntity<Object> updateCount(
      @PathVariable Integer ID, @Valid @RequestBody Integer newCount) {
    ProductPurchaseEntity productPurchaseEntity =
        productPurchaseRepository.findById(ID).orElse(null);
    if (productPurchaseEntity == null) {
      throw new RuntimeException("product does not exist");
    }
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
