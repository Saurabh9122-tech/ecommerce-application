package com.saurabh.ecommerce.repository;

import com.saurabh.ecommerce.entity.CartItem;
import com.saurabh.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByProduct(Product product);

}