package com.rashu.ecommerce.service;

import com.rashu.ecommerce.dto.ProductRequest;
import com.rashu.ecommerce.dto.ProductResponse;
import com.rashu.ecommerce.entity.ProductEntity;
import com.rashu.ecommerce.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    public ProductResponse fetchProduct(Long id){
        return productRepo.findById(id)
                        .map(this::mapToProductResponse)
                .orElse(null);
    }
    public List<ProductResponse> fetchAllProducts(){
        return  productRepo.findAll()
                .stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }
    public void createProduct(ProductRequest productRequest){
        ProductEntity product = new ProductEntity();
        updatedToRequest(product, productRequest);
        productRepo.save(product);
    }


    public ProductRequest updateProducts(Long id,ProductRequest productRequest){
        if (id!= null){
                ProductEntity existingProduct= productRepo.findById(id)
                        .orElseThrow(()-> new RuntimeException("Entered invalid id"));
                existingProduct.setName(productRequest.getName());
                existingProduct.setDescription(productRequest.getDescription());
                existingProduct.setPrice(productRequest.getPrice());
                existingProduct.setStockQuantity(productRequest.getStockQuantity());
                existingProduct.setCategory(productRequest.getCategory());
                existingProduct.setImageUrl(productRequest.getImageUrl());
                productRepo.save(existingProduct);
        }
        return productRequest;
    }
    public void deleteProduct(Long id) {
        if (id != null) {
            productRepo.deleteById(id);
        }
    }
    public ProductResponse mapToProductResponse(ProductEntity savedProduct){
        ProductResponse response = new ProductResponse();
        response.setId(String.valueOf(savedProduct.getId()));
        response.setName(savedProduct.getName());
        response.setDescription(savedProduct.getDescription());
        response.setCategory(savedProduct.getCategory());
        response.setPrice(savedProduct.getPrice());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setStockQuantity(savedProduct.getStockQuantity());
        return response;
    }
    private void updatedToRequest(ProductEntity product,ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setImageUrl(productRequest.getImageUrl());
    }
}
