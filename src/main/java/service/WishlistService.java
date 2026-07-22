package com.saurabh.ecommerce.service;
import org.springframework.transaction.annotation.Transactional;
import com.saurabh.ecommerce.entity.Product;
import com.saurabh.ecommerce.entity.User;
import com.saurabh.ecommerce.entity.Wishlist;
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


    private User getCurrentUser(){

        Authentication auth =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        return userService.getUserByEmail(auth.getName());
    }

    public boolean isInWishlist(Long productId){

        return wishlistRepository
                .findByProductIdAndUser(productId, getCurrentUser())
                .isPresent();
    }
    public void addWishlist(Product product){

        User user = getCurrentUser();

        if(wishlistRepository
                .findByProductIdAndUser(product.getId(), user)
                .isEmpty()){

            Wishlist wishlist = new Wishlist();

            wishlist.setProduct(product);
            wishlist.setUser(user);

            wishlistRepository.save(wishlist);
        }
    }


    public List<Wishlist> getMyWishlist(){

        return wishlistRepository.findByUser(getCurrentUser());

    }

    @Transactional
    public void removeWishlist(Long productId) {

        wishlistRepository.deleteByProductIdAndUser(
                productId,
                getCurrentUser()
        );
    }

}