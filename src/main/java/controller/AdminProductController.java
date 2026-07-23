package com.saurabh.ecommerce.controller;

import com.saurabh.ecommerce.entity.Product;
import com.saurabh.ecommerce.service.CategoryService;
import com.saurabh.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public AdminProductController(ProductService productService,
                                  CategoryService categoryService) {

        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String products(Model model) {

        model.addAttribute("products",
                productService.getAllProducts());

        return "admin/products";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {

        model.addAttribute("product", new Product());

        model.addAttribute("categories",
                categoryService.getAllCategories());

        return "admin/product-form";
    }

    @PostMapping("/save")
    public String saveProduct(
            @Valid @ModelAttribute Product product,
            BindingResult result,
            @RequestParam("image") MultipartFile image,
            Model model) {

        if (result.hasErrors()) {

            model.addAttribute("categories",
                    categoryService.getAllCategories());

            return "admin/product-form";
        }

        try {

            // Keep old image while editing
            if (product.getId() != null && image.isEmpty()) {

                Product oldProduct =
                        productService.getProductById(product.getId());

                if (oldProduct != null) {
                    product.setImageName(oldProduct.getImageName());
                }

            }

            // Upload new image
            if (!image.isEmpty()) {

                String uploadDir =
                        System.getProperty("user.dir") + "/uploads/products";

                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String fileName =
                        System.currentTimeMillis() + "_"
                                + image.getOriginalFilename();

                Files.copy(
                        image.getInputStream(),
                        uploadPath.resolve(fileName),
                        StandardCopyOption.REPLACE_EXISTING
                );

                product.setImageName(fileName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        productService.saveProduct(product);

        return "redirect:/admin/products";
    }
    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id,
                              Model model) {

        model.addAttribute("product",
                productService.getProductById(id));

        model.addAttribute("categories",
                categoryService.getAllCategories());

        return "admin/product-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);

        return "redirect:/admin/products";
    }

}