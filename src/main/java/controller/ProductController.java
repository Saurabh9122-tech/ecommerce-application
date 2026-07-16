package com.saurabh.ecommerce.controller;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import com.saurabh.ecommerce.entity.Product;
import com.saurabh.ecommerce.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String viewProducts(
            @RequestParam(required = false) String keyword,
            Model model) {

        model.addAttribute("products",
                productService.searchProducts(keyword));

        model.addAttribute("keyword", keyword);

        return "index";
    }
    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {

        Product product = productService.getProductById(id);

        model.addAttribute("product", product);

        return "product-details";
    }
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    @PostMapping("/save")
    public String saveProduct(
            @Valid @ModelAttribute Product product,
            BindingResult result,
            @RequestParam("image") MultipartFile image,
            Model model) {

        if(result.hasErrors()){
            return "product-form";
        }

        // Existing image upload code

        productService.saveProduct(product);

        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "product-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}