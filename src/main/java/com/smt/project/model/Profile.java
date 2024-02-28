package com.smt.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Profile {
    @Id
    @GeneratedValue
    private UUID id;
    private String firstName;
    private String lastName;
    private Integer age;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private Cart shoppingCart;
    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Order> orders;
}
