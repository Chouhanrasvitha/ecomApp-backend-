package com.rashu.ecommerce.repository;

import com.rashu.ecommerce.entity.CartItem;
import com.rashu.ecommerce.entity.ProductEntity;
import com.rashu.ecommerce.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Long> {

    CartItem findByUserAndProduct(UserEntity user, ProductEntity product);

    void deleteByUserAndProduct(UserEntity user, ProductEntity product);
}

