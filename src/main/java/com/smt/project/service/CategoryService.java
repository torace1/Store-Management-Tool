package com.smt.project.service;

import com.smt.project.enums.CategoryName;
import com.smt.project.exception.SmtException;
import com.smt.project.model.Category;
import com.smt.project.repository.CategoryRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void createCategory(String categoryName) {
        CategoryName catName;
        try {
             catName = CategoryName.valueOf(categoryName);
        } catch (IllegalArgumentException e) {
           throw new SmtException(400, "wrong category name");
        }
        if (categoryRepository.existsByName(catName)) {
            throw new SmtException(400, String.format("Category with name {%s} already exists", categoryName));
        }
        Category category = new Category();
        category.setName(catName);
        categoryRepository.save(category);
    }

    public Category getCategoryByName(CategoryName categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new SmtException( 404,
                        String.format("Something went wrong while trying to retrieve category with name {%s}", categoryName.name())
                ));
    }

    public Category getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new SmtException(404,
                        String.format("Something went wrong while trying to retrieve category with id {%s}", categoryId))
                );
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void deleteCategory(UUID categoryId) {
        categoryRepository.delete(getCategoryById(categoryId));
    }
}
