package com.saurabh.ecommerce.service;

import com.saurabh.ecommerce.entity.*;
import com.saurabh.ecommerce.repository.WishlistRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserService userService;

    public WishlistService(WishlistRepository wishlistRepository,
                           UserService userService) {

        this.wishlistRepository = wishlistRepository;
        this.userService = userService;
    }

    private User currentUser(){

        Authentication auth =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        return userService.getUserByEmail(auth.getName());
    }

    public void add(Product product){

        User user=currentUser();

        if(wishlistRepository
                .findByUserAndProduct(user,product)
                .isEmpty()){

            Wishlist w=new Wishlist();

            w.setUser(user);

            w.setProduct(product);

            wishlistRepository.save(w);

        }

    }

    public List<Wishlist> getWishlist(){

        return wishlistRepository.findByUser(currentUser());

    }

}