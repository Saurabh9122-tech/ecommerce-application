package com.saurabh.ecommerce.service;

import com.saurabh.ecommerce.entity.Product;
import com.saurabh.ecommerce.repository.CartItemRepository;
import com.saurabh.ecommerce.repository.OrderRepository;
import com.saurabh.ecommerce.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private static final Logger logger =
            LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;

    public ProductService(
            ProductRepository productRepository,
            CartItemRepository cartItemRepository,
            OrderRepository orderRepository) {

        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Save product
    public Product saveProduct(Product product) {

        logger.info("Saving product: {}", product.getName());

        return productRepository.save(product);
    }

    // Get product by ID
    public Product getProductById(Long id) {

        logger.info("Fetching product id {}", id);

        return productRepository.findById(id).orElse(null);
    }

    // Delete product
    @Transactional
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow();

        // Delete cart references
        cartItemRepository.deleteByProductId(id);

        // Delete order references
        orderRepository.deleteByProductId(id);

        // Delete product
        productRepository.delete(product);
    }

    // Search products
    public List<Product> searchProducts(String keyword) {

        logger.info("Searching products with keyword: {}", keyword);

        if (keyword == null || keyword.trim().isEmpty()) {
            return productRepository.findAll();
        }

        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    // Category filter
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

    // Dashboard
    public long getProductCount() {
        return productRepository.count();
    }
}