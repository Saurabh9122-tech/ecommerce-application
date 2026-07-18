package com.saurabh.ecommerce.controller;
import org.springframework.data.domain.Page;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import com.saurabh.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import com.saurabh.ecommerce.entity.Product;
import com.saurabh.ecommerce.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.saurabh.ecommerce.service.CartService;
@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final CartService cartService;
    public ProductController(ProductService productService,
                             CategoryService categoryService,
                             CartService cartService) {
        this.cartService = cartService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String viewProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long category,
            @RequestParam(required = false) String sort,
            Model model) {

        // Search
        if (keyword != null && !keyword.trim().isEmpty()) {

            model.addAttribute("products",
                    productService.searchProducts(keyword));

        }

        // Category Filter
        else if (category != null) {

            model.addAttribute("products",
                    productService.getProductsByCategory(category));

        }

        // Sorting
        else if (sort != null && !sort.isEmpty()) {

            model.addAttribute("products",
                    productService.getSortedProducts(sort));

        }

        // Pagination
        else {

            Page<Product> productPage =
                    productService.getProductsByPage(page);

            model.addAttribute("products",
                    productPage.getContent());

            model.addAttribute("currentPage", page);

            model.addAttribute("totalPages",
                    productPage.getTotalPages());
        }

        model.addAttribute("categories",
                categoryService.getAllCategories());

        model.addAttribute("keyword", keyword);

        model.addAttribute("selectedCategory", category);

        model.addAttribute("selectedSort", sort);

        model.addAttribute("cartCount",
                cartService.getCartCount());

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
        model.addAttribute("categories",
                categoryService.getAllCategories());
        return "product-form";
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

            return "product-form";
        }

        try {

            if (!image.isEmpty()) {

                String uploadDir = System.getProperty("user.dir") + "/uploads/products/";
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
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

        return "redirect:/products";
    }
    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {

        Product product = productService.getProductById(id);

        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());

        return "product-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}