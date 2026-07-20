package com.saurabh.ecommerce.controller;

import com.saurabh.ecommerce.entity.Product;
import com.saurabh.ecommerce.service.ProductService;
import org.springframework.web.bind.annotation.*;
import com.saurabh.ecommerce.dto.ProductDTO;
import java.util.stream.Collectors;
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
    public List<ProductDTO> getAllProducts() {

        return productService.getAllProducts()
                .stream()
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getStock()
                ))
                .collect(Collectors.toList());
    }

    // GET PRODUCT BY ID
    // UPDATE PRODUCT
    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable Long id) {

        Product product = productService.getProductById(id);

        if (product == null) {
            return null;
        }

        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }

    // DELETE PRODUCT
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {

        Product product = productService.getProductById(id);

        if (product == null) {
            return "Product Not Found";
        }

        productService.deleteProduct(id);

        return "Product Deleted Successfully";
    }
    // ADD PRODUCT
    @PostMapping
    public Product addProduct(@RequestBody Product product) {

        return productService.saveProduct(product);

    }
}