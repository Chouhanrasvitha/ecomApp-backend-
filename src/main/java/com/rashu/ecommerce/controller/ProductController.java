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


    @GetMapping("/product-info")
    public ResponseEntity<List<ProductResponse>> getAllActiveProducts() {
        return new ResponseEntity<>(productService.fetchActiveProducts(), HttpStatus.FOUND);
    }

    @GetMapping("/product-info/search")
    public ResponseEntity<List<ProductResponse>> getSearchProducts(@RequestParam("keyword") String keyword) {
        return new ResponseEntity<>(productService.searchProducts(keyword), HttpStatus.FOUND);
    }

    @PostMapping("/addProduct-info")
    public ResponseEntity<ProductResponse> addProducts(@RequestBody ProductRequest productRequest) {
        return new ResponseEntity<>(productService.createProducts(productRequest), HttpStatus.CREATED);
    }

    @PutMapping("/updateProduct-info/{id}")
    public ResponseEntity<ProductResponse> modifiedProducts(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.updateProducts(id, productRequest);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String > removeProducts(@PathVariable Long id) {
       productService.deleteInactiveProducts(id);
       return new ResponseEntity<>("product deleted successfully",HttpStatus.NO_CONTENT);
    }
}

