package com.productions.ppt.commercebackend.app.category;

import com.productions.ppt.commercebackend.app.product.ProductEntity;
import com.productions.ppt.commercebackend.app.product.purchase.ProductPurchaseRepository;
import com.productions.ppt.commercebackend.app.product.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.Set;

@RestController
public class CategoryController {
  CategoryRepository categoryRepository;
  ProductRepository productRepository;
  CategoryController(
      CategoryRepository categoryRepository,
      ProductRepository productRepository,
      ProductPurchaseRepository productPurchaseRepository) {
    this.categoryRepository = categoryRepository;
    this.productRepository = productRepository;
  }

  @PostMapping("/category")
  ResponseEntity<Object> createController(@Valid @RequestBody CategoryEntity c) {
    categoryRepository.save(c);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest().path("/{ID}").buildAndExpand(c.id).toUri();
    return ResponseEntity.created(location).build();
  }

  @PostMapping("/category/{categoryID}/add/product/{productID}}")
  ResponseEntity<Object> addProductToCategory(
      @PathVariable Integer categoryID,
      @PathVariable Integer productID,
      @Valid @RequestBody Integer number) {
    Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(categoryID);
    Optional<ProductEntity> productEntityOptional = productRepository.findById(productID);

    if (!(categoryEntityOptional.isPresent() && productEntityOptional.isPresent())) {
      return null;
    }

    ProductEntity productEntity = productEntityOptional.get();
    CategoryEntity categoryEntity = categoryEntityOptional.get();

    categoryEntity.getProductEntities().add(productEntity);
    productEntity.getCategoryEntityList().add(categoryEntity);

    categoryRepository.save(categoryEntity);
    productRepository.save(productEntity);

    URI location =
        ServletUriComponentsBuilder.fromPath("/category/{categoryID}")
            .buildAndExpand(categoryEntity.id)
            .toUri();
    return ResponseEntity.created(location).build();
  }

  @GetMapping("/category/{ID}")
  CategoryEntity createController(@PathVariable Integer ID) {
    Optional<CategoryEntity> c = categoryRepository.findById(ID);
    return c.orElse(null);
  }

  @GetMapping("/category/{ID}/products")
  Set<ProductEntity> getPro(@PathVariable Integer ID) {
    Optional<CategoryEntity> c = categoryRepository.findById(ID);
    return c.map(CategoryEntity::getProductEntities).orElse(null);
  }
}
