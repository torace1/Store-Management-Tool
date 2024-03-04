package com.smt.project.service.impl;

import com.smt.project.enums.CategoryName;
import com.smt.project.exception.SmtException;
import com.smt.project.model.Cart;
import com.smt.project.model.Category;
import com.smt.project.repository.CartRepository;
import com.smt.project.repository.CategoryRepository;
import com.smt.project.repository.ProductRepository;
import com.smt.project.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

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
    public List<UUID> getCartIdsByCategoryId(UUID categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        return cartRepository.findByProductsCategory(category.getId()).stream().map(Cart::getId).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteCategory(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        List<Cart> carts = cartRepository.findByProductsCategory(category.getId());
        if (carts.size()!=0){
            throw new SmtException(500, " There are carts with products from this category");
        }
        categoryRepository.delete(category);
        log.info(String.format("Category with id %s was deleted", categoryId));
    }
}
