package com.smt.project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.smt.project.enums.CategoryName;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue
    private UUID id;
    private CategoryName name;
    @OneToMany
    @JsonManagedReference
    private List<Product> products;
}
