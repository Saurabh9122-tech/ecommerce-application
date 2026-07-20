package com.saurabh.ecommerce.controller;

import com.saurabh.ecommerce.service.CategoryService;
import com.saurabh.ecommerce.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public HomeController(ProductService productService,
                          CategoryService categoryService) {

        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/home")
    public String home(Model model) {

        model.addAttribute("products",
                productService.getProductsByPage(0).getContent());

        model.addAttribute("categories",
                categoryService.getAllCategories());

        return "home";
    }

}