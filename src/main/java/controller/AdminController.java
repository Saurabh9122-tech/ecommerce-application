package com.saurabh.ecommerce.controller;

import com.saurabh.ecommerce.service.CategoryService;
import com.saurabh.ecommerce.service.OrderService;
import com.saurabh.ecommerce.service.ProductService;
import com.saurabh.ecommerce.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final OrderService orderService;
    private final UserService userService;

    public AdminController(ProductService productService,
                           CategoryService categoryService,
                           OrderService orderService,
                           UserService userService) {

        this.productService = productService;
        this.categoryService = categoryService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String dashboard(Model model) {

        model.addAttribute("productCount", productService.getProductCount());
        model.addAttribute("categoryCount", categoryService.getCategoryCount());
        model.addAttribute("orderCount", orderService.getOrderCount());
        model.addAttribute("userCount", userService.getUserCount());
        model.addAttribute("revenue", orderService.getRevenue());

        return "dashboard";
    }
}