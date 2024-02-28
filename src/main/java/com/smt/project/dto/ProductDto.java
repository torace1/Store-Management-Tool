package com.smt.project.dto;

import com.smt.project.enums.CategoryName;
import lombok.Data;

@Data
public class ProductDto {
    private String name;
    private Double price;
    private CategoryName category;
}
