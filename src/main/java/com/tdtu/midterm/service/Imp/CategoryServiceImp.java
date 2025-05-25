package com.tdtu.midterm.service.Imp;

import com.tdtu.midterm.model.Category;
import com.tdtu.midterm.repository.CategoryRepository;
import com.tdtu.midterm.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
