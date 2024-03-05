package com.smt.project.controller;

import com.smt.project.audit.AuditLogging;
import com.smt.project.dto.ProductDto;
import com.smt.project.model.Product;
import com.smt.project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @Secured("ADMIN")
    @AuditLogging
    public ResponseEntity<Void> createProduct(@RequestBody ProductDto productDto) {
        productService.createProduct(productDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable UUID productId) {
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    @Secured("ADMIN")
    @AuditLogging
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{productId}/carts")
    @Secured("ADMIN")
    @AuditLogging
    public ResponseEntity<List<UUID>> getTheCartsAssociatedWithProductId(@PathVariable UUID productId) {
        return  ResponseEntity.status(HttpStatus.OK).body(productService.checkThePresenceOfTheProdcutsInCarts(productId));
    }
}
