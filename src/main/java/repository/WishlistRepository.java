package com.saurabh.ecommerce.repository;

import com.saurabh.ecommerce.entity.User;
import com.saurabh.ecommerce.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface WishlistRepository
        extends JpaRepository<Wishlist, Long> {


    List<Wishlist> findByUser(User user);


    Optional<Wishlist> findByProductIdAndUser(
            Long productId,
            User user
    );


    @Modifying
    @Transactional
    void deleteByProductIdAndUser(
            Long productId,
            User user
    );

}