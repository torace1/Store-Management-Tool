package com.smt.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Table(name = "orders")
@Entity
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue
    private UUID id;
    private Double priceSum;
    @ManyToOne
    @JsonBackReference
    private Profile profile;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonManagedReference
    private List<Product> products;

    public Order( Double priceSum, Profile profile, List<Product> products) {
        this.id = UUID.randomUUID();
        this.priceSum = priceSum;
        this.profile = profile;
        this.products = products;
    }
}
