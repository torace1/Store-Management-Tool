package com.smt.project.service;


import com.smt.project.enums.CategoryName;
import com.smt.project.exception.SmtException;
import com.smt.project.model.Category;
import com.smt.project.repository.CategoryRepository;
import com.smt.project.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Captor
    private ArgumentCaptor<Category> categoryCaptor;


    @DisplayName("JUnit test for createCategory method success")
    @Test
    public void givenCategoryName_whenCreateCategory_thenNothing(){
        // given
        String categoryName = "TECHNOLOGY";
        CategoryName expectedCategoryName = CategoryName.TECHNOLOGY;
        Category category = new Category();
        category.setName(expectedCategoryName);

        when(categoryRepository.existsByName(any(CategoryName.class))).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // when
        Category createdCategory = categoryService.createCategory(categoryName);

        // then
        assertNotNull(createdCategory);
        assertEquals(expectedCategoryName, createdCategory.getName());
    }

    @DisplayName("JUnit test for createCategory method fail, already existing category with this name")
    @Test
    public void givenExistingCategoryName_whenCreateCategory_thenExceptionThrown(){
        // given
        String categoryName = "TECHNOLOGY";

        // when
        when(categoryRepository.existsByName(any(CategoryName.class))).thenReturn(true);

        // then
        assertThrows(SmtException.class, () -> {
            categoryService.createCategory(categoryName);
        });
    }

    @DisplayName("JUnit test for createCategory method fail, invalid category name")
    @Test
    public void givenInvalidCategoryName_whenCreateCategory_thenExceptionThrown(){
        // given
        String categoryName = "INVALID_CATEGORY_NAME";

        // then
        assertThrows(SmtException.class, () -> {
            categoryService.createCategory(categoryName);
        });
    }

    @DisplayName("JUnit test for getCategoryByName method success")
    @Test
    public void givenExistingCategoryName_whenGetCategoryByName_thenCategoryReturned(){
        // given
        CategoryName categoryName = CategoryName.TECHNOLOGY;
        Category expectedCategory = new Category();
        expectedCategory.setName(categoryName);
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(expectedCategory));

        // when
        Category category = categoryService.getCategoryByName(categoryName);

        // then
        assertEquals(category, expectedCategory);
    }

    @DisplayName("JUnit test for getCategoryByName method with non-existing category")
    @Test
    public void givenNonExistingCategoryName_whenGetCategoryByName_thenExceptionThrown(){
        // given
        CategoryName categoryName = CategoryName.TECHNOLOGY;
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.empty());

        // when
        assertThrows(SmtException.class, () -> categoryService.getCategoryByName(categoryName));
    }

    @DisplayName("JUnit test for getCategoryById method success")
    @Test
    public void givenExistingCategoryId_whenGetCategoryById_thenCategoryReturned(){
        // given
        UUID categoryId = UUID.randomUUID();
        Category expectedCategory = new Category();
        expectedCategory.setId(categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(expectedCategory));

        // when
        Category category = categoryService.getCategoryById(categoryId);

        // then
        assertEquals(category, expectedCategory);
    }

    @DisplayName("JUnit test for getCategoryById method with non-existing category")
    @Test
    public void givenNonExistingCategoryId_whenGetCategoryById_thenExceptionThrown(){
        // given
        UUID categoryId = UUID.randomUUID();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // when
        assertThrows(SmtException.class, () -> categoryService.getCategoryById(categoryId));
    }

    @DisplayName("JUnit test for getAllCategories method success")
    @Test
    public void whenGetAllCategories_thenListReturned(){
        // given
        Category category1 = new Category();
        Category category2 = new Category();
        Category category3 = new Category();
        List<Category> expectedCategories = Arrays.asList(category1, category2, category3);
        when(categoryRepository.findAll()).thenReturn(expectedCategories);

        // when
        List<Category> categories = categoryService.getAllCategories();

        // then
        assertEquals(expectedCategories.size(), categories.size());
        assertTrue(categories.containsAll(expectedCategories));
    }

    @DisplayName("JUnit test for deleteCategory method success")
    @Test
    public void givenCategoryId_whenDeleteCategory_thenCategoryDeleted(){
        // given
        UUID categoryId = UUID.randomUUID();
        Category expectedCategory = new Category();
        expectedCategory.setId(categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(expectedCategory));

        // when
        categoryService.deleteCategory(categoryId);

        // then
        verify(categoryRepository, times(1)).delete(categoryCaptor.capture());
        assertEquals(expectedCategory, categoryCaptor.getValue());
    }
}