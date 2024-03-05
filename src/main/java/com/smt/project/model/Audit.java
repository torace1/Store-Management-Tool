package com.smt.project.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Audit {

    @Id
    @GeneratedValue
    private UUID id;

    private String action;

    private long timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
