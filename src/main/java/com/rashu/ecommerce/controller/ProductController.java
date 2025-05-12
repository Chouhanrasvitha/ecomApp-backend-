package com.rashu.ecommerce.controller;

import com.rashu.ecommerce.entity.ProductEntity;
import com.rashu.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @PutMapping("/updateProduct-info/{id}")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable Long id, @RequestBody ProductEntity productEntity){
        if (id==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
       return ResponseEntity.ok(productService.updateProducts(id,productEntity));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("deleted successfully");
    }
}
