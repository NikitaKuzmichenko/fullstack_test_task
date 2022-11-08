package com.example.demo.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Sensor")
@Table(name = "sensor")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name",length = 30,nullable = false)
    private String name;

    @Column(name = "model",length = 15,nullable = false)
    private String model;

    @Column(name = "lower_operational_bound")
    private int lowerOperationalBound;

    @Column(name = "higher_operational_bound")
    private int higherOperationalBound;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "measurements_unit_id")
    private MeasurementsUnit measurementsUnit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private SensorType type;

    @Column(name = "location",length = 40,nullable = false)
    private String location;

    @Column(name = "description",length = 200,nullable = false)
    private String description;
}
