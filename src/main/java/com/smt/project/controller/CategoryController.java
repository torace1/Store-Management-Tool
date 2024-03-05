package com.smt.project.controller;

import com.smt.project.audit.AuditLogging;
import com.smt.project.model.Category;
import com.smt.project.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/categories")
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @Secured("ADMIN")
    @AuditLogging
    public ResponseEntity<Void> createCategory(@RequestParam String categoryName) {
        categoryService.createCategory(categoryName);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryWithName(@PathVariable UUID categoryId) {
        return  ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategoryById(categoryId));
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return  ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllCategories());
    }

    @DeleteMapping("/{categoryId}")
    @Secured("ADMIN")
    @AuditLogging
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return  ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{categoryId}/carts")
    @Secured("ADMIN")
    @AuditLogging
    public ResponseEntity<List<UUID>> getCarts(@PathVariable UUID categoryId) {
        return  ResponseEntity.status(HttpStatus.OK).body(  categoryService.getCartIdsByCategoryId(categoryId));
    }
}
