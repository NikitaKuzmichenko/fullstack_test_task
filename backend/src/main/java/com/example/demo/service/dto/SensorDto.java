package com.example.demo.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class SensorDto implements Serializable {

    private long id;

    @NotNull
    @Size(max = 30)
    private String name;

    @NotNull
    @Size(max = 15)
    private String model;

    private int lowerOperationalBound;

    private int higherOperationalBound;

    @NotNull
    private MeasurementsUnitDto measurementsUnit;

    @NotNull
    private SensorTypeDto type;

    @NotNull
    @Size(max = 40)
    private String location;

    @NotNull
    @Size(max = 200)
    private String description;
}
