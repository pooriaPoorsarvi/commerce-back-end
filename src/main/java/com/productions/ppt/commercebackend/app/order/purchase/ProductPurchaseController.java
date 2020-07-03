package com.productions.ppt.commercebackend.app.order.purchase;

import com.productions.ppt.commercebackend.app.order.OrderEntity;
import com.productions.ppt.commercebackend.app.order.OrderService;
import com.productions.ppt.commercebackend.app.product.ProductEntity;
import com.productions.ppt.commercebackend.app.product.ProductService;
import com.productions.ppt.commercebackend.exceptions.BusinessErrorException;
import com.productions.ppt.commercebackend.exceptions.EntityNotFoundInDBException;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
class ProductPurchaseController {

  ProductPurchaseRepository productPurchaseRepository;
  ProductService productService;
  OrderService orderService;

  public ProductPurchaseController(
      ProductPurchaseRepository productPurchaseRepository,
      ProductService productService,
      OrderService orderService) {
    this.productPurchaseRepository = productPurchaseRepository;
    this.productService = productService;
    this.orderService = orderService;
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
            .buildAndExpand(productPurchaseEntity.getId())
            .toUri();
    return ResponseEntity.created(location).build();
  }
}
