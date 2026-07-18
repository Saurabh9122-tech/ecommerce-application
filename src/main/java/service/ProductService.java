package com.saurabh.ecommerce.service;

import com.saurabh.ecommerce.entity.Product;
import com.saurabh.ecommerce.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Save product
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // Get product by ID
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // Delete product
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Search products
    public List<Product> searchProducts(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return productRepository.findAll();
        }

        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    // Filter by category
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    // Pagination
    public Page<Product> getProductsByPage(int page) {

        Pageable pageable = PageRequest.of(page, 6);

        return productRepository.findAll(pageable);
    }

    // Sorting
    public List<Product> getSortedProducts(String sort) {

        if ("priceAsc".equals(sort)) {
            return productRepository.findAll(
                    Sort.by(Sort.Direction.ASC, "price"));
        }

        if ("priceDesc".equals(sort)) {
            return productRepository.findAll(
                    Sort.by(Sort.Direction.DESC, "price"));
        }

        if ("name".equals(sort)) {
            return productRepository.findAll(
                    Sort.by(Sort.Direction.ASC, "name"));
        }

        return productRepository.findAll();
    }

    public long getProductCount() {
        return productRepository.count();
    }
}