package com.smt.project.service;

import com.smt.project.enums.CategoryName;
import com.smt.project.model.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    Category createCategory(String categoryName);

    Category getCategoryByName(CategoryName categoryName);

    Category getCategoryById(UUID categoryId);

    List<Category> getAllCategories();

    void deleteCategory(UUID categoryId);
}
