package com.rashu.ecommerce.service;

import com.rashu.ecommerce.dto.ProductRequest;
import com.rashu.ecommerce.dto.ProductResponse;
import com.rashu.ecommerce.entity.ProductEntity;
import com.rashu.ecommerce.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;

    //Using the Dto's instead of entity classes
    public List<ProductResponse> fetchActiveProducts(){
        return productRepo.findByActive(true)
                .stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }
    public List<ProductResponse> searchProducts(String keyword){
        return productRepo.searchByKeyword(keyword)
                .stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }
  public ProductResponse createProducts(ProductRequest productRequest){
      ProductEntity product = new ProductEntity();
      updateRequestToEntity(product,productRequest);
      ProductEntity savedProduct = productRepo.save(product);
      return mapEntityToResponse(savedProduct);
  }
  public ProductResponse updateProducts(Long id, ProductRequest productRequest){
        if(productRepo.existsById(id)){
            ProductEntity existingProduct = productRepo.findById(id)
                    .orElseThrow(()-> new RuntimeException("Product not Found"));
            updateRequestToEntity(existingProduct,productRequest);
            ProductEntity updatedProduct = productRepo.save(existingProduct);
            return mapEntityToResponse(updatedProduct);
        }
      return null;
  }
  public void deleteInactiveProducts(Long id){
       ProductEntity product = productRepo.findById(id)
               .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"product not found"));
       product.setActive(false);
       productRepo.delete(product);
  }

    public ProductResponse mapEntityToResponse(ProductEntity savedProduct) {
      ProductResponse response = new ProductResponse();
      response.setId(String.valueOf(savedProduct.getId()));
      response.setName(savedProduct.getName());
      response.setDescription(savedProduct.getDescription());
      response.setCategory(savedProduct.getCategory());
      response.setPrice(savedProduct.getPrice());
      response.setStockQuantity(savedProduct.getStockQuantity());
      response.setImageUrl(savedProduct.getImageUrl());
      response.setActive(savedProduct.isActive());
      return response;
    }

    public void updateRequestToEntity(ProductEntity product, ProductRequest productRequest) {
      product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setCategory(productRequest.getCategory());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setImageUrl(productRequest.getImageUrl());
    }
}
