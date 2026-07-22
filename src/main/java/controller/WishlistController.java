package com.saurabh.ecommerce.controller;
import org.springframework.ui.Model;
import com.saurabh.ecommerce.entity.Product;
import com.saurabh.ecommerce.service.ProductService;
import com.saurabh.ecommerce.service.WishlistService;
import org.springframework.stereotype.Controller;
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


    @GetMapping("/add/{id}")
    public String addWishlist(@PathVariable Long id) {

        Product product =
                productService.getProductById(id);

        wishlistService.addWishlist(product);


        return "redirect:/products";
    }



    @GetMapping("/remove/{id}")
    public String removeWishlist(@PathVariable Long id) {

        wishlistService.removeWishlist(id);

        return "redirect:/products";
    }



    @GetMapping
    public String wishlistPage(Model model) {

        model.addAttribute(
                "items",
                wishlistService.getMyWishlist()
        );

        return "wishlist";
    }
}