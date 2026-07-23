package com.saurabh.ecommerce.repository;

import com.saurabh.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // User Order History
    List<Order> findByEmailOrderByOrderDateDesc(String email);

    // Dashboard Revenue
    @Query("SELECT SUM(o.totalAmount) FROM Order o")
    Double getTotalRevenue();

    // Delete all orders of a product
    @Modifying
    @Query("DELETE FROM Order o WHERE o.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);

}