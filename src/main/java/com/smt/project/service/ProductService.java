package com.smt.project.service;

import com.smt.project.dto.ProductDto;
import com.smt.project.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

     void createProduct(ProductDto productDto);
     Product getProductById(UUID productId);
     void deleteProduct(UUID productId);
     List<Product> getAllProducts();
     public void updateProductStock(Product product);
     public List<UUID> checkThePresenceOfTheProdcutsInCarts(UUID productid);
}
