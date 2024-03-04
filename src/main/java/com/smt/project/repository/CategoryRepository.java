package com.smt.project.repository;

import com.smt.project.enums.CategoryName;
import com.smt.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByName(CategoryName name);
    boolean existsByName(CategoryName name);
    boolean existsById(UUID categoryId);
}
