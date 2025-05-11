package com.rashu.ecommerce.controller;

import com.rashu.ecommerce.entity.ProductEntity;
import com.rashu.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping("/product-info/{id}")
    public ResponseEntity<ProductEntity> getProduct(@PathVariable Long id){
        ProductEntity product = productService.fetchProduct(id);
        return ResponseEntity.ok(product);
    }
    @GetMapping("/product-info")
    public ResponseEntity<List<ProductEntity>> getProducts(){
        List<ProductEntity> productList = productService.fetchAllProducts();
        return ResponseEntity.ok(productList);
    }
    @PostMapping("/addProduct-info")
    public ResponseEntity<String> addProducts(@RequestBody ProductEntity productEntity){
            productService.createProduct(productEntity);
            return ResponseEntity.ok("created products successfully");
    }
    @PutMapping("/updateProduct-info")
    public void updateProduct(@PathVariable Long id, @RequestBody ProductEntity productEntity){

    }
    @DeleteMapping("/delete")
    public void deleteProduct(){

    }
}
