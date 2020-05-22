package com.productions.ppt.commercebackend.app.product;

import com.productions.ppt.commercebackend.app.category.CategoryEntity;
import com.productions.ppt.commercebackend.app.category.CategoryService;
import com.productions.ppt.commercebackend.exceptions.EntityNotFoundInDBException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@RestController
class ProductController {
  ProductRepository productRepository;
  CategoryService categoryService;

  public ProductController(
      ProductRepository productRepository, CategoryService categoryService) {
    this.productRepository = productRepository;
    this.categoryService = categoryService;
  }

  @GetMapping("/products/{ID}/categories")
  Set<CategoryEntity> getCategories(@PathVariable Integer ID) {
    Optional<ProductEntity> p1 = productRepository.findById(ID);
    return p1.map(ProductEntity::getCategoryEntityList)
        .<EntityNotFoundInDBException>orElseThrow(
            () -> {
              throw new EntityNotFoundInDBException("Product not found.");
            });
  }

  @PostMapping("/products")
  ResponseEntity<Object> createProduct(@Valid @RequestBody ProductEntity productEntity) {
    productRepository.save(productEntity);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{ID}")
            .buildAndExpand(productEntity.getId())
            .toUri();
    return ResponseEntity.created(location).build();
  }

  //  TODO make the CORS more specific for all methods
  @CrossOrigin()
  @GetMapping("/products/{ID}")
  ProductEntity getProduct(@PathVariable Integer ID) {
    return productRepository
        .findById(ID)
        .<EntityNotFoundInDBException>orElseThrow(
            () -> {
              throw new EntityNotFoundInDBException("Product not found.");
            });
  }

  @CrossOrigin()
  @GetMapping("/products/discover")
  Stream<ProductEntity> getDiscoveryProducts() {
    //    TODO add error if there are not enough products
    return productRepository
        .findAll(PageRequest.of(0, 3, Sort.by("numberOfPurchases").descending()))
        .get();
  }
}
