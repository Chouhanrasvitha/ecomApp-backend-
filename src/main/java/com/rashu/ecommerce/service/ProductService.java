package com.rashu.ecommerce.service;

import com.rashu.ecommerce.entity.ProductEntity;
import com.rashu.ecommerce.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    public ProductEntity fetchProduct(Long id){
        return productRepo.findById(id).orElse(null);
    }
    public List<ProductEntity> fetchAllProducts(){
        return productRepo.findAll();
    }
    public void createProduct(ProductEntity productEntity){
        productRepo.save(productEntity);
    }
    public ProductEntity updateProducts(Long id,ProductEntity productEntity){
        if (id!= null){
                ProductEntity existingProduct= productRepo.findById(id)
                        .orElseThrow(()-> new RuntimeException("Entered invalid id"));
                existingProduct.setName(productEntity.getName());
                existingProduct.setDescription(productEntity.getDescription());
                existingProduct.setPrice(productEntity.getPrice());
                existingProduct.setStockQuantity(productEntity.getStockQuantity());
                existingProduct.setCategory(productEntity.getCategory());
                existingProduct.setImageUrl(productEntity.getImageUrl());
                productRepo.save(existingProduct);
        }
        return productEntity;
    }
    public void deleteProduct(Long id) {
        if (id != null) {
            productRepo.deleteById(id);
        }
    }
}
