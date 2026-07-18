package com.saurabh.ecommerce.controller;

import com.saurabh.ecommerce.entity.Category;
import com.saurabh.ecommerce.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listCategories(Model model) {

        model.addAttribute("categories",
                categoryService.getAllCategories());

        return "category-list";
    }

    @GetMapping("/new")
    public String showForm(Model model) {

        model.addAttribute("category", new Category());

        return "category-form";
    }

    @PostMapping("/save")
    public String saveCategory(@ModelAttribute Category category) {

        categoryService.saveCategory(category);

        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id,
                               Model model) {

        model.addAttribute("category",
                categoryService.getCategoryById(id));

        return "category-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {

        categoryService.deleteCategory(id);

        return "redirect:/categories";
    }

}