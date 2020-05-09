package com.productions.ppt.commercebackend.app.product;

import com.productions.ppt.commercebackend.app.category.CategoryEntity;
import com.productions.ppt.commercebackend.app.category.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.Set;

@RestController
public class ProductController {
  ProductRepository productRepository;
  CategoryRepository categoryRepository;

  public ProductController(
      ProductRepository productRepository, CategoryRepository categoryRepository) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
  }

  @GetMapping("/product/{ID}/categories")
  Set<CategoryEntity> getCategories(@PathVariable Integer ID) {
    Optional<ProductEntity> p1 = productRepository.findById(ID);
    return p1.map(ProductEntity::getCategoryEntityList).orElse(null);
  }

  @PostMapping("/product")
  ResponseEntity<Object> createProduct(@Valid @RequestBody ProductEntity productEntity) {
    productRepository.save(productEntity);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{ID}")
            .buildAndExpand(productEntity.id)
            .toUri();
    return ResponseEntity.created(location).build();
  }

  @GetMapping("/product/{ID}")
  ProductEntity getProduct(@PathVariable Integer ID) {
    return productRepository.findById(ID).orElse(null);
  }
}
