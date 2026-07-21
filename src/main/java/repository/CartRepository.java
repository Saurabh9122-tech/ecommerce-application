package com.saurabh.ecommerce.repository;

import com.saurabh.ecommerce.entity.CartItem;
import com.saurabh.ecommerce.entity.Product;
import com.saurabh.ecommerce.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUser(User user);

    Optional<CartItem> findByProductAndUser(Product product, User user);

    @Transactional
    @Modifying
    void deleteByUser(User user);
}