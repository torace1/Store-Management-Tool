package com.smt.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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
    @Cascade(CascadeType.ALL)
    private List<Product> products;
}
