package com.example.tasktestravenapplicationtwo.models;

import jakarta.persistence.*;
import lombok.Data;

/**
 * This Lombok-annotated class, Customer, represents a customer entity.
 * It is annotated with @Entity and includes JPA annotations for table mapping.
 * The @Data annotation automates common boilerplate code generation.
 */
@Data
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "created")
    private Long created;

    @Column(name = "updated")
    private Long updated;

    @Column(nullable = false, name = "full_name")
    private String fullName;

    @Column(nullable = false, name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(nullable = false, name = "is_active")
    private Boolean isActive;
}