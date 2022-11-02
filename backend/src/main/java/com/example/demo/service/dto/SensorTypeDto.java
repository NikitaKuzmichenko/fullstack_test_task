package com.example.demo.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SensorTypeDto implements Serializable {

    private long id;

    @NotNull
    private String value;
}
