package com.example.demo.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "SensorType")
@Table(name = "sensor_type")
public class SensorType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "value", nullable = false,unique = true)
    private String value;
}
