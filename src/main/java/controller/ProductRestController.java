package com.saurabh.ecommerce.controller;

import com.saurabh.ecommerce.dto.ProductDTO;
import com.saurabh.ecommerce.entity.Product;
import com.saurabh.ecommerce.exception.ProductNotFoundException;
import com.saurabh.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable Long id) {

        Product product = productService.getProductById(id);

        if (product == null) {
            throw new ProductNotFoundException(id);
        }

        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }

    // ADD PRODUCT
    @PostMapping
    public Product addProduct(@Valid @RequestBody Product product) {

        return productService.saveProduct(product);

    }

    // UPDATE PRODUCT
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id,
                                 @Valid @RequestBody Product updatedProduct) {

        Product existingProduct = productService.getProductById(id);

        if (existingProduct == null) {
            throw new ProductNotFoundException(id);
        }

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStock(updatedProduct.getStock());
        existingProduct.setCategory(updatedProduct.getCategory());

        return productService.saveProduct(existingProduct);
    }

    // DELETE PRODUCT
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {

        Product product = productService.getProductById(id);

        if (product == null) {
            throw new ProductNotFoundException(id);
        }

        productService.deleteProduct(id);

        return "Product Deleted Successfully";
    }
}