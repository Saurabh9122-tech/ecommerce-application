package com.saurabh.ecommerce.config;

import com.saurabh.ecommerce.entity.Category;
import com.saurabh.ecommerce.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public DataInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {

        if (categoryRepository.count() == 0) {

            String[] categories = {
                    "Electronics",
                    "Mobile",
                    "Laptop",
                    "Fashion",
                    "Shoes",
                    "Books",
                    "Grocery",
                    "Sports",
                    "Home & Kitchen"
            };

            for (String name : categories) {

                Category category = new Category();
                category.setName(name);

                categoryRepository.save(category);
            }
        }
    }
}