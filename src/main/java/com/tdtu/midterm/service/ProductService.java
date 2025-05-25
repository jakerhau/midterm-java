package com.tdtu.midterm.service;

import com.tdtu.midterm.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    List<Product> getProductsByFilter(Long category, Double minPrice, Double maxPrice, String brand, String color, Integer page, Integer size, String sortBy, String sortDir);

    Product getProductById(Long id);

    Long countProducts(Long category, Double minPrice, Double maxPrice, String brand, String color);
}
