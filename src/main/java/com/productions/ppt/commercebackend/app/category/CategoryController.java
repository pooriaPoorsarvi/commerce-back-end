package com.productions.ppt.commercebackend.app.category;

import com.productions.ppt.commercebackend.app.product.ProductEntity;
import com.productions.ppt.commercebackend.app.product.ProductService;
import com.productions.ppt.commercebackend.exceptions.EntityNotFoundInDBException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
class CategoryController {
  CategoryRepository categoryRepository;
  ProductService productService;

  CategoryController(
      CategoryRepository categoryRepository,
      ProductService productService) {
    this.categoryRepository = categoryRepository;
    this.productService = productService;
  }

  @PostMapping("/categories")
  ResponseEntity<Object> createController(@Valid @RequestBody CategoryEntity c) {
    categoryRepository.save(c);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest().path("/{ID}").buildAndExpand(c.getId()).toUri();
    return ResponseEntity.created(location).build();
  }

  @PostMapping("/categories/{categoryID}/add/product/{productID}}")
  ResponseEntity<Object> addProductToCategory(
      @PathVariable Integer categoryID, @PathVariable Integer productID) {
    Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(categoryID);
    Optional<ProductEntity> productEntityOptional = productService.findById(productID);

    if (categoryEntityOptional.isPresent()) {
      throw new EntityNotFoundInDBException("Category not found.");
    }

    if (productEntityOptional.isPresent()) {
      throw new EntityNotFoundInDBException("Product not found.");
    }

    ProductEntity productEntity = productEntityOptional.get();
    CategoryEntity categoryEntity = categoryEntityOptional.get();

    categoryEntity.getProductEntities().add(productEntity);
    productEntity.getCategoryEntityList().add(categoryEntity);

    categoryRepository.save(categoryEntity);
    productService.save(productEntity);

    URI location =
        ServletUriComponentsBuilder.fromPath("/category/{categoryID}")
            .buildAndExpand(categoryEntity.getId())
            .toUri();
    return ResponseEntity.created(location).build();
  }

  @GetMapping("/categories/{ID}")
  CategoryEntity createController(@PathVariable Integer ID) {
    Optional<CategoryEntity> c = categoryRepository.findById(ID);
    return c.<EntityNotFoundInDBException>orElseThrow(
        () -> {
          throw new EntityNotFoundInDBException("Category not found.");
        });
  }

  @CrossOrigin()
  @GetMapping("/categories/{ID}/products")
  Set<ProductEntity> getPro(@PathVariable Integer ID) {
    Optional<CategoryEntity> c = categoryRepository.findById(ID);
    return c.map(CategoryEntity::getProductEntities)
        .<EntityNotFoundInDBException>orElseThrow(
            () -> {
              throw new EntityNotFoundInDBException("Category not found.");
            });
  }

  @CrossOrigin()
  @GetMapping("/categories")
  List<CategoryEntity> getAllCategories(){
    return categoryRepository.findAll();
  }

}
