package com.productions.ppt.commercebackend.app.category;

import com.productions.ppt.commercebackend.app.product.ProductEntity;
import com.productions.ppt.commercebackend.app.product.ProductService;
import com.productions.ppt.commercebackend.exceptions.BusinessErrorException;
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

  CategoryController(CategoryRepository categoryRepository, ProductService productService) {
    this.categoryRepository = categoryRepository;
    this.productService = productService;
  }

  @CrossOrigin()
  @PostMapping("/categories")
  ResponseEntity<Object> createController(@Valid @RequestBody CategoryEntity c) {
    //  TODO do a check on all of the instances that you have had to use the setId(0)
    //    c.setId(0);
    System.out.println(c.getId());
    categoryRepository.save(c);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{ID}")
            .buildAndExpand(c.getId())
            .toUri();
    return ResponseEntity.created(location).build();
  }

  @CrossOrigin()
  @PostMapping("/categories/{categoryID}/add/product/{productID}")
  ResponseEntity<Object> addProductToCategory(
      @PathVariable Integer categoryID, @PathVariable Integer productID) {
    Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(categoryID);
    Optional<ProductEntity> productEntityOptional = productService.findById(productID);
    System.out.println(categoryID);
    System.out.println(productID);
    if (!categoryEntityOptional.isPresent()) {
      throw new EntityNotFoundInDBException("Category not found.");
    }

    if (!productEntityOptional.isPresent()) {
      throw new EntityNotFoundInDBException("Product not found.");
    }

    ProductEntity productEntity = productEntityOptional.get();
    CategoryEntity categoryEntity = categoryEntityOptional.get();

    categoryEntity.getProductEntities().add(productEntity);
    productEntity.getCategoryEntityList().add(categoryEntity);

    categoryRepository.save(categoryEntity);
    productService.save(productEntity);
    categoryRepository.flush();
    productService.flush();

    URI location =
        ServletUriComponentsBuilder.fromPath("/category/{categoryID}")
            .buildAndExpand(categoryEntity.getId())
            .toUri();
    return ResponseEntity.created(location).build();
  }

  @CrossOrigin()
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
  @DeleteMapping("/categories/{ID}")
  void deleteCategory(@PathVariable Integer ID) {
    CategoryEntity c =
        categoryRepository
            .findById(ID)
            .<BusinessErrorException>orElseThrow(
                () -> {
                  throw new BusinessErrorException("Category not found");
                });
    categoryRepository.delete(c);
    categoryRepository.flush();
  }

  @CrossOrigin()
  @DeleteMapping("/categories/{categoryID}/remove/product/{productID}")
  void deleteCategory(@PathVariable Integer categoryID, @PathVariable Integer productID) {
    CategoryEntity c =
            categoryRepository
                    .findById(categoryID)
                    .<BusinessErrorException>orElseThrow(
                            () -> {
                              throw new BusinessErrorException("Category not found");
                            });
    Set<ProductEntity> prs = c.getProductEntities();
    for (ProductEntity p : prs) {
        if (p.getId().equals(productID)){
          c.getProductEntities().remove(p);
          p.getCategoryEntityList().remove(c);
          categoryRepository.save(c);
          productService.save(p);
          categoryRepository.flush();
          productService.flush();
          return;
        }
    }
    throw new BusinessErrorException("Product ID not found inside the category");
  }

  @CrossOrigin()
  @GetMapping("/categories")
  List<CategoryEntity> getAllCategories() {
    return categoryRepository.findAll();
  }
}
