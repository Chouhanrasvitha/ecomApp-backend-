package com.rashu.ecommerce.controller;

import com.rashu.ecommerce.dto.ProductRequest;
import com.rashu.ecommerce.dto.ProductResponse;
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
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id){
        ProductResponse productResponse= productService.fetchProduct(id);
    return ResponseEntity.ok(productResponse);
    }
    @GetMapping("/product-info")
    public ResponseEntity<List<ProductResponse>> getProducts() {
        List<ProductResponse> productResponse= productService.fetchAllProducts();
        return ResponseEntity.ok(productResponse);
    }
    @PostMapping("/addProduct-info")
    public ResponseEntity<String> addProducts(@RequestBody ProductRequest productRequest){
            productService.createProduct(productRequest);
            return ResponseEntity.ok("created products successfully");
    }
    @PutMapping("/updateProduct-info/{id}")
    public ResponseEntity<ProductRequest> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest){
        if (id==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
       return ResponseEntity.ok(productService.updateProducts(id,productRequest));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("deleted successfully");
    }
}
