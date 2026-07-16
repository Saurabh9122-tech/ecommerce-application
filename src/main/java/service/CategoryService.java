package com.saurabh.ecommerce.service;

import com.saurabh.ecommerce.entity.Category;
import com.saurabh.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> getAllCategories(){
        return repository.findAll();
    }

}