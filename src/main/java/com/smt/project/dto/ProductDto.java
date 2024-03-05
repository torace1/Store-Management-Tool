package com.smt.project.dto;

import com.smt.project.enums.CategoryName;

public record ProductDto(String name,Integer stock, Double price, CategoryName category) {
}
