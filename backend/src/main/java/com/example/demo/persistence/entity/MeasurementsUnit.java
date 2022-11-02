package com.example.demo.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "MeasurementUnit")
@Table(name = "MeasurementUnit")
public class MeasurementsUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "value", nullable = false)
    private String value;
}
