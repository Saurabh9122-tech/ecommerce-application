package com.saurabh.ecommerce.controller;

import com.saurabh.ecommerce.entity.Product;
import com.saurabh.ecommerce.service.ProductService;
import com.saurabh.ecommerce.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;
    private final ProductService productService;

    public WishlistController(WishlistService wishlistService,
                              ProductService productService) {

        this.wishlistService = wishlistService;
        this.productService = productService;
    }

    @GetMapping
    public String wishlist(Model model){

        model.addAttribute("items",
                wishlistService.getWishlist());

        return "wishlist";
    }

    @GetMapping("/add/{id}")
    public String add(@PathVariable Long id){

        Product product=
                productService.getProductById(id);

        wishlistService.add(product);

        return "redirect:/wishlist";
    }

}