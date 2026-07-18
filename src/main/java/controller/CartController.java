package com.saurabh.ecommerce.controller;

import com.saurabh.ecommerce.entity.Product;
import com.saurabh.ecommerce.service.CartService;
import com.saurabh.ecommerce.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService,
                          ProductService productService) {

        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping
    public String viewCart(Model model) {

        model.addAttribute("cartItems", cartService.getAllItems());
        model.addAttribute("grandTotal", cartService.getGrandTotal());

        return "cart";
    }

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable Long id) {

        Product product = productService.getProductById(id);

        cartService.addToCart(product);

        return "redirect:/cart";
    }

    @GetMapping("/remove/{id}")
    public String removeItem(@PathVariable Long id) {

        cartService.removeItem(id);

        return "redirect:/cart";
    }
    @GetMapping("/increase/{id}")
    public String increase(@PathVariable Long id){

        cartService.increaseQuantity(id);

        return "redirect:/cart";

    }

    @GetMapping("/decrease/{id}")
    public String decrease(@PathVariable Long id){

        cartService.decreaseQuantity(id);

        return "redirect:/cart";

    }
}