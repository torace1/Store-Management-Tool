package com.smt.project.service.impl;

import com.smt.project.dto.ProductDto;
import com.smt.project.exception.SmtException;
import com.smt.project.mapper.ProductMapper;
import com.smt.project.model.Category;
import com.smt.project.model.Product;
import com.smt.project.repository.ProductRepository;
import com.smt.project.service.CategoryService;
import com.smt.project.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Override
    public void createProduct(ProductDto productDto) {
        Product product = ProductMapper.productDtoToProduct(productDto);
        Category category = categoryService.getCategoryByName(productDto.category());
        List<Product> categoryProducts = category.getProducts();
        categoryProducts.add(product);
        category.setProducts(categoryProducts);
        product.setCategory(category);
        productRepository.save(product);
        log.info( String.format("Product with name %s was create", product.getName()));
    }

    @Override
    public Product getProductById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new SmtException(404,
                        String.format("Something went wrong while trying to retrieve product with id {%s}", productId)
                ));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProduct(UUID productId) {
        Product product = getProductById(productId);
        Category category = product.getCategory();
        List<Product> categoryProducts = category.getProducts();
        categoryProducts.remove(product);
        productRepository.deleteById(productId);
        log.info( String.format("Product with id %s was deleted", productId));
    }
}