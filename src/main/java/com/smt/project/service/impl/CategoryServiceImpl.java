package com.smt.project.service.impl;

import com.smt.project.enums.CategoryName;
import com.smt.project.exception.SmtException;
import com.smt.project.model.Category;
import com.smt.project.repository.CategoryRepository;
import com.smt.project.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(String categoryName) {

        log.info(String.format("createCategory with id categoryName :: %s", categoryName));
        CategoryName catName;
        try {
            catName = CategoryName.valueOf(categoryName);
        } catch (IllegalArgumentException e) {
            throw new SmtException(400, "Wrong category name");
        }
        if (categoryRepository.existsByName(catName)) {
            throw new SmtException(400, String.format("Category with name {%s} already exists", categoryName));
        }
        Category category = new Category();
        category.setName(catName);
        log.info(String.format("Category with name %s was created", categoryName));
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryByName(CategoryName categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new SmtException(404,
                        String.format("Something went wrong while trying to retrieve category with name {%s}", categoryName.name())
                ));
    }

    @Override
    public Category getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new SmtException(404,
                        String.format("Something went wrong while trying to retrieve category with id {%s}", categoryId))
                );
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        categoryRepository.delete(getCategoryById(categoryId));
        log.info(String.format("Category with id %s was deleted", categoryId));
    }
}
