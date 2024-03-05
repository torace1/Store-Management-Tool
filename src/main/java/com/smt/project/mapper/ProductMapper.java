package com.smt.project.mapper;

import com.smt.project.dto.ProductDto;
import com.smt.project.model.Product;

public final class ProductMapper {
    public static Product productDtoToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.name());
        product.setPrice(productDto.price());
        product.setStock(productDto.stock());
        return product;
    }
}
