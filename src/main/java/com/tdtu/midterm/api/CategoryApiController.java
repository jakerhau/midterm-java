package com.tdtu.midterm.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdtu.midterm.dto.Response;
import com.tdtu.midterm.model.Category;
import com.tdtu.midterm.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryApiController {
    
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Response getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return Response.builder(categories)
                    .message("Categories retrieved successfully")
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .code(500)
                    .status("Error")
                    .message("Failed to retrieve categories: " + e.getMessage())
                    .build();
        }
    }
}
