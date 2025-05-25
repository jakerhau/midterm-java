package com.tdtu.midterm.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdtu.midterm.constant.Brand;
import com.tdtu.midterm.constant.Color;
import com.tdtu.midterm.dto.Response;
import com.tdtu.midterm.model.Category;
import com.tdtu.midterm.model.Product;
import com.tdtu.midterm.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${api.base.url}")
    private String apiBaseUrl;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String index(Model model,
                       @RequestParam(required = false) Long category,
                       @RequestParam(required = false) Double minPrice,
                       @RequestParam(required = false) Double maxPrice,
                       @RequestParam(required = false) String brand,
                       @RequestParam(required = false) String color,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "6") int size,
                       @RequestParam(defaultValue = "id") String sortBy,
                       @RequestParam(defaultValue = "asc") String sortDir) {
        try {
            // Create Pageable object
            Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
            Pageable pageable = PageRequest.of(page, size, sort);

            // Build API URL with parameters
            StringBuilder apiUrl = new StringBuilder(apiBaseUrl + "/api/products?");
            if (category != null) apiUrl.append("category=").append(category).append("&");
            if (minPrice != null) apiUrl.append("minPrice=").append(minPrice).append("&");
            if (maxPrice != null) apiUrl.append("maxPrice=").append(maxPrice).append("&");
            if (brand != null) apiUrl.append("brand=").append(brand).append("&");
            if (color != null) apiUrl.append("color=").append(color).append("&");
            apiUrl.append("page=").append(pageable.getPageNumber()).append("&");
            apiUrl.append("size=").append(pageable.getPageSize()).append("&");
            apiUrl.append("sortBy=").append(sortBy).append("&");
            apiUrl.append("sortDir=").append(sortDir);

            //Get categories api
            String categoryApiUrl = apiBaseUrl + "/api/categories";
            Response categoryResponse = restTemplate.getForObject(categoryApiUrl, Response.class);
            if (categoryResponse != null && categoryResponse.getCode() == 200) {
                List<Category> categories = objectMapper.convertValue(
                    categoryResponse.getData(),
                    new TypeReference<List<Category>>() {}
                );
                model.addAttribute("categories", categories);
            } else {
                logger.error("Failed to load categories");
            }

            logger.info("Calling API: {}", apiUrl.toString());

            // Call API
            Response response = restTemplate.getForObject(apiUrl.toString(), Response.class);
            
            if (response != null && response.getCode() == 200) {
                Response.PageData pageData = objectMapper.convertValue(
                    response.getData(),
                    Response.PageData.class
                );
                
                List<Product> products = objectMapper.convertValue(
                    pageData.getContent(),
                    new TypeReference<List<Product>>() {}
                );
                
                List<Brand> brands = List.of(Brand.values());
                List<Color> colors = List.of(Color.values());

                model.addAttribute("products", products);
                model.addAttribute("brands", brands);
                model.addAttribute("colors", colors);
                
                // Add pagination info
                model.addAttribute("currentPage", pageData.getCurrentPage());
                model.addAttribute("pageSize", pageData.getPageSize());
                model.addAttribute("totalPages", pageData.getTotalPages());
                model.addAttribute("totalElements", pageData.getTotalElements());
                model.addAttribute("sortBy", sortBy);
                model.addAttribute("sortDir", sortDir);
                
                if (response.getMessage() != null && !response.getMessage().isEmpty()) {
                    model.addAttribute("message", response.getMessage());
                    model.addAttribute("toastType", "success");
                }
            } else {
                String errorMsg = response != null ? response.getMessage() : "Failed to load products";
                logger.error("API Error: {}", errorMsg);
                model.addAttribute("message", errorMsg);
                model.addAttribute("toastType", "error");
            }
            return "products/index";
        } catch (Exception e) {
            logger.error("Error calling products API", e);
            model.addAttribute("message", "Error: " + e.getMessage());
            model.addAttribute("toastType", "error");
            return "error";
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String detail(Model model, @PathVariable("id") Long id) {
        try {
            String apiUrl = apiBaseUrl + "/api/products/" + id;
            logger.info("Calling API: {}", apiUrl);

            // Call API
            Response response = restTemplate.getForObject(apiUrl, Response.class);
            
            if (response != null && response.getCode() == 200) {
                Product product = objectMapper.convertValue(
                    response.getData(),
                    Product.class
                );
                model.addAttribute("product", product);
                
                if (response.getMessage() != null && !response.getMessage().isEmpty()) {
                    model.addAttribute("message", response.getMessage());
                    model.addAttribute("toastType", "success");
                }
            } else {
                String errorMsg = response != null ? response.getMessage() : "Failed to load product details";
                logger.error("API Error: {}", errorMsg);
                model.addAttribute("message", errorMsg);
                model.addAttribute("toastType", "error");
            }
        } catch (Exception e) {
            logger.error("Error calling product detail API", e);
            model.addAttribute("message", "Error: " + e.getMessage());
            model.addAttribute("toastType", "error");
        }
        
        return "products/product-detail";
    }
}
