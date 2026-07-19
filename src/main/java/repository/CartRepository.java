package com.saurabh.ecommerce.repository;

import com.saurabh.ecommerce.entity.CartItem;
import com.saurabh.ecommerce.entity.Product;
import com.saurabh.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByProductAndUser(Product product, User user);

    List<CartItem> findByUser(User user);

    void deleteByUser(User user);

}