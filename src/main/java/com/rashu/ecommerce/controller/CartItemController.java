package com.rashu.ecommerce.controller;

import com.rashu.ecommerce.dto.CartItemRequest;
import com.rashu.ecommerce.entity.CartItem;
import com.rashu.ecommerce.entity.UserEntity;
import com.rashu.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolvedModule;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartItemController {
    private final CartService cartService;
    @PostMapping("/addCartItems")
    public ResponseEntity<String> addItemsToCart(@RequestHeader("X-User-ID") String userId,
                                               @RequestBody CartItemRequest cartItemRequest){
        if (!cartService.addProductsToCart(userId,cartItemRequest)){
            return ResponseEntity.badRequest().body("Product out of stock or User not found or product not found ");
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/removeCartItems/{productId}")
    public ResponseEntity<String> removeItemsToCart(@RequestHeader("X-User-ID") String userId,
                                                    @PathVariable Long productId){
        if (!cartService.deleteItemsFromCart(userId,productId)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
