package com.rashu.ecommerce.service;

import com.rashu.ecommerce.dto.CartItemRequest;
import com.rashu.ecommerce.entity.CartItem;
import com.rashu.ecommerce.entity.ProductEntity;
import com.rashu.ecommerce.entity.UserEntity;
import com.rashu.ecommerce.repository.CartItemRepo;
import com.rashu.ecommerce.repository.ProductRepo;
import com.rashu.ecommerce.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final CartItemRepo cartItemRepo;

    public boolean addProductsToCart(String userId, CartItemRequest request) {
        Optional<UserEntity> userOpt = userRepo.findById(Long.valueOf(userId));
        if (userOpt.isEmpty())
            return false;
        UserEntity user = userOpt.get();
        Optional<ProductEntity> productOpt = productRepo.findById(request.getProductId());
        if (productOpt.isEmpty())
            return false;
        ProductEntity product = productOpt.get();
        if (product.getStockQuantity()< request.getQuantity())
            return false;
        CartItem existingCartItem = cartItemRepo.findByUserAndProduct(user,product);
            if (existingCartItem!=null){
                existingCartItem.setQuantity(existingCartItem.getQuantity()+ request.getQuantity());
                existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
                cartItemRepo.save(existingCartItem);
            }
            else {
                CartItem cartItem = new CartItem();
                cartItem.setUser(user);
                cartItem.setProduct(product);
                cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
                cartItem.setQuantity(request.getQuantity());
                cartItemRepo.save(cartItem);
            }
        return true;
    }

    public boolean deleteItemsFromCart(String userId, Long productId){
        Optional<UserEntity> userOpt = userRepo.findById(Long.valueOf(userId));
        if (userOpt.isEmpty())
            return false;
        UserEntity user = userOpt.get();
        Optional<ProductEntity> productOpt = productRepo.findById(productId);
        if (productOpt.isEmpty())
            return false;
        ProductEntity product = productOpt.get();
        cartItemRepo.deleteByUserAndProduct(user,product);
        return true;

    }
}