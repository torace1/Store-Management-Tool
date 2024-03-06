package com.smt.project.service;


import com.smt.project.enums.CategoryName;
import com.smt.project.exception.SmtException;
import com.smt.project.model.Cart;
import com.smt.project.model.Category;
import com.smt.project.repository.CartRepository;
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
    @Mock
    private CartRepository cartRepository;
    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Captor
    private ArgumentCaptor<Category> categoryCaptor;


    @DisplayName("test for createCategory method success")
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

    @DisplayName("test for createCategory method fail, already existing category with this name")
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

    @DisplayName("test for createCategory method fail, invalid category name")
    @Test
    public void givenInvalidCategoryName_whenCreateCategory_thenExceptionThrown(){
        // given
        String categoryName = "INVALID_CATEGORY_NAME";

        // then
        assertThrows(SmtException.class, () -> {
            categoryService.createCategory(categoryName);
        });
    }

    @DisplayName("test for getCategoryByName method success")
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

    @DisplayName("test for getCategoryByName method with non-existing category")
    @Test
    public void givenNonExistingCategoryName_whenGetCategoryByName_thenExceptionThrown(){
        // given
        CategoryName categoryName = CategoryName.TECHNOLOGY;
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.empty());

        // when
        assertThrows(SmtException.class, () -> categoryService.getCategoryByName(categoryName));
    }

    @DisplayName("test for getCategoryById method success")
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

    @DisplayName("test for getCategoryById method with non-existing category")
    @Test
    public void givenNonExistingCategoryId_whenGetCategoryById_thenExceptionThrown(){
        // given
        UUID categoryId = UUID.randomUUID();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // when
        assertThrows(SmtException.class, () -> categoryService.getCategoryById(categoryId));
    }

    @DisplayName("test for getAllCategories method success")
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

    @Test
    @DisplayName("Test getCartIdsByCategoryId method success")
    void whenGetAllCartsByCategory_thenListReturned() {
        // given
        UUID categoryId = UUID.randomUUID();
        Category category = new Category();
        category.setId(categoryId);
        Cart cart1 = new Cart();
        cart1.setId(UUID.randomUUID());
        Cart cart2 = new Cart();
        cart2.setId(UUID.randomUUID());
        List<Cart> carts = Arrays.asList(cart1, cart2);
        when(categoryRepository.findById(categoryId)).thenReturn(java.util.Optional.of(category));
        when(cartRepository.findByProductsCategory(categoryId)).thenReturn(carts);

        // when
        List<UUID> cartIds = categoryService.getCartIdsByCategoryId(categoryId);

        // then
        assertEquals(2, cartIds.size());
        assertTrue(cartIds.contains(cart1.getId()));
        assertTrue(cartIds.contains(cart2.getId()));
    }

    @Test
    @DisplayName("Test deleteCategory method success")
    void givenProductsAndCarts_whenDeleteCategory_thenCheckException() {
        // given
        UUID categoryId = UUID.randomUUID();
        Category category = new Category();
        category.setId(categoryId);
        Cart cart1 = new Cart();
        cart1.setId(UUID.randomUUID());
        Cart cart2 = new Cart();
        cart2.setId(UUID.randomUUID());
        List<Cart> carts = Arrays.asList(cart1, cart2);
        when(categoryRepository.findById(categoryId)).thenReturn(java.util.Optional.of(category));
        when(cartRepository.findByProductsCategory(categoryId)).thenReturn(carts);

        // then
        assertThrows(SmtException.class, () -> categoryService.deleteCategory(categoryId));
    }

    @Test
    @DisplayName("Test deleteCategory method success")
    void givenProductsAndCarts_whenDeleteCategory_thenDelete() {
        // given
        UUID categoryId = UUID.randomUUID();
        Category category = new Category();
        category.setId(categoryId);

        // when
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        categoryService.deleteCategory(categoryId);

        // then
        verify(cartRepository).findByProductsCategory(categoryId);
        verify(cartRepository, times(0)).deleteById(any());
        verify(categoryRepository).delete(category);
    }
}