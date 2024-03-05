package com.smt.project.service.impl;

import com.smt.project.dto.ProductDto;
import com.smt.project.exception.SmtException;
import com.smt.project.mapper.ProductMapper;
import com.smt.project.model.Cart;
import com.smt.project.model.Category;
import com.smt.project.model.Product;
import com.smt.project.repository.CartRepository;
import com.smt.project.repository.CategoryRepository;
import com.smt.project.repository.ProductRepository;
import com.smt.project.service.CategoryService;
import com.smt.project.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    private final CartRepository cartRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void createProduct(ProductDto productDto) {
        Product product = ProductMapper.productDtoToProduct(productDto);
        Category category = categoryService.getCategoryByName(productDto.category());
        List<Product> categoryProducts = category.getProducts();
        categoryProducts.add(product);
        category.setProducts(categoryProducts);
        product.setCategory(category);
        productRepository.save(product);
        log.info(String.format("Product with name %s was create", product.getName()));
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
    @Transactional
    public void deleteProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new SmtException(404,"Product not found"));


            for (Cart cart : product.getCarts()) {
                cart.getProducts().remove(product);
                cartRepository.save(cart);
            }
            Category category = product.getCategory();
            if (category != null) {
                category.getProducts().remove(product);
                categoryRepository.save(category);
            }
            product.getCarts().clear();

            productRepository.delete(product);
            log.info(String.format("Product with id %s was deleted", productId));
    }

    @Override
    public void updateProductStock(Product product) {
        log.info(String.format("Stock for product %s was updated", product.getId()));
        productRepository.save(product);

    }

    @Override
    public List<UUID> checkThePresenceOfTheProdcutsInCarts(UUID productId) {

        List<UUID> carts = new ArrayList<>();
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
             carts = product.getCarts().stream().map(Cart::getId).collect(Collectors.toList());
        }

        return carts;
    }
}