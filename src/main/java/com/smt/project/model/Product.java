package com.smt.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private Double price;
    @ManyToOne
    @JsonBackReference
    private Category category;
    @JsonBackReference
    @ManyToMany
    private List<Cart> carts;
    @ManyToMany
    @JsonBackReference
    private List<Order> order;
}
