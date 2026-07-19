package com.saurabh.ecommerce.controller;

import com.saurabh.ecommerce.entity.Product;
import com.saurabh.ecommerce.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    // GET ALL PRODUCTS
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // GET PRODUCT BY ID
    // UPDATE PRODUCT
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id,
                                 @RequestBody Product product) {

        Product existing = productService.getProductById(id);

        if (existing == null) {
            return null;
        }

        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setStock(product.getStock());
        existing.setCategory(product.getCategory());

        return productService.saveProduct(existing);
    }

    // ADD PRODUCT
    @PostMapping
    public Product addProduct(@RequestBody Product product) {

        return productService.saveProduct(product);

    }
}