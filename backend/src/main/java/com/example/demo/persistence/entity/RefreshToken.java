package com.example.demo.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "RefreshToken")
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "refresh_token",nullable = false, unique = true)
    private String value;

    @Column(name = "creation_date",nullable = false)
    private Date creationDate;

    @Column(name = "expiration_date",nullable = false)
    private Date expirationDate;
}
