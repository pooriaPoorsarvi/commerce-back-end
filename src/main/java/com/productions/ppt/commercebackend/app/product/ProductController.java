package com.productions.ppt.commercebackend.app.product;

import com.productions.ppt.commercebackend.app.category.CategoryEntity;
import com.productions.ppt.commercebackend.app.category.CategoryService;
import com.productions.ppt.commercebackend.exceptions.BusinessErrorException;
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

  public ProductController(ProductRepository productRepository, CategoryService categoryService) {
    this.productRepository = productRepository;
    this.categoryService = categoryService;
  }

  @GetMapping("/products/admin")
  String adminOnly() {
    return "admin only";
  }

  @CrossOrigin()
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
  //  TODO create a put option separately in the future
  @CrossOrigin()
  @PostMapping("/products")
  Integer createProduct(@Valid @RequestBody ProductEntity productEntity) {
    //    TODO set a DTO so that you can filter out id later and you don't have to do this each time
    // manually
    Integer id = productEntity.getId();
    if (id != null && id != 0) {
      //      TODO check so that only the fields that are not being sent are being kept constant,
      //        also make it so that this
      //          this is done for all the fields automatically
      ProductEntity p =
          productRepository
              .findById(id)
              .<BusinessErrorException>orElseThrow(
                  () -> {
                    throw new BusinessErrorException("Bad product ID");
                  });
      productEntity.setCategoryEntityList(p.getCategoryEntityList());
      productEntity.setNumberOfPurchases(p.getNumberOfPurchases());
    }
    //    productEntity.setId(0);
    productRepository.save(productEntity);
    productRepository.flush();
    //    TODO make angular use Response Entity
    //    URI location =
    //        ServletUriComponentsBuilder.fromCurrentRequest()
    //            .path("/{ID}")
    //            .buildAndExpand(productEntity.getId())
    //            .toUri();
    //    return ResponseEntity.created(location).build();
    return productEntity.getId();
  }

  @CrossOrigin()
  @DeleteMapping("/products/{ID}")
  void deleteProduct(@PathVariable Integer ID) {
    ProductEntity productEntity =
        productRepository
            .findById(ID)
            .<BusinessErrorException>orElseThrow(
                () -> {
                  throw new BusinessErrorException("Product not found");
                });
    productRepository.delete(productEntity);
    productRepository.flush();
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

  //  TODO you have to make this into a paginated function in the future as the number of products
  // grow
  @CrossOrigin()
  @GetMapping("/products/all")
  List<ProductEntity> getAllProducts() {
    //    TODO add error if there are not enough products
    return productRepository.findAll();
  }

  //  TODO transform this into get mapping
  @CrossOrigin()
  @PostMapping("/products/search")
  List<ProductEntity> search(@RequestBody @Valid @NotNull String searchExpression) {
    System.out.println(searchExpression);
    return productRepository.findBySearch(searchExpression);
  }
}
