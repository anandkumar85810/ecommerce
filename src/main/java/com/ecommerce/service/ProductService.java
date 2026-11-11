package com.ecommerce.service;

import com.ecommerce.dto.request.ProductRequest;
import com.ecommerce.dto.response.ProductResponse;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponse createProduct(ProductRequest request) {

        if (productRepository.existsBySku(request.getSku())) {
            throw new IllegalArgumentException("Product with sku " + request.getSku() + " already exists");
        }
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + request.getCategoryId() + " not found!"));

        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setSku(request.getSku());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        ProductResponse productResponse = new ProductResponse();

        productResponse.setId(savedProduct.getId());
        productResponse.setName(savedProduct.getName());
        productResponse.setDescription(savedProduct.getDescription());
        productResponse.setPrice(savedProduct.getPrice());
        productResponse.setStockQuantity(savedProduct.getStockQuantity());
        productResponse.setImageUrl(savedProduct.getImageUrl());
        productResponse.setSku(savedProduct.getSku());
        productResponse.setActive(savedProduct.getActive());
        productResponse.setCategoryId(savedProduct.getCategory().getId());
        productResponse.setCategoryName(savedProduct.getCategory().getCategoryName());
        productResponse.setCreatedAt(savedProduct.getCreatedAt());
        productResponse.setUpdatedAt(savedProduct.getUpdatedAt());

        return productResponse;
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found!"));

        ProductResponse productResponse = new ProductResponse();

        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setStockQuantity(product.getStockQuantity());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setSku(product.getSku());
        productResponse.setActive(product.getActive());
        productResponse.setCategoryId(product.getCategory().getId());
        productResponse.setCategoryName(product.getCategory().getCategoryName());
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());

        return productResponse;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponseList = new ArrayList<>();

        for (Product product : products) {
            ProductResponse productResponse = new ProductResponse();

            productResponse.setId(product.getId());
            productResponse.setName(product.getName());
            productResponse.setDescription(product.getDescription());
            productResponse.setPrice(product.getPrice());
            productResponse.setStockQuantity(product.getStockQuantity());
            productResponse.setImageUrl(product.getImageUrl());
            productResponse.setSku(product.getSku());
            productResponse.setActive(product.getActive());
            productResponse.setCategoryId(product.getCategory().getId());
            productResponse.setCategoryName(product.getCategory().getCategoryName());
            productResponse.setCreatedAt(product.getCreatedAt());
            productResponse.setUpdatedAt(product.getUpdatedAt());

            productResponseList.add(productResponse);
        }
        return productResponseList;
    }

    public ProductResponse updateProduct(ProductRequest request, Long id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found!"));

        productRepository.findBySku(request.getSku())
                .filter(existingProduct -> !existingProduct.getId().equals(id))
                .ifPresent(existingProduct -> {
                    throw new IllegalArgumentException("Product with sku " + request.getSku() + " already exists");
                });

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category with id " + request.getCategoryId() + " not found!"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setSku(request.getSku());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(category);

        Product updateProduct = productRepository.save(product);

        ProductResponse productResponse = new ProductResponse();

        productResponse.setId(updateProduct.getId());
        productResponse.setName(updateProduct.getName());
        productResponse.setDescription(updateProduct.getDescription());
        productResponse.setPrice(updateProduct.getPrice());
        productResponse.setStockQuantity(updateProduct.getStockQuantity());
        productResponse.setImageUrl(updateProduct.getImageUrl());
        productResponse.setSku(updateProduct.getSku());
        productResponse.setActive(updateProduct.getActive());
        productResponse.setCategoryId(updateProduct.getCategory().getId());
        productResponse.setCategoryName(updateProduct.getCategory().getCategoryName());
        productResponse.setCreatedAt(updateProduct.getCreatedAt());
        productResponse.setUpdatedAt(updateProduct.getUpdatedAt());
        return productResponse;
    }

    public void deleteProduct(Long id) {
       Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found!"));

       productRepository.delete(product);
    }
}
