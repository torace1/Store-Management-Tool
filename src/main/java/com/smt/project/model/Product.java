package com.smt.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

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

    private Integer stock;
    @ManyToOne
    @JsonBackReference
    private Category category;
    @JsonBackReference
    @ManyToMany(mappedBy = "products")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Cart> carts;
    @ManyToMany(mappedBy = "products")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JsonBackReference
    private List<Order> order;
}
