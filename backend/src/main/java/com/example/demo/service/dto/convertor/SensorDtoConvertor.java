package com.example.demo.service.dto.convertor;

import com.example.demo.persistence.entity.Sensor;
import com.example.demo.service.dto.SensorDto;

public final class SensorDtoConvertor {
    private SensorDtoConvertor(){}

    public static SensorDto toDto(Sensor sensor){
        if(sensor == null){
            return null;
        }

        SensorDto dto = new SensorDto();
        dto.setId(sensor.getId());
        dto.setName(sensor.getName());
        dto.setModel(sensor.getModel());
        dto.setLowerOperationalBound(sensor.getLowerOperationalBound());
        dto.setHigherOperationalBound(sensor.getHigherOperationalBound());
        dto.setMeasurementsUnit(MeasurementsUnitDtoConvertor.toDto(sensor.getMeasurementsUnit()));
        dto.setType(SensorTypeDtoConvertor.toDto(sensor.getType()));
        dto.setLocation(sensor.getLocation());
        dto.setDescription(sensor.getDescription());

        return dto;
    }

    public static Sensor fromDto(SensorDto sensor){
        if(sensor == null){
            return null;
        }

        Sensor entity = new Sensor();
        entity.setId(sensor.getId());
        entity.setName(sensor.getName());
        entity.setModel(sensor.getModel());
        entity.setLowerOperationalBound(sensor.getLowerOperationalBound());
        entity.setHigherOperationalBound(sensor.getHigherOperationalBound());
        entity.setMeasurementsUnit(MeasurementsUnitDtoConvertor.fromDto(sensor.getMeasurementsUnit()));
        entity.setType(SensorTypeDtoConvertor.fromDto(sensor.getType()));
        entity.setLocation(sensor.getLocation());
        entity.setDescription(sensor.getDescription());

        return entity;
    }
}
