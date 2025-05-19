package com.rashu.ecommerce.repository;

import com.rashu.ecommerce.dto.ProductResponse;
import com.rashu.ecommerce.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity,Long> {
    List<ProductEntity> findByActive(boolean active);
    @Query("SELECT p FROM products p WHERE LOWER(p.name)" +
            " LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))"
    + "OR LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))"
            )
    List<ProductEntity> searchByKeyword(@Param("keyword") String keyword);


}
