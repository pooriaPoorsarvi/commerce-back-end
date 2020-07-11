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
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
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

  @GetMapping("/products/admin")
  String adminOnly() {
    return "admin only";
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

//  TODO update the origin for these cross origins
  @CrossOrigin()
  @PostMapping("/products")
  ResponseEntity<Object> createProduct(@Valid @RequestBody ProductEntity productEntity) {
//    TODO set a DTO so that you can filter out id later and you don't have to do this each time manually
    productEntity.setId(0);
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


//  TODO transform this into get mapping
  @CrossOrigin()
  @PostMapping("/products/search")
  List<ProductEntity> search(@RequestBody @Valid @NotNull String searchExpression){
    System.out.println(searchExpression);
    return productRepository.findBySearch(searchExpression);
  }

}
