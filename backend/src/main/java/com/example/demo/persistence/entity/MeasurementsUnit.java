package com.example.demo.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "MeasurementUnit")
@Table(name = "measurement_unit")
public class MeasurementsUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "value", nullable = false, unique = true)
    private String value;
}
