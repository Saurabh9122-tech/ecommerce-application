package com.saurabh.ecommerce.repository;


import com.saurabh.ecommerce.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartItemRepository
        extends JpaRepository<CartItem, Long> {


    void deleteByProductId(Long productId);

}