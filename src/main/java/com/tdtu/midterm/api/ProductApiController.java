package com.tdtu.midterm.api;

import com.tdtu.midterm.dto.Response;
import com.tdtu.midterm.model.Product;
import com.tdtu.midterm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductApiController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public Response getProducts(
            @RequestParam(required = false) Long category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String color,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "6") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        try {
            List<Product> products = productService.getProductsByFilter(category, minPrice, maxPrice, brand, color, page, size, sortBy, sortDir);
            Long totalElements = productService.countProducts(category, minPrice, maxPrice, brand, color);
            Integer totalPages = (int) Math.ceil((double) totalElements / size);
            
            Response.PageData pageData = Response.PageData.of(
                products,
                totalElements,
                totalPages,
                page,
                size
            );
            
            return Response.builder(pageData)
                    .message("Products retrieved successfully")
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .code(500)
                    .status("Error")
                    .message("Failed to retrieve products: " + e.getMessage())
                    .build();
        }
    }

    @GetMapping("/{id}")
    public Response getProductDetail(@PathVariable("id") Long id) {
        try {
            Product product = productService.getProductById(id);
            if (product != null) {
                return Response.builder(product)
                        .message("Product retrieved successfully")
                        .build();
            } else {
                return Response.builder()
                        .code(404)
                        .status("Error")
                        .message("Product not found")
                        .build();
            }
        } catch (Exception e) {
            return Response.builder()
                    .code(500)
                    .status("Error")
                    .message("Failed to retrieve product: " + e.getMessage())
                    .build();
        }
    }
} 