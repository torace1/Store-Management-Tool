package com.smt.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Cart {
    @Id
    @GeneratedValue
    private UUID id;
    private Double priceSum;
    @OneToOne
    @JsonBackReference
    private Profile profile;
    @ManyToMany
    @JsonManagedReference
    private List<Product> products;
}
