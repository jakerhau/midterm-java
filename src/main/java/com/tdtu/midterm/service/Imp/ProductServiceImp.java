package com.tdtu.midterm.service.Imp;

import com.tdtu.midterm.constant.Brand;
import com.tdtu.midterm.constant.Color;
import com.tdtu.midterm.model.Product;
import com.tdtu.midterm.repository.ProductRepository;
import com.tdtu.midterm.service.ProductService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByFilter(Long category, Double minPrice, Double maxPrice, String brand, String color, Integer page, Integer size, String sortBy, String sortDir) {
        Brand brandEnum = null;
        Color colorEnum = null;

        if (brand != null && !brand.isEmpty()) {
            try {
                brandEnum = Brand.valueOf(brand.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Handle invalid brand value
            }
        }
        if (color != null && !color.isEmpty()) {
            try {
                colorEnum = Color.valueOf(color.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Handle invalid color value
            }
        }

        // Create Pageable object with proper field name mapping
        String fieldName = sortBy;
        if ("price".equals(sortBy)) {
            fieldName = "price";
        } else if ("name".equals(sortBy)) {
            fieldName = "productName";
        } else {
            fieldName = "id"; // default sort field
        }
        
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), fieldName);
        Pageable pageable = PageRequest.of(page, size, sort);

        Brand finalBrandEnum = brandEnum;
        Color finalColorEnum = colorEnum;
        return productRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (category != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), category));
            }
            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            if (finalBrandEnum != null) {
                predicates.add(criteriaBuilder.equal(root.get("brand"), finalBrandEnum));
            }
            if (finalColorEnum != null) {
                predicates.add(criteriaBuilder.equal(root.get("color"), finalColorEnum));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable).getContent();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Long countProducts(Long category, Double minPrice, Double maxPrice, String brand, String color) {
        Brand brandEnum = null;
        Color colorEnum = null;

        if (brand != null && !brand.isEmpty()) {
            try {
                brandEnum = Brand.valueOf(brand.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Handle invalid brand value
            }
        }
        if (color != null && !color.isEmpty()) {
            try {
                colorEnum = Color.valueOf(color.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Handle invalid color value
            }
        }

        Brand finalBrandEnum = brandEnum;
        Color finalColorEnum = colorEnum;
        return productRepository.count((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (category != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), category));
            }
            
            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            
            if (finalBrandEnum != null) {
                predicates.add(criteriaBuilder.equal(root.get("brand"), finalBrandEnum));
            }
            
            if (finalColorEnum != null) {
                predicates.add(criteriaBuilder.equal(root.get("color"), finalColorEnum));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
