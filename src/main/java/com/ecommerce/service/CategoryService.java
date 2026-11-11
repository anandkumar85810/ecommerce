package com.ecommerce.service;

import com.ecommerce.dto.request.CategoryRequest;
import com.ecommerce.dto.response.CategoryResponse;
import com.ecommerce.entity.Category;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CategoryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Caching(evict = {
            @CacheEvict(value = "categories", allEntries = true),
            @CacheEvict(value = "categoryById", allEntries = true)
    })
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = new  Category();

        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());
        category.setImage(request.getImage());

        Category savedCategory = categoryRepository.save(category);

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(savedCategory.getId());
        categoryResponse.setCategoryName(savedCategory.getCategoryName());
        categoryResponse.setDescription(savedCategory.getDescription());
        categoryResponse.setImage(savedCategory.getImage());
        categoryResponse.setActive(savedCategory.getActive());
        categoryResponse.setCreatedAt(LocalDateTime.now());
        categoryResponse.setUpdatedAt(LocalDateTime.now());
        return categoryResponse;
    }

    @Cacheable(value = "categoryById", key = "#p0")
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Category with id " + id + " not found!"));

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setId(category.getId());
        categoryResponse.setCategoryName(category.getCategoryName());
        categoryResponse.setDescription(category.getDescription());
        categoryResponse.setImage(category.getImage());
        categoryResponse.setActive(category.getActive());
        categoryResponse.setCreatedAt(category.getCreatedAt());
        categoryResponse.setUpdatedAt(category.getUpdatedAt());

        return categoryResponse;
    }

    @Cacheable(value = "categories", key = "'all'")
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        List<CategoryResponse> categoryResponseList = new ArrayList<>();

        for (Category category : categories) {
            CategoryResponse categoryResponse = new CategoryResponse();

            categoryResponse.setId(category.getId());
            categoryResponse.setCategoryName(category.getCategoryName());
            categoryResponse.setDescription(category.getDescription());
            categoryResponse.setImage(category.getImage());
            categoryResponse.setActive(category.getActive());
            categoryResponse.setCreatedAt(category.getCreatedAt());
            categoryResponse.setUpdatedAt(category.getUpdatedAt());

            categoryResponseList.add(categoryResponse);
        }
        return categoryResponseList;
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Category with id " + id + " not found!"));

        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());
        category.setImage(request.getImage());

        Category savedCategory = categoryRepository.save(category);

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setId(savedCategory.getId());
        categoryResponse.setCategoryName(savedCategory.getCategoryName());
        categoryResponse.setDescription(savedCategory.getDescription());
        categoryResponse.setImage(savedCategory.getImage());
        categoryResponse.setActive(savedCategory.getActive());
        categoryResponse.setCreatedAt(savedCategory.getCreatedAt());
        categoryResponse.setUpdatedAt(savedCategory.getUpdatedAt());

        return categoryResponse;
    }

    public void deleteCategory(Long id) {
       Category category = categoryRepository.findById(id).orElseThrow(() ->
               new ResourceNotFoundException("Category with id " + id + " not found!"));

       categoryRepository.delete(category);
    }
}
