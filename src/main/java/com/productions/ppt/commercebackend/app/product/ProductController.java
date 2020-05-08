package com.productions.ppt.commercebackend.app.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @GetMapping("/{ID}")
    ProductEntity getProduct(@PathVariable Integer ID){
        return new ProductEntity(ID, "something");
    }
}
