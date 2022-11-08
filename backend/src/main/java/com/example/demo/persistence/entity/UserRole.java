package com.example.demo.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "UserRole")
@Table(name = "user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name",nullable = false, unique = true)
    private String role;
}
