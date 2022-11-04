package com.example.demo.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "RefreshToken")
@Entity(name = "RefreshToken")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "refresh_token")
    private String value;

    @Column(name = "creationDate")
    private Date creationDate;

    @Column(name = "expirationDate")
    private Date expirationDate;
}
