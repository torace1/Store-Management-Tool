package com.smt.project.service;

import com.smt.project.dto.ProductDto;
import com.smt.project.exception.SmtException;
import com.smt.project.mapper.ProductMapper;
import com.smt.project.model.Category;
import com.smt.project.model.Product;
import com.smt.project.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public void createProduct(ProductDto productDto) {
        Product product = ProductMapper.productDtoToProduct(productDto);
        Category category = categoryService.getCategoryByName(productDto.getCategory());
        List<Product> categoryProducts = category.getProducts();
        categoryProducts.add(product);
        category.setProducts(categoryProducts);
        product.setCategory(category);
        productRepository.save(product);
    }

    public Product getProductById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new SmtException(404,
                        String.format("Something went wrong while trying to retrieve product with id {%s}", productId)
                ));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(UUID productId) {
        Product product = getProductById(productId);
        Category category = product.getCategory();
        List<Product> categoryProducts = category.getProducts();
        categoryProducts.remove(product);
        productRepository.deleteById(productId);
    }
}